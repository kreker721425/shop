package com.github.kreker721425.shop.component.form;

import com.github.kreker721425.shop.dto.ClientDto;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class ClientForm extends FormLayout {

    private ClientDto client;

    private final Binder<ClientDto> binder = new BeanValidationBinder<>(ClientDto.class);

    private final TextField name = new TextField("Имя");
    private final TextField phone = new TextField("Телефон");
    private final DatePicker birthday = new DatePicker("Дата рождения");

    private final Button save = new Button("Сохранить");
    private final Button close = new Button("Отмена");

    public ClientForm() {
        addClassName("contact-form");
        name.setMaxLength(100);
        binder.bindInstanceFields(this);
        add(name, phone, birthday, createButtonsLayout());
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

    public void setClient(ClientDto client) {
        this.client = client;
        binder.readBean(client);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(client);
            fireEvent(new SaveEvent(this, client));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public abstract static class ContactFormEvent extends ComponentEvent<ClientForm> {
        private final ClientDto client;

        protected ContactFormEvent(ClientForm source, ClientDto client) {
            super(source, false);
            this.client = client;
        }

        public ClientDto getClient() {
            return client;
        }
    }

    public static class SaveEvent extends ContactFormEvent {
        SaveEvent(ClientForm source, ClientDto contact) {
            super(source, contact);
        }
    }

    public static class CloseEvent extends ContactFormEvent {
        CloseEvent(ClientForm source) {
            super(source, null);
        }
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
