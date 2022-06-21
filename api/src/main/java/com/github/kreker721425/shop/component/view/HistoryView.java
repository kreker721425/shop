package com.github.kreker721425.shop.component.view;

import com.github.kreker721425.shop.component.HeaderLayout;
import com.github.kreker721425.shop.db.enums.OperationEnum;
import com.github.kreker721425.shop.db.enums.TableEnum;
import com.github.kreker721425.shop.dto.ClientDto;
import com.github.kreker721425.shop.dto.HistoryDto;
import com.github.kreker721425.shop.repository.filter.HistoryFilter;
import com.github.kreker721425.shop.service.HistoryService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.security.RolesAllowed;
import java.util.Objects;
import java.util.stream.Stream;

@Component
@Scope("prototype")
@Route(value = "/history", layout = HeaderLayout.class)
@PageTitle("История | Shop")
@RolesAllowed(value = "ROLE_ADMIN")
public class HistoryView extends VerticalLayout {

    private final Grid<HistoryDto> grid = new Grid<>(HistoryDto.class);
    private final ComboBox<TableEnum> filterTable = new ComboBox<>();
    private final ComboBox<OperationEnum> filterOperation = new ComboBox<>();
    private final DateTimePicker filterCreatedTo = new DateTimePicker();
    private final DateTimePicker filterCreatedFrom = new DateTimePicker();
    private final TextField filterUser = new TextField();
    private final HistoryService historyService;

    public HistoryView(HistoryService userService) {
        this.historyService = userService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        add(getToolbar(), grid);
        updateList();
    }

    private void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.removeAllColumns();
        grid.addColumn(HistoryDto::getTableName).setHeader("Таблица");
        grid.addColumn(HistoryDto::getOperation).setHeader("Операция");
        grid.addColumn(new LocalDateTimeRenderer<>(HistoryDto::getCreatedAt, "dd.MM.yyyy :: HH:mm:ss")).setHeader("Время создания");
        grid.addColumn(history -> history.getUser().getName()).setHeader("Имя пользователя");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.getColumns().forEach(column -> column.setSortable(true));
    }

    private HorizontalLayout getToolbar() {
        filterTable.setPlaceholder("Таблица");
        filterTable.setClearButtonVisible(true);
        filterTable.setItems(TableEnum.values());
        filterTable.setItemLabelGenerator(TableEnum::getLiteral);
        filterTable.setWidth("9em");

        filterOperation.setPlaceholder("Операция");
        filterOperation.setClearButtonVisible(true);
        filterOperation.setItems(OperationEnum.values());
        filterOperation.setItemLabelGenerator(OperationEnum::getLiteral);
        filterOperation.setWidth("9em");

        filterCreatedFrom.setDatePlaceholder("Создан от");
        filterCreatedFrom.addValueChangeListener(e -> filterCreatedTo.setMin(e.getValue()));
        filterCreatedFrom.setWidth("16em");

        filterCreatedTo.setDatePlaceholder("Создан по");
        filterCreatedTo.setWidth("16em");

        filterUser.setPlaceholder("Пользователь");
        filterUser.setClearButtonVisible(true);
        filterUser.setWidth("20em");


        var filterButtonSearch = new Button("Поиск");
        filterButtonSearch.addClickShortcut(Key.ENTER);
        filterButtonSearch.addClickListener(event -> updateList());

        var filterButtonClear = new Button("Сбросить");
        filterButtonClear.addClickShortcut(Key.ESCAPE);
        filterButtonClear.addClickListener(event -> clearFilter());

        var toolbar = new HorizontalLayout(filterTable, filterOperation, filterCreatedFrom,
                filterCreatedTo, filterUser, filterButtonSearch, filterButtonClear);
        toolbar.addClassName("toolbar");
        toolbar.setWidthFull();
        return toolbar;
    }

    private void clearFilter() {
        filterTable.setValue(null);
        filterOperation.setValue(null);
        filterCreatedFrom.setValue(null);
        filterCreatedTo.setValue(null);
        filterUser.setValue(StringUtils.EMPTY);
        updateList();
    }

    private void updateList() {
        var filter = HistoryFilter.builder()
                .tableName(filterTable.getValue())
                .operation(filterOperation.getValue())
                .createdAtStart(filterCreatedFrom.getValue())
                .createdAtEnd(filterCreatedTo.getValue())
                .user(filterUser.getValue())
                .build();
        grid.setItems(historyService.search(filter));
        grid.setItemDetailsRenderer(createHistoryDetailsRenderer());
    }

    private static ComponentRenderer<HistoryDetailsForm, HistoryDto> createHistoryDetailsRenderer() {
        return new ComponentRenderer<>(HistoryDetailsForm::new, HistoryDetailsForm::setHistory);
    }

    private static class HistoryDetailsForm extends FormLayout {
        private final TextField oldValueField = new TextField("Старое значение");
        private final TextField newValueField = new TextField("Новое значение");

        public HistoryDetailsForm() {
            Stream.of(oldValueField, newValueField).forEach(field -> {
                field.setReadOnly(true);
                add(field);
            });

            setResponsiveSteps(new ResponsiveStep("0", 3));
            setColspan(oldValueField, 3);
            setColspan(newValueField, 3);
        }

        public void setHistory(HistoryDto history) {
            oldValueField.setValue(Objects.isNull(history.getOldValue()) ? StringUtils.EMPTY : history.getOldValue());
            newValueField.setValue(Objects.isNull(history.getNewValue()) ? StringUtils.EMPTY : history.getNewValue());
        }
    }
}
