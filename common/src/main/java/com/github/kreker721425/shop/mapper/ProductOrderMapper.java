package com.github.kreker721425.shop.mapper;

import com.github.kreker721425.shop.db.tables.pojos.ProductOrder;
import com.github.kreker721425.shop.dto.ProductOrderDto;
import com.github.kreker721425.shop.service.ProductService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ProductOrderMapper {

    @Autowired
    @Lazy
    protected ProductService productService;

    @Mapping(target = "product",
            expression = "java(productService.get(productOrder.getProductId()))")
    public abstract ProductOrderDto map(ProductOrder productOrder);

    public abstract List<ProductOrderDto> map(List<ProductOrder> productOrder);

    @Mapping(target = "productId",
            expression = "java(dto.getProduct().getId())")
    public abstract ProductOrder mapToEntity(ProductOrderDto dto);
}