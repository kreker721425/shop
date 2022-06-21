package com.github.kreker721425.shop.mapper;

import com.github.kreker721425.shop.db.tables.pojos.Order;
import com.github.kreker721425.shop.dto.ClientDto;
import com.github.kreker721425.shop.dto.OrderDto;
import com.github.kreker721425.shop.exception.ObjectNotFoundException;
import com.github.kreker721425.shop.service.ClientService;
import com.github.kreker721425.shop.service.ProductOrderService;
import com.github.kreker721425.shop.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class OrderMapper {

    @Autowired
    @Lazy
    protected ProductOrderService productOrderService;
    @Autowired
    @Lazy
    protected ClientService clientService;
    @Autowired
    @Lazy
    protected UserService userService;

    @Mapping(target = "products",
            expression = "java(productOrderService.getByOrderId(order.getId()))")
    @Mapping(target = "client",
            expression = "java(getClient(order.getClientId()))")
    @Mapping(target = "user",
            expression = "java(userService.get(order.getUserId()))")
    public abstract OrderDto map(Order order);

    public abstract List<OrderDto> map(List<Order> orders);

    @Mapping(target = "clientId",
            expression = "java(dto.getClient() != null ? dto.getClient().getId() : null)")
    @Mapping(target = "userId",
            expression = "java(dto.getUser().getId())")
    public abstract Order mapToEntity(OrderDto dto);

    public ClientDto getClient(Long clientId) {
        try {
            return clientService.get(clientId);
        } catch (ObjectNotFoundException e) {
            return null;
        }
    }
}