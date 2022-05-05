package com.github.kreker721425.shop.repository.filter;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderFilter extends Filter {
    private List<String> id = new ArrayList<>();
    private List<String> clientName = new ArrayList<>();
    private LocalDate createdAtStart;
    private LocalDate createdAtEnd;
}