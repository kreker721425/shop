package com.github.kreker721425.shop.service;

import com.github.kreker721425.shop.repository.filter.Filter;

import java.util.List;

/**
 * Интерфейс с CRUD операциями
 *
 * @param <D> - DTO
 * @param <I> - тип идентификатора
 * @param <F> Фильтр
 */
public interface CRUDService<D, I, F extends Filter> {
    /**
     * Добавление сущности
     *
     * @param type - DTO с данными для создания
     * @return DTO сущности
     */
    D add(D type);

    /**
     * Поиск сущности по id
     *
     * @param id идентификатор сущности
     * @return DTO сущности
     */
    D get(I id);

    /**
     * Редактирование сущности
     *
     * @param id   идентификатор сущности
     * @param edit DTO с данными для редактирования
     * @return DTO сущности
     */
    D update(I id, D edit);

    /**
     * Удаление сущности
     *
     * @param id идентификатор сущности
     */
    void delete(I id);

    /**
     * Получение всех сущностей
     *
     * @return Список всех сущностей
     */
    List<D> getAll();

    /**
     * Получение списка сущностей по заданным критериям
     *
     * @param filter - объект с критериями для выборки
     * @return Список сущностей, соответствующих заданным критериям
     */
    List<D> search(F filter);
}
