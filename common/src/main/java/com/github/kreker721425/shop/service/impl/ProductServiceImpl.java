package com.github.kreker721425.shop.service.impl;

import com.github.kreker721425.shop.db.enums.OperationEnum;
import com.github.kreker721425.shop.db.enums.TableEnum;
import com.github.kreker721425.shop.dto.HistoryDto;
import com.github.kreker721425.shop.dto.ProductDto;
import com.github.kreker721425.shop.exception.ObjectNotFoundException;
import com.github.kreker721425.shop.mapper.ProductMapper;
import com.github.kreker721425.shop.repository.ProductRepository;
import com.github.kreker721425.shop.repository.filter.ProductFilter;
import com.github.kreker721425.shop.service.HistoryService;
import com.github.kreker721425.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final HistoryService historyService;

    @Override
    public ProductDto add(ProductDto type) {
        if (Objects.nonNull(type.getId())) {
            return update(type.getId(), type);
        }
        var product = productMapper.mapToEntity(type);
        productRepository.insert(product);
        historyService.add(HistoryDto.builder()
                .tableName(TableEnum.PRODUCT)
                .operation(OperationEnum.ADD)
                .newValue(product.toString())
                .build());

        return productMapper.map(product);
    }

    @Override
    public ProductDto get(Long id) {
        var product = productRepository.findById(id);
        if (product == null) {
            throw new ObjectNotFoundException(String.format("Продукт не найден. id = %s", id));
        }
        return productMapper.map(product);
    }

    @Override
    public ProductDto update(Long id, ProductDto edit) {
        var newProduct = productMapper.mapToEntity(edit);
        newProduct.setId(id);
        var oldProductDto = get(id);
        var newProductDto = productMapper.map(newProduct);
        productRepository.update(newProduct);
        historyService.add(HistoryDto.builder()
                .tableName(TableEnum.PRODUCT)
                .operation(OperationEnum.PUT)
                .oldValue(oldProductDto.toString())
                .newValue(newProductDto.toString())
                .build());

        return newProductDto;
    }

    @Override
    public void delete(Long id) {
        var product = get(id);
        productRepository.deleteById(id);
        historyService.add(HistoryDto.builder()
                .tableName(TableEnum.PRODUCT)
                .operation(OperationEnum.DELETE)
                .oldValue(product.toString())
                .build());
    }

    @Override
    public List<ProductDto> getAll() {
        return productMapper.map(productRepository.findAll());
    }

    @Override
    public List<ProductDto> search(ProductFilter filter) {
        return productMapper.map(productRepository.search(filter));
    }

    @Override
    public void updateCount(List<ProductDto> products) {
        productRepository.update(productMapper.mapToEntity(products));
    }
}
