package com.github.kreker721425.shop.repository.filter;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class OrderFilter extends Filter {
    private String id;
    private String clientPhone;
    private LocalDateTime createdAtStart;
    private LocalDateTime createdAtEnd;
}