package com.github.kreker721425.shop.component.view;

import com.github.kreker721425.shop.db.enums.UserRoleEnum;
import com.github.kreker721425.shop.dto.ClientDto;
import com.github.kreker721425.shop.repository.filter.ClientFilter;
import com.github.kreker721425.shop.security.SecurityService;
import com.github.kreker721425.shop.service.ClientService;
import com.github.kreker721425.shop.component.HeaderLayout;
import com.github.kreker721425.shop.component.form.ClientForm;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.security.PermitAll;

@Component
@Scope("prototype")
@Route(value = "/clients", layout = HeaderLayout.class)
@PageTitle("Клиенты | Shop")
@PermitAll
public class ClientView extends VerticalLayout {
    private final Grid<ClientDto> grid = new Grid<>(ClientDto.class);
    private final TextField filterName = new TextField();
    private final TextField filterPhone = new TextField();
    private final DatePicker filterBirthday = new DatePicker();
    private final ClientForm form = new ClientForm();
    private final ClientService clientService;
    private final SecurityService securityService;

    public ClientView(ClientService clientService, SecurityService securityService) {
        this.clientService = clientService;
        this.securityService = securityService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        form.setWidth("25em");
        form.addListener(ClientForm.SaveEvent.class, this::save);
        form.addListener(ClientForm.CloseEvent.class, e -> closeEditor());

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
        grid.addColumn(ClientDto::getName).setHeader("Имя");
        grid.addColumn(ClientDto::getPhone).setHeader("Номер телефона");
        grid.addColumn(new LocalDateRenderer<>(ClientDto::getBirthday, "dd.MM.yyyy")).setHeader("Дата рождения");
        grid.addColumn(ClientDto::getBonusCount).setHeader("Кол-во бонусов");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.getColumns().forEach(column -> column.setSortable(true));
    }

    private HorizontalLayout getToolbar() {
        filterName.setPlaceholder("Имя");
        filterName.setClearButtonVisible(true);
        filterName.setValueChangeMode(ValueChangeMode.LAZY);

        filterPhone.setPlaceholder("Телефон");
        filterPhone.setClearButtonVisible(true);
        filterPhone.setValueChangeMode(ValueChangeMode.LAZY);

        filterBirthday.setPlaceholder("День рождения");
        filterBirthday.setClearButtonVisible(true);


        var filterButtonSearch = new Button("Поиск");
        filterButtonSearch.addClickShortcut(Key.ENTER);
        filterButtonSearch.addClickListener(event -> updateList());

        var filterButtonClear = new Button("Сбросить");
        filterButtonClear.addClickShortcut(Key.ESCAPE);
        filterButtonClear.addClickListener(event -> clearFilter());

        var addContactButton = new Button("Добавить клиента");
        addContactButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addContactButton.addClickListener(click -> addClient());

        var toolbar = new HorizontalLayout(filterName, filterPhone, filterBirthday, filterButtonSearch, filterButtonClear, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void save(ClientForm.SaveEvent event) {
        clientService.add(event.getClient());
        updateList();
        closeEditor();
    }

    private void delete(ClientDto client) {
        clientService.delete(client.getId());
        updateList();
        closeEditor();
    }

    public void edit(ClientDto client) {
        if (client == null) {
            closeEditor();
        } else {
            form.setClient(client);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    void addClient() {
        grid.asSingleSelect().clear();
        edit(new ClientDto());
    }

    private void closeEditor() {
        form.setClient(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void clearFilter() {
        filterName.setValue(StringUtils.EMPTY);
        filterPhone.setValue(StringUtils.EMPTY);
        filterBirthday.setValue(null);
        updateList();
    }

    private void updateList() {
        var filter = ClientFilter.builder()
                .name(filterName.getValue())
                .phone(filterPhone.getValue())
                .birthday(filterBirthday.getValue())
                .build();
        grid.setItems(clientService.search(filter));
    }

    private class ContextMenu extends GridContextMenu<ClientDto> {
        public ContextMenu(Grid<ClientDto> target) {
            super(target);

            addItem("Редактировать", e -> e.getItem().ifPresent(ClientView.this::edit));

            if (securityService.getAuthenticatedUserRole() == UserRoleEnum.ADMIN) {
                add(new Hr());
                addItem("Удалить", e -> e.getItem().ifPresent(ClientView.this::delete));
            }
        }
    }
}
