package com.github.kreker721425.shop.repository.filter;

import com.github.kreker721425.shop.db.enums.OperationEnum;
import com.github.kreker721425.shop.db.enums.TableEnum;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class HistoryFilter extends Filter {
    private String id;
    private TableEnum tableName;
    private OperationEnum operation;
    private LocalDateTime createdAtStart;
    private LocalDateTime createdAtEnd;
    private String user;
}
