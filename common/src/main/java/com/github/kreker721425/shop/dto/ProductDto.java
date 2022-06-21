package com.github.kreker721425.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long id;
    @NotEmpty(message = "Введите номер телефона")
    @Pattern(regexp = "^\\d{1,30}$", message = "Здесь могут быть только цифры")
    private String article;
    @NotEmpty(message = "Введите название")
    private String name;
    private Integer count;
    private String description;
    @NotNull(message = "Введите цену")
    private BigDecimal price;
    private BigDecimal priceDiscount;
}
