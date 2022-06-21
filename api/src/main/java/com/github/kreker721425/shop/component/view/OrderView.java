package com.github.kreker721425.shop.component.view;

import com.github.kreker721425.shop.component.HeaderLayout;
import com.github.kreker721425.shop.dto.OrderDto;
import com.github.kreker721425.shop.dto.ProductOrderDto;
import com.github.kreker721425.shop.repository.filter.OrderFilter;
import com.github.kreker721425.shop.service.OrderService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.security.PermitAll;
import java.math.BigDecimal;
import java.util.Objects;

@Component
@Scope("prototype")
@Route(value = "/orders", layout = HeaderLayout.class)
@PageTitle("Заказы | Shop")
@PermitAll
public class OrderView extends VerticalLayout {

    private final Grid<OrderDto> grid = new Grid<>(OrderDto.class);
    private final DateTimePicker filterCreatedTo = new DateTimePicker();
    private final DateTimePicker filterCreatedFrom = new DateTimePicker();
    private final TextField filterClientPhone = new TextField();
    private final OrderService orderService;

    public OrderView(OrderService userService) {
        this.orderService = userService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        Dialog dialog = new Dialog();
        dialog.getElement().setAttribute("aria-label", "Add note");

        dialog.setDraggable(true);
        dialog.setResizable(true);

        grid.addItemClickListener(item -> {
            if (Objects.isNull(item.getItem())) {
                return;
            }
            HorizontalLayout horizontalLayout = new HorizontalLayout();

            Button closeButton = new Button(new Icon("lumo", "cross"), e -> {
                dialog.close();
                dialog.removeAll();
            });
            closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            horizontalLayout.add(closeButton);
            horizontalLayout.setAlignItems(FlexComponent.Alignment.END);

            var layout = new VerticalLayout(horizontalLayout, createDialogContent(item.getItem()));
            layout.setSizeFull();

            dialog.add(layout);
            dialog.open();
        });

        dialog.setHeight("50em");
        dialog.setWidth("60em");
        dialog.addDialogCloseActionListener(e -> {
            dialog.close();
            dialog.removeAll();
        });

        add(getToolbar(), grid, dialog);
        updateList();
    }

    private static Grid<ProductOrderDto> createDialogContent(OrderDto orderDto) {
        Grid<ProductOrderDto> gridProducts = new Grid<>(ProductOrderDto.class);
        gridProducts.addClassNames("contact-grid");
        gridProducts.setSizeFull();
        gridProducts.removeAllColumns();
        gridProducts.addColumn(productOrder -> productOrder.getProduct().getArticle()).setHeader("Артикул");
        gridProducts.addColumn(productOrder -> productOrder.getProduct().getName()).setHeader("Название");
        gridProducts.addColumn(ProductOrderDto::getCount).setHeader("Кол-во");
        gridProducts.addColumn(productOrder -> productOrder.getProduct().getPriceDiscount()).setHeader("Цена");
        gridProducts.addColumn(productOrder ->
                        productOrder.getProduct().getPriceDiscount().multiply(new BigDecimal(productOrder.getCount())))
                .setHeader("Стоимость");
        gridProducts.getColumns().forEach(col -> col.setAutoWidth(true));
        gridProducts.getColumns().forEach(column -> column.setSortable(true));

        gridProducts.setItems(orderDto.getProducts());

        return gridProducts;
    }

    private void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.removeAllColumns();
        grid.addColumn(new LocalDateTimeRenderer<>(OrderDto::getCreatedAt, "dd.MM.yyyy :: HH:mm:ss"))
                .setHeader("Дата создания");
        grid.addColumn(OrderDto::getPrice).setHeader("Стоимость");
        grid.addColumn(OrderDto::getBonusCount).setHeader("Кол-во бонусов");
        grid.addColumn(order -> Objects.nonNull(order.getUser()) ? order.getUser().getName() : StringUtils.EMPTY)
                .setHeader("Имя пользователя");
        grid.addColumn(order -> Objects.nonNull(order.getClient()) ? order.getClient().getPhone() : StringUtils.EMPTY)
                .setHeader("Телефон клиента");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.getColumns().forEach(column -> column.setSortable(true));
    }

    private HorizontalLayout getToolbar() {

        filterCreatedFrom.setDatePlaceholder("Создан от");
        filterCreatedFrom.addValueChangeListener(e -> filterCreatedTo.setMin(e.getValue()));

        filterCreatedTo.setDatePlaceholder("Создан по");

        filterClientPhone.setPlaceholder("Телефон клиента");
        filterClientPhone.setClearButtonVisible(true);

        var filterButtonSearch = new Button("Поиск");
        filterButtonSearch.addClickShortcut(Key.ENTER);
        filterButtonSearch.addClickListener(event -> updateList());

        var filterButtonClear = new Button("Сбросить");
        filterButtonClear.addClickShortcut(Key.ESCAPE);
        filterButtonClear.addClickListener(event -> clearFilter());

        var toolbar = new HorizontalLayout(filterCreatedFrom, filterCreatedTo, filterClientPhone,
                filterButtonSearch, filterButtonClear);
        toolbar.addClassName("toolbar");
        toolbar.setWidthFull();
        return toolbar;
    }

    private void clearFilter() {
        filterCreatedFrom.setValue(null);
        filterCreatedTo.setValue(null);
        filterClientPhone.setValue(StringUtils.EMPTY);
        updateList();
    }

    private void updateList() {
        var filter = OrderFilter.builder()
                .clientPhone(filterClientPhone.getValue())
                .createdAtStart(filterCreatedFrom.getValue())
                .createdAtEnd(filterCreatedTo.getValue())
                .build();
        grid.setItems(orderService.search(filter));
    }
}
