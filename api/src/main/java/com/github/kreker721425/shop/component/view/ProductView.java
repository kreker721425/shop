package com.github.kreker721425.shop.component.view;

import com.github.kreker721425.shop.db.enums.UserRoleEnum;
import com.github.kreker721425.shop.dto.ProductDto;
import com.github.kreker721425.shop.repository.filter.ProductFilter;
import com.github.kreker721425.shop.security.SecurityService;
import com.github.kreker721425.shop.service.ProductService;
import com.github.kreker721425.shop.component.HeaderLayout;
import com.github.kreker721425.shop.component.form.ProductForm;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
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
@Route(value = "/products", layout = HeaderLayout.class)
@PageTitle("Продукты | Shop")
@PermitAll
public class ProductView extends VerticalLayout {

    private final Grid<ProductDto> grid = new Grid<>(ProductDto.class);
    private final TextField filterArticle = new TextField();
    private final TextField filterName = new TextField();
    private final IntegerField filterCount = new IntegerField();
    private final ProductForm form = new ProductForm();
    private final ProductService productService;
    private final SecurityService securityService;

    public ProductView(ProductService productService, SecurityService securityService) {
        this.productService = productService;
        this.securityService = securityService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        form.setWidth("25em");
        form.addListener(ProductForm.SaveEvent.class, this::save);
        form.addListener(ProductForm.CloseEvent.class, e -> closeEditor());

        var contextMenu = new ContextMenu(grid);

        var content = new FlexLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.setFlexShrink(0, form);
        content.addClassNames("content", "gap-m");
        content.setSizeFull();

        add(getToolbar(), content, contextMenu);
        updateList();
        closeEditor();
    }

    private void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.removeAllColumns();
        grid.addColumn(ProductDto::getArticle).setHeader("Артикул");
        grid.addColumn(ProductDto::getName).setHeader("Название");
        grid.addColumn(ProductDto::getCount).setHeader("Кол-во");
        grid.addColumn(ProductDto::getPrice).setHeader("Цена");
        grid.addColumn(ProductDto::getPriceDiscount).setHeader("Цена со скидкой");
        grid.addColumn(ProductDto::getDescription).setHeader("Описание");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.getColumns().forEach(column -> column.setSortable(true));
    }

    private HorizontalLayout getToolbar() {
        filterArticle.setPlaceholder("Артикул");
        filterArticle.setClearButtonVisible(true);
        filterArticle.setValueChangeMode(ValueChangeMode.LAZY);

        filterName.setPlaceholder("Название");
        filterName.setClearButtonVisible(true);
        filterName.setValueChangeMode(ValueChangeMode.LAZY);

        filterCount.setPlaceholder("Кол-во");
        filterCount.setClearButtonVisible(true);
        filterCount.setValueChangeMode(ValueChangeMode.LAZY);

        var filterButtonSearch = new Button("Поиск");
        filterButtonSearch.addClickShortcut(Key.ENTER);
        filterButtonSearch.addClickListener(event -> updateList());

        var filterButtonClear = new Button("Сбросить");
        filterButtonClear.addClickShortcut(Key.ESCAPE);
        filterButtonClear.addClickListener(event -> clearFilter());

        var addContactButton = new Button("Добавить продукт");
        addContactButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addContactButton.addClickListener(click -> addProduct());

        var toolbar = new HorizontalLayout(filterArticle, filterName, filterCount, filterButtonSearch, filterButtonClear, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void save(ProductForm.SaveEvent event) {
        var product = event.getProduct();
        if (Objects.isNull(product.getPriceDiscount()) || product.getPriceDiscount().compareTo(BigDecimal.ZERO) == 0) {
            product.setPriceDiscount(product.getPrice());
        }
        productService.add(product);
        updateList();
        closeEditor();
    }

    private void delete(ProductDto product) {
        productService.delete(product.getId());
        updateList();
        closeEditor();
    }

    public void edit(ProductDto product) {
        if (product == null) {
            closeEditor();
        } else {
            form.setProduct(product);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    void addProduct() {
        grid.asSingleSelect().clear();
        edit(new ProductDto());
    }

    private void closeEditor() {
        form.setProduct(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void clearFilter() {
        filterArticle.setValue(StringUtils.EMPTY);
        filterName.setValue(StringUtils.EMPTY);
        filterCount.setValue(null);
        updateList();
    }

    private void updateList() {
        var filter = ProductFilter.builder()
                .article(filterArticle.getValue())
                .name(filterName.getValue())
                .count(Objects.isNull(filterCount.getValue()) ? null : filterCount.getValue().toString())
                .build();
        grid.setItems(productService.search(filter));
    }

    private class ContextMenu extends GridContextMenu<ProductDto> {
        public ContextMenu(Grid<ProductDto> target) {
            super(target);

            addItem("Редактировать", e -> e.getItem().ifPresent(ProductView.this::edit));

            if (securityService.getAuthenticatedUserRole() == UserRoleEnum.ADMIN) {
                add(new Hr());
                addItem("Удалить", e -> e.getItem().ifPresent(ProductView.this::delete));
            }
        }
    }
}
