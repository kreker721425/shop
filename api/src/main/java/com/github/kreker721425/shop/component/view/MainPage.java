package com.github.kreker721425.shop.component.view;

import com.github.kreker721425.shop.component.HeaderLayout;
import com.github.kreker721425.shop.dto.ClientDto;
import com.github.kreker721425.shop.dto.OrderDto;
import com.github.kreker721425.shop.dto.ProductDto;
import com.github.kreker721425.shop.dto.ProductOrderDto;
import com.github.kreker721425.shop.repository.filter.ProductFilter;
import com.github.kreker721425.shop.security.SecurityService;
import com.github.kreker721425.shop.service.ClientService;
import com.github.kreker721425.shop.service.OrderService;
import com.github.kreker721425.shop.service.ProductService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.security.PermitAll;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@Scope("prototype")
@Route(value = "/", layout = HeaderLayout.class)
@PageTitle("Оформление заказа | Shop")
@PermitAll
public class MainPage extends Div {
    private ClientDto clientDto;
    private BigDecimal total;
    private BigDecimal bonusCount;
    private final ProductService productService;
    private final OrderService orderService;
    private final ClientService clientService;
    private final SecurityService securityService;
    private List<ProductDto> allProducts;
    private List<ProductOrderDto> orderProducts;

    private final TextArea name = new TextArea("Имя клиента");
    private final TextField birthday = new TextField("Дата рождения");
    private final TextField bonuses = new TextField("Количество бонусов");
    private final TextField number = new TextField("Введите номер телефона");
    private final IntegerField countBuy = new IntegerField("Списать бонусов");

    private final TextField filterArticle = new TextField();
    private final TextField filterName = new TextField();

    private final TextField totalField = new TextField("Итого стоимость");
    private final Grid<ProductDto> gridProducts = new Grid<>(ProductDto.class);
    private final Grid<ProductOrderDto> gridOrder = new Grid<>(ProductOrderDto.class);

    public MainPage(ProductService productService, OrderService orderService, ClientService clientService, SecurityService securityService) {
        this.productService = productService;
        this.orderService = orderService;
        this.clientService = clientService;
        this.securityService = securityService;
        allProducts = productService.getAll();
        orderProducts = new ArrayList<>();
        updateList();
        gridProducts.addItemDoubleClickListener(e -> {
            if (e.getItem().getCount() < 1) {
                var notification = Notification.show("Товар отсутствует", 5000, Notification.Position.TOP_END);
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                return;
            }
            allProducts.remove(e.getItem());
            orderProducts.add(ProductOrderDto.builder()
                    .product(e.getItem())
                    .count(1L)
                    .build());
            updateList();
        });
        gridProducts.setHeight("21em");
        gridProducts.removeAllColumns();
        gridProducts.addColumn(ProductDto::getArticle).setHeader("Артикул");
        gridProducts.addColumn(ProductDto::getName).setHeader("Название");
        gridProducts.addColumn(ProductDto::getCount).setHeader("Кол-во");
        gridProducts.addColumn(ProductDto::getPrice).setHeader("Цена");
        gridProducts.addColumn(ProductDto::getPriceDiscount).setHeader("Цена со скидкой");
        gridProducts.getColumns().forEach(column -> column.setSortable(true));
        gridProducts.getColumns().forEach(col -> col.setAutoWidth(true));


        gridOrder.addItemDoubleClickListener(e -> {
            allProducts.add(e.getItem().getProduct());
            orderProducts.remove(e.getItem());
            updateList();
        });

        gridOrder.setHeight("15em");
        gridOrder.removeAllColumns();
        gridOrder.addColumn(productOrderDto -> productOrderDto.getProduct().getArticle()).setHeader("Артикул");
        gridOrder.addColumn(productOrderDto -> productOrderDto.getProduct().getName()).setHeader("Название");
        gridOrder.addColumn(productOrderDto -> productOrderDto.getProduct().getPriceDiscount()).setHeader("Цена");
        gridOrder.addColumn(ProductOrderDto::getCount).setHeader("Кол-во");
        gridOrder.addColumn(productOrderDto ->
                productOrderDto.getProduct().getPriceDiscount()
                        .multiply(new BigDecimal(productOrderDto.getCount()))).setHeader("Стоимость");
        gridOrder.getColumns().forEach(column -> column.setSortable(true));
        gridOrder.getColumns().forEach(col -> col.setAutoWidth(true));

        var contextMenu = new ContextMenu(gridOrder);
        VerticalLayout verticalLayout = new VerticalLayout(getToolbar(), gridProducts, gridOrder);

        VerticalLayout clientForm = new VerticalLayout();
        clientForm.getThemeList().remove("spacing");
        clientForm.getThemeList().add("spacing-xs");
        clientForm.setAlignItems(FlexComponent.Alignment.STRETCH);


        name.setReadOnly(true);
        name.setMinHeight("100px");
        birthday.setReadOnly(true);
        bonuses.setReadOnly(true);
        totalField.setReadOnly(true);
        countBuy.setValue(0);

        Button buyButton = new Button("Оформить заказ");
        buyButton.addClickListener(event -> save());
        buyButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button searchButton = new Button("Поиск");

        searchButton.addClickListener(event -> {
            if (StringUtils.isEmpty(number.getValue())) {
                number.setInvalid(true);
                number.setErrorMessage("Введите номер");
                return;
            }
            number.setInvalid(false);
            try {
                clientDto = clientService.getByPhone(number.getValue());
                name.setValue(clientDto.getName());
                birthday.setValue(clientDto.getBirthday().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                bonuses.setValue(clientDto.getBonusCount().toString());
            } catch (Exception e) {
                clientDto = null;
                name.setValue(StringUtils.EMPTY);
                birthday.setValue(StringUtils.EMPTY);
                bonuses.setValue(StringUtils.EMPTY);
                var notification = Notification.show(e.getMessage(), 5000, Notification.Position.TOP_END);
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
        clientForm.add(number, searchButton, name, birthday, bonuses, totalField, countBuy, buyButton);
        clientForm.setWidth("20em");
        clientForm.setMargin(false);


        HorizontalLayout horizontalLayout = new HorizontalLayout(verticalLayout, clientForm);

        add(horizontalLayout, contextMenu);
    }

    private HorizontalLayout getToolbar() {
        filterName.setPlaceholder("Название");
        filterName.setClearButtonVisible(true);
        filterName.setValueChangeMode(ValueChangeMode.LAZY);

        filterArticle.setPlaceholder("Артикул");
        filterArticle.setClearButtonVisible(true);
        filterArticle.setValueChangeMode(ValueChangeMode.LAZY);

        var filterButtonSearch = new Button("Поиск");
        filterButtonSearch.addClickShortcut(Key.ENTER);
        filterButtonSearch.addClickListener(event -> updateList());

        var filterButtonClear = new Button("Сбросить");
        filterButtonClear.addClickShortcut(Key.ESCAPE);
        filterButtonClear.addClickListener(event -> clearFilter());

        var toolbar = new HorizontalLayout(filterArticle, filterName, filterButtonSearch, filterButtonClear);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void clearFilter() {
        filterName.setValue(StringUtils.EMPTY);
        filterArticle.setValue(StringUtils.EMPTY);
        updateList();
    }

    private void updateList() {
        total = orderProducts.stream()
                .map(productOrder -> productOrder.getProduct().getPriceDiscount()
                        .multiply(new BigDecimal(productOrder.getCount())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
        var date = LocalDate.now();
        if (!Objects.isNull(clientDto) && clientDto.getBirthday().getDayOfMonth() == date.getDayOfMonth()
                && clientDto.getBirthday().getMonthValue() == date.getMonthValue()) {
            bonusCount = total.multiply(new BigDecimal("0.01"));
        } else {
            bonusCount = total.multiply(new BigDecimal("0.001"));
        }
        totalField.setValue(total.toString());

        var filter = ProductFilter.builder()
                .article(filterArticle.getValue())
                .name(filterName.getValue())
                .build();
        allProducts = productService.search(filter);
        orderProducts.stream()
                .filter(orderProduct -> allProducts.contains(orderProduct.getProduct()))
                .forEach(orderProduct -> allProducts.remove(orderProduct.getProduct()));
        gridProducts.setItems(allProducts);
        gridOrder.setItems(orderProducts);
    }

    private void save() {
        if (total.compareTo(new BigDecimal("0")) == 0) {
            var notification = Notification.show("Корзина пуста", 5000, Notification.Position.TOP_END);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            return;
        }
        var isActivatedBonuses = false;
        var countBonusBuy = countBuy.getValue();
        if (!Objects.isNull(clientDto)) {
            if (Objects.nonNull(countBonusBuy) && countBonusBuy > 0) {
                if (clientDto.getBonusCount().compareTo(BigDecimal.valueOf(countBonusBuy)) >= 0) {
                    isActivatedBonuses = true;
                    clientService.updateBonusCount(clientDto.getId(), clientDto.getBonusCount().subtract(BigDecimal.valueOf(countBonusBuy)));
                } else {
                    var notification = Notification.show("У клиента меньше баллов", 5000, Notification.Position.TOP_END);
                    notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                    return;
                }
            } else {
                clientService.updateBonusCount(clientDto.getId(), clientDto.getBonusCount().add(bonusCount));
            }
        }
        orderService.add(OrderDto.builder()
                .createdAt(LocalDateTime.now())
                .price(isActivatedBonuses ? total.subtract(BigDecimal.valueOf(countBonusBuy)) : total)
                .bonusCount(isActivatedBonuses ? BigDecimal.valueOf(countBonusBuy).negate() : bonusCount)
                .client(clientDto)
                .user(securityService.getAuthenticatedUser())
                .products(orderProducts)
                .build());
        productService.updateCount(
                orderProducts.stream()
                        .map(orderProduct -> {
                            var product = orderProduct.getProduct();
                            product.setCount(product.getCount() - orderProduct.getCount().intValue());
                            return product;
                        })
                        .toList());
        clear();
    }

    private void clear() {
        clientDto = null;
        name.setValue(StringUtils.EMPTY);
        birthday.setValue(StringUtils.EMPTY);
        bonuses.setValue(StringUtils.EMPTY);
        total = null;
        bonusCount = null;
        allProducts = productService.getAll();
        orderProducts = new ArrayList<>();

        totalField.setValue(StringUtils.EMPTY);
        updateList();
    }

    private class ContextMenu extends GridContextMenu<ProductOrderDto> {
        public ContextMenu(Grid<ProductOrderDto> target) {
            super(target);

            addItem("Добавить 1", e -> e.getItem().ifPresent(productOrder -> {
                if (productOrder.getProduct().getCount() >= productOrder.getCount() + 1) {
                    productOrder.setCount(productOrder.getCount() + 1);
                    updateList();
                } else {
                    var notification = Notification.show("Нет такого количества товара", 5000, Notification.Position.TOP_END);
                    notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                }
            }));
            addItem("Добавить 3", e -> e.getItem().ifPresent(productOrder -> {
                if (productOrder.getProduct().getCount() >= productOrder.getCount() + 3) {
                    productOrder.setCount(productOrder.getCount() + 3);
                    updateList();
                } else {
                    var notification = Notification.show("Нет такого количества товара", 5000, Notification.Position.TOP_END);
                    notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                }
            }));
            addItem("Добавить 5", e -> e.getItem().ifPresent(productOrder -> {
                if (productOrder.getProduct().getCount() >= productOrder.getCount() + 5) {
                    productOrder.setCount(productOrder.getCount() + 5);
                    updateList();
                } else {
                    var notification = Notification.show("Нет такого количества товара", 5000, Notification.Position.TOP_END);
                    notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                }
            }));

            add(new Hr());

            addItem("Убрать 1", e -> e.getItem().ifPresent(productOrder -> {
                if (productOrder.getCount() > 1) {
                    productOrder.setCount(productOrder.getCount() - 1);
                    updateList();
                } else {
                    var notification = Notification.show("Нет такого количества товара", 5000, Notification.Position.TOP_END);
                    notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                }
            }));
            addItem("Убрать 3", e -> e.getItem().ifPresent(productOrder -> {
                if (productOrder.getCount() > 3) {
                    productOrder.setCount(productOrder.getCount() - 3);
                    updateList();
                } else {
                    var notification = Notification.show("Нет такого количества товара", 5000, Notification.Position.TOP_END);
                    notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                }
            }));
            addItem("Убрать 5", e -> e.getItem().ifPresent(productOrder -> {
                if (productOrder.getCount() > 5) {
                    productOrder.setCount(productOrder.getCount() - 5);
                    updateList();
                } else {
                    var notification = Notification.show("Нет такого количества товара", 5000, Notification.Position.TOP_END);
                    notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                }
            }));
        }
    }
}
