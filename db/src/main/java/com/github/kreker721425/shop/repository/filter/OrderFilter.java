package com.github.kreker721425.shop.repository.filter;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class OrderFilter extends Filter {
    private String id;
    private String clientName;
    private LocalDate createdAtStart;
    private LocalDate createdAtEnd;
}