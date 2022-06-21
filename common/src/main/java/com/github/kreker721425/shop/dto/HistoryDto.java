package com.github.kreker721425.shop.dto;

import com.github.kreker721425.shop.db.enums.OperationEnum;
import com.github.kreker721425.shop.db.enums.TableEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoryDto {
    private Long id;
    private TableEnum tableName;
    private OperationEnum operation;
    private String oldValue;
    private String newValue;
    private LocalDateTime createdAt;
    private UserDto user;
}
