package com.github.kreker721425.shop.component.form;

import com.github.kreker721425.shop.db.enums.UserRoleEnum;
import com.github.kreker721425.shop.db.enums.UserStatusEnum;
import com.github.kreker721425.shop.dto.UserDto;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class UserForm extends FormLayout {

    private UserDto user;

    private final Binder<UserDto> binder = new BeanValidationBinder<>(UserDto.class);

    private final TextField login = new TextField("Логин");
    private final TextField password = new TextField("Пароль");
    private final TextField name = new TextField("Имя");
    private final ComboBox<UserRoleEnum> role = new ComboBox<>("Роль");
    private final ComboBox<UserStatusEnum> status = new ComboBox<>("Статус");

    private final Button save = new Button("Сохранить");
    private final Button close = new Button("Отмена");

    public UserForm() {
        addClassName("contact-form");
        name.setMaxLength(100);

        role.setItems(UserRoleEnum.values());
        role.setItemLabelGenerator(UserRoleEnum::getLiteral);
        status.setItems(UserStatusEnum.values());
        status.setItemLabelGenerator(UserStatusEnum::getLiteral);
        binder.bindInstanceFields(this);
        add(login, password, name, role, status, createButtonsLayout());
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

    public void setUser(UserDto user) {
        this.user = user;
        binder.readBean(user);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(user);
            fireEvent(new SaveEvent(this, user));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public abstract static class ContactFormEvent extends ComponentEvent<UserForm> {
        private final UserDto user;

        protected ContactFormEvent(UserForm source, UserDto user) {
            super(source, false);
            this.user = user;
        }

        public UserDto getUser() {
            return user;
        }
    }

    public static class SaveEvent extends ContactFormEvent {
        SaveEvent(UserForm source, UserDto contact) {
            super(source, contact);
        }
    }

    public static class CloseEvent extends ContactFormEvent {
        CloseEvent(UserForm source) {
            super(source, null);
        }
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
