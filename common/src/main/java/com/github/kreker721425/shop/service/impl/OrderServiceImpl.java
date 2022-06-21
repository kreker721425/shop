package com.github.kreker721425.shop.service.impl;

import com.github.kreker721425.shop.db.enums.OperationEnum;
import com.github.kreker721425.shop.db.enums.TableEnum;
import com.github.kreker721425.shop.db.tables.pojos.ProductOrder;
import com.github.kreker721425.shop.dto.HistoryDto;
import com.github.kreker721425.shop.dto.OrderDto;
import com.github.kreker721425.shop.exception.ObjectNotFoundException;
import com.github.kreker721425.shop.mapper.OrderMapper;
import com.github.kreker721425.shop.repository.OrderRepository;
import com.github.kreker721425.shop.repository.ProductOrderRepository;
import com.github.kreker721425.shop.repository.filter.OrderFilter;
import com.github.kreker721425.shop.service.HistoryService;
import com.github.kreker721425.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final HistoryService historyService;
    private final ProductOrderRepository productOrderRepository;

    @Override
    public OrderDto add(OrderDto type) {
        if (Objects.nonNull(type.getId())) {
            return update(type.getId(), type);
        }
        var order = orderMapper.mapToEntity(type);
        orderRepository.insert(order);

        type.getProducts().forEach(productOrderDto -> {
            var productOrder = new ProductOrder();
            productOrder.setOrderId(order.getId());
            productOrder.setProductId(productOrderDto.getProduct().getId());
            productOrder.setCount(productOrderDto.getCount());
            productOrderRepository.insert(productOrder);
        });
        return orderMapper.map(order);
    }

    @Override
    public OrderDto get(Long id) {
        var order = orderRepository.findById(id);
        if (order == null) {
            throw new ObjectNotFoundException(String.format("Заказ не найден. id = %s", id));
        }
        return orderMapper.map(order);
    }

    @Override
    public OrderDto update(Long id, OrderDto edit) {
        var newOrder = orderMapper.mapToEntity(edit);
        newOrder.setId(id);
        var oldOrderDto = get(id);
        var newOrderDto = orderMapper.map(newOrder);
        orderRepository.update(newOrder);
        historyService.add(HistoryDto.builder()
                .tableName(TableEnum.ORDER)
                .operation(OperationEnum.PUT)
                .oldValue(oldOrderDto.toString())
                .newValue(newOrderDto.toString())
                .build());

        return newOrderDto;
    }

    @Override
    public void delete(Long id) {
        var order = get(id);
        orderRepository.deleteById(id);
        historyService.add(HistoryDto.builder()
                .tableName(TableEnum.ORDER)
                .operation(OperationEnum.DELETE)
                .oldValue(order.toString())
                .build());
    }

    @Override
    public List<OrderDto> getAll() {
        return orderMapper.map(orderRepository.findAll());
    }

    @Override
    public List<OrderDto> search(OrderFilter filter) {
        return orderMapper.map(orderRepository.search(filter));
    }
}
