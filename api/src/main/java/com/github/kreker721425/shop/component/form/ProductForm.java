package com.github.kreker721425.shop.component.form;

import com.github.kreker721425.shop.dto.ProductDto;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.shared.Registration;

public class ProductForm extends FormLayout {

    private ProductDto product;

    private final TextField article = new TextField("Артикул");
    private final TextField name = new TextField("Название");
    private final IntegerField count = new IntegerField("Количество");
    private final TextArea description = new TextArea("Описание");
    private final BigDecimalField price = new BigDecimalField("Цена");
    private final BigDecimalField priceDiscount = new BigDecimalField("Цена со скидкой");

    private final Binder<ProductDto> binder = new BeanValidationBinder<>(ProductDto.class);

    private final Button save = new Button("Сохранить");
    private final Button close = new Button("Отмена");

    public ProductForm() {
        addClassName("contact-form");
        binder.bindInstanceFields(this);

        name.setMaxLength(50);
        count.setMax(2147483647);
        var charLimit = 250;
        description.setMinHeight("100px");
        description.setMaxLength(charLimit);
        description.setValueChangeMode(ValueChangeMode.EAGER);
        description.addValueChangeListener(e ->
                e.getSource().setHelperText(e.getValue().length() + "/" + charLimit));

        add(article, name, count, description, price, priceDiscount, createButtonsLayout());
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickShortcut(Key.ENTER);
        save.addClickListener(event -> validateAndSave());

        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        close.addClickShortcut(Key.ESCAPE);
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, close);
    }

    public void setProduct(ProductDto product) {
        this.product = product;
        binder.readBean(product);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(product);
            fireEvent(new SaveEvent(this, product));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public abstract static class ContactFormEvent extends ComponentEvent<ProductForm> {
        private final ProductDto product;

        protected ContactFormEvent(ProductForm source, ProductDto product) {
            super(source, false);
            this.product = product;
        }

        public ProductDto getProduct() {
            return product;
        }
    }

    public static class SaveEvent extends ContactFormEvent {
        SaveEvent(ProductForm source, ProductDto contact) {
            super(source, contact);
        }
    }

    public static class CloseEvent extends ContactFormEvent {
        CloseEvent(ProductForm source) {
            super(source, null);
        }
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}