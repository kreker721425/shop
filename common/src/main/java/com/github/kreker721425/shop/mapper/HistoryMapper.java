package com.github.kreker721425.shop.mapper;

import com.github.kreker721425.shop.db.tables.pojos.History;
import com.github.kreker721425.shop.dto.HistoryDto;
import com.github.kreker721425.shop.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class HistoryMapper {

    @Autowired
    @Lazy
    protected UserService userService;

    @Mapping(target = "user",
            expression = "java(userService.get(history.getUserId()))")
    public abstract HistoryDto map(History history);

    public abstract List<HistoryDto> map(List<History> histories);

    @Mapping(target = "userId",
            expression = "java(dto.getUser().getId())")
    @Mapping(target = "createdAt",
            expression = "java(java.time.LocalDateTime.now())")
    public abstract History mapToEntity(HistoryDto dto);
}
