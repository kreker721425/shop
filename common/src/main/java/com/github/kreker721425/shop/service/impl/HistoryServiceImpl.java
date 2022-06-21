package com.github.kreker721425.shop.service.impl;

import com.github.kreker721425.shop.dto.HistoryDto;
import com.github.kreker721425.shop.exception.ObjectNotFoundException;
import com.github.kreker721425.shop.mapper.HistoryMapper;
import com.github.kreker721425.shop.repository.HistoryRepository;
import com.github.kreker721425.shop.repository.filter.HistoryFilter;
import com.github.kreker721425.shop.security.SecurityService;
import com.github.kreker721425.shop.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository historyRepository;
    private final HistoryMapper historyMapper;
    private final SecurityService securityService;

    @Override
    public HistoryDto add(HistoryDto type) {
        type.setUser(securityService.getAuthenticatedUser());
        if (Objects.nonNull(type.getId())) {
            return update(type.getId(), type);
        }
        var history = historyMapper.mapToEntity(type);
        historyRepository.insert(history);

        return historyMapper.map(history);
    }

    @Override
    public HistoryDto get(Long id) {
        var history = historyRepository.findById(id);
        if (history == null) {
            throw new ObjectNotFoundException(String.format("История не найдена. id = %s", id));
        }
        return historyMapper.map(history);
    }

    @Override
    public HistoryDto update(Long id, HistoryDto edit) {
        edit.setUser(securityService.getAuthenticatedUser());
        var history = historyMapper.mapToEntity(edit);
        history.setId(id);
        historyRepository.update(history);

        return historyMapper.map(history);
    }

    @Override
    public void delete(Long id) {
        historyRepository.deleteById(id);
    }

    @Override
    public List<HistoryDto> getAll() {
        return historyMapper.map(historyRepository.findAll());
    }

    @Override
    public List<HistoryDto> search(HistoryFilter filter) {
        return historyMapper.map(historyRepository.search(filter));
    }
}
