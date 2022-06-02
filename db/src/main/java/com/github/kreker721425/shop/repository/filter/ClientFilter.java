package com.github.kreker721425.shop.repository.filter;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class ClientFilter extends Filter {
    private String id;
    private String name;
    private String phone;
    private LocalDate birthday;
}
