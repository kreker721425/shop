package com.github.kreker721425.shop.mapper;

import com.github.kreker721425.shop.db.tables.pojos.Product;
import com.github.kreker721425.shop.dto.ProductDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto map(Product client);

    List<ProductDto> map(List<Product> clients);

    Product mapToEntity(ProductDto dto);

    List<Product> mapToEntity(List<ProductDto> dto);
}
