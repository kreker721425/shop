package com.github.kreker721425.shop.repository.filter;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClientFilter extends Filter {
    private List<String> id = new ArrayList<>();
    private List<String> name = new ArrayList<>();
    private List<String> phone = new ArrayList<>();
    private LocalDate birthday;
}
