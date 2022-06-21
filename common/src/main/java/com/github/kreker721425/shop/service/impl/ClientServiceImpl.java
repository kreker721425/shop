package com.github.kreker721425.shop.service.impl;

import com.github.kreker721425.shop.db.enums.OperationEnum;
import com.github.kreker721425.shop.db.enums.TableEnum;
import com.github.kreker721425.shop.db.tables.pojos.Client;
import com.github.kreker721425.shop.dto.ClientDto;
import com.github.kreker721425.shop.dto.HistoryDto;
import com.github.kreker721425.shop.exception.ClientException;
import com.github.kreker721425.shop.exception.ObjectNotFoundException;
import com.github.kreker721425.shop.mapper.ClientMapper;
import com.github.kreker721425.shop.repository.ClientRepository;
import com.github.kreker721425.shop.repository.filter.ClientFilter;
import com.github.kreker721425.shop.service.ClientService;
import com.github.kreker721425.shop.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final HistoryService historyService;

    @Override
    public ClientDto add(ClientDto type) {
        if (Objects.nonNull(type.getId())) {
            return update(type.getId(), type);
        }
        checkPhone(type.getPhone());
        type.setBonusCount(new BigDecimal("0"));
        var client = clientMapper.mapToEntity(type);
        var clientDto = clientMapper.map(client);
        clientRepository.insert(client);
        historyService.add(HistoryDto.builder()
                .tableName(TableEnum.CLIENT)
                .operation(OperationEnum.ADD)
                .newValue(clientDto.toString())
                .build());

        return clientDto;
    }

    @Override
    public ClientDto get(Long id) {
        return clientMapper.map(findById(id));
    }

    @Override
    public ClientDto update(Long id, ClientDto edit) {
        var newClient = clientMapper.mapToEntity(edit);
        newClient.setId(id);
        var oldClientDto = get(id);
        var newClientDto = clientMapper.map(newClient);
        if (!StringUtils.equalsIgnoreCase(edit.getPhone(), oldClientDto.getPhone())) {
            checkPhone(edit.getPhone());
        }
        clientRepository.update(newClient);
        historyService.add(HistoryDto.builder()
                .tableName(TableEnum.CLIENT)
                .operation(OperationEnum.PUT)
                .oldValue(oldClientDto.toString())
                .newValue(newClientDto.toString())
                .build());

        return newClientDto;
    }

    @Override
    public void delete(Long id) {
        var client = get(id);
        clientRepository.deleteById(id);
        historyService.add(HistoryDto.builder()
                .tableName(TableEnum.CLIENT)
                .operation(OperationEnum.DELETE)
                .oldValue(client.toString())
                .build());
    }

    @Override
    public List<ClientDto> getAll() {
        return clientMapper.map(clientRepository.findAll());
    }

    @Override
    public List<ClientDto> search(ClientFilter filter) {
        return clientMapper.map(clientRepository.search(filter));
    }

    @Override
    public ClientDto getByPhone(String phone) {
        var clients = clientRepository.fetchByPhone(phone);
        if (CollectionUtils.isEmpty(clients)) {
            throw new ObjectNotFoundException("Клиент не найден");
        }
        return clientMapper.map(clients.get(0));
    }

    @Override
    public void updateBonusCount(Long id, BigDecimal bonusCount) {
        var client = findById(id);
        client.setBonusCount(bonusCount);
        clientRepository.update(client);
    }

    private void checkPhone(String phone) {
        var client = clientRepository.fetchByPhone(phone);
        if (!CollectionUtils.isEmpty(client)) {
            throw new ClientException("Такой номер телефона уже зарегистрирован");
        }
    }

    private Client findById(Long id) {
        var client = clientRepository.findById(id);
        if (client == null) {
            throw new ObjectNotFoundException(String.format("Клиент не найден. id = %s", id));
        }
        return client;
    }
}
