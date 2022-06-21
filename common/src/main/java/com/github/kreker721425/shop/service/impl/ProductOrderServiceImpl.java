package com.github.kreker721425.shop.service.impl;

import com.github.kreker721425.shop.db.enums.OperationEnum;
import com.github.kreker721425.shop.db.enums.TableEnum;
import com.github.kreker721425.shop.dto.HistoryDto;
import com.github.kreker721425.shop.dto.ProductOrderDto;
import com.github.kreker721425.shop.exception.ObjectNotFoundException;
import com.github.kreker721425.shop.mapper.ProductOrderMapper;
import com.github.kreker721425.shop.repository.ProductOrderRepository;
import com.github.kreker721425.shop.repository.filter.ProductOrderFilter;
import com.github.kreker721425.shop.service.HistoryService;
import com.github.kreker721425.shop.service.ProductOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProductOrderServiceImpl implements ProductOrderService {

    private final ProductOrderRepository productOrderRepository;
    private final ProductOrderMapper productOrderMapper;
    private final HistoryService historyService;

    @Override
    public ProductOrderDto add(ProductOrderDto type) {
        if (Objects.nonNull(type.getId())) {
            return update(type.getId(), type);
        }
        var productOrder = productOrderMapper.mapToEntity(type);
        productOrderRepository.insert(productOrder);
        return productOrderMapper.map(productOrder);
    }

    @Override
    public ProductOrderDto get(Long id) {
        var productOrder = productOrderRepository.findById(id);
        if (productOrder == null) {
            throw new ObjectNotFoundException(String.format("Заказ не найден. id = %s", id));
        }
        return productOrderMapper.map(productOrder);
    }

    @Override
    public ProductOrderDto update(Long id, ProductOrderDto edit) {
        var productOrder = productOrderMapper.mapToEntity(edit);
        productOrder.setId(id);
        var oldProductOrderDto = get(id);
        var newProductOrderDto = productOrderMapper.map(productOrder);
        productOrderRepository.update(productOrder);
        historyService.add(HistoryDto.builder()
                .tableName(TableEnum.CLIENT)
                .operation(OperationEnum.PUT)
                .oldValue(oldProductOrderDto.toString())
                .newValue(newProductOrderDto.toString())
                .build());
        return newProductOrderDto;
    }

    @Override
    public void delete(Long id) {
        var productOrder = get(id);
        productOrderRepository.deleteById(id);
        historyService.add(HistoryDto.builder()
                .tableName(TableEnum.PRODUCT)
                .operation(OperationEnum.DELETE)
                .oldValue(productOrder.toString())
                .build());
    }

    @Override
    public List<ProductOrderDto> getAll() {
        return productOrderMapper.map(productOrderRepository.findAll());
    }

    @Override
    public List<ProductOrderDto> search(ProductOrderFilter filter) {
        return productOrderMapper.map(productOrderRepository.search(filter));
    }

    @Override
    public List<ProductOrderDto> getByOrderId(Long orderId) {
        return productOrderMapper.map(productOrderRepository.fetchByOrderId(orderId));
    }
}
