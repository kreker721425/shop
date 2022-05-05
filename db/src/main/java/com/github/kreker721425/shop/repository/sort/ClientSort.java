package com.github.kreker721425.shop.repository.sort;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ClientSort extends Sort {
    private Boolean id;
    private Boolean name;
    private Boolean phone;
    private Boolean bonusCount;
    private Boolean birthday;
}
