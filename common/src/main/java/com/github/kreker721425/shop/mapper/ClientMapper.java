package com.github.kreker721425.shop.mapper;

import com.github.kreker721425.shop.db.tables.pojos.Client;
import com.github.kreker721425.shop.dto.ClientDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientDto map(Client client);

    List<ClientDto> map(List<Client> clients);

    Client mapToEntity(ClientDto dto);
}
