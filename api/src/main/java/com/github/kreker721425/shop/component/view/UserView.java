package com.github.kreker721425.shop.component.view;

import com.github.kreker721425.shop.component.HeaderLayout;
import com.github.kreker721425.shop.component.form.UserForm;
import com.github.kreker721425.shop.dto.UserDto;
import com.github.kreker721425.shop.repository.filter.UserFilter;
import com.github.kreker721425.shop.service.UserService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.security.RolesAllowed;

@Component
@Scope("prototype")
@Route(value = "/users", layout = HeaderLayout.class)
@PageTitle("Клиенты | Shop")
@RolesAllowed(value = "ROLE_ADMIN")
public class UserView extends VerticalLayout {

    private final Grid<UserDto> grid = new Grid<>(UserDto.class);
    private final TextField filterName = new TextField();
    private final TextField filterLogin = new TextField();
    private final UserForm form = new UserForm();
    private final UserService userService;

    public UserView(UserService userService) {
        this.userService = userService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        form.setWidth("25em");
        form.addListener(UserForm.SaveEvent.class, this::save);
        form.addListener(UserForm.CloseEvent.class, e -> closeEditor());

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
        grid.addColumn(UserDto::getLogin).setHeader("Логин");
        grid.addColumn(UserDto::getName).setHeader("Имя");
        grid.addColumn(UserDto::getRole).setHeader("Роль");
        grid.addColumn(UserDto::getStatus).setHeader("Статус");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.getColumns().forEach(column -> column.setSortable(true));
    }

    private HorizontalLayout getToolbar() {
        filterName.setPlaceholder("Имя");
        filterName.setClearButtonVisible(true);
        filterName.setValueChangeMode(ValueChangeMode.LAZY);

        filterLogin.setPlaceholder("Логин");
        filterLogin.setClearButtonVisible(true);
        filterLogin.setValueChangeMode(ValueChangeMode.LAZY);


        var filterButtonSearch = new Button("Поиск");
        filterButtonSearch.addClickShortcut(Key.ENTER);
        filterButtonSearch.addClickListener(event -> updateList());

        var filterButtonClear = new Button("Сбросить");
        filterButtonClear.addClickShortcut(Key.ESCAPE);
        filterButtonClear.addClickListener(event -> clearFilter());

        var addContactButton = new Button("Добавить пользователя");
        addContactButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addContactButton.addClickListener(click -> addUser());

        var toolbar = new HorizontalLayout(filterName, filterLogin, filterButtonSearch, filterButtonClear, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void save(UserForm.SaveEvent event) {
        userService.add(event.getUser());
        updateList();
        closeEditor();
    }

    private void delete(UserDto user) {
        userService.delete(user.getId());
        updateList();
        closeEditor();
    }

    public void edit(UserDto user) {
        if (user == null) {
            closeEditor();
        } else {
            form.setUser(user);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    void addUser() {
        grid.asSingleSelect().clear();
        edit(new UserDto());
    }

    private void closeEditor() {
        form.setUser(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void clearFilter() {
        filterName.setValue(StringUtils.EMPTY);
        filterLogin.setValue(StringUtils.EMPTY);
        updateList();
    }

    private void updateList() {
        var filter = UserFilter.builder()
                .name(filterName.getValue())
                .login(filterLogin.getValue())
                .build();
        grid.setItems(userService.search(filter));
    }

    private class ContextMenu extends GridContextMenu<UserDto> {
        public ContextMenu(Grid<UserDto> target) {
            super(target);

            addItem("Редактировать", e -> e.getItem().ifPresent(UserView.this::edit));
            add(new Hr());
            addItem("Удалить", e -> e.getItem().ifPresent(UserView.this::delete));
        }
    }
}
