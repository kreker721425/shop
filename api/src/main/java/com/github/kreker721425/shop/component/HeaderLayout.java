package com.github.kreker721425.shop.component;

import com.github.kreker721425.shop.component.view.HistoryView;
import com.github.kreker721425.shop.component.view.MainPage;
import com.github.kreker721425.shop.component.view.OrderView;
import com.github.kreker721425.shop.component.view.UserView;
import com.github.kreker721425.shop.db.enums.UserRoleEnum;
import com.github.kreker721425.shop.security.SecurityService;
import com.github.kreker721425.shop.component.view.ClientView;
import com.github.kreker721425.shop.component.view.ProductView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;

public class HeaderLayout extends AppLayout {
    private final SecurityService securityService;

    public HeaderLayout(SecurityService securityService) {
        this.securityService = securityService;
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Shop");
        logo.addClassNames("text-l", "m-m");

        Button logout = new Button("Выйти", e -> securityService.logout());
        H4 name = new H4(securityService.getAuthenticatedUser().getName());
        name.addClassNames("text-l", "m-m");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, name, logout);

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidth("100%");
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);

    }

    private void createDrawer() {
        if (securityService.getAuthenticatedUserRole() == UserRoleEnum.ADMIN) {
            addToDrawer(new VerticalLayout(
                    new RouterLink("Пользователи", UserView.class),
                    new RouterLink("История", HistoryView.class),
                    new RouterLink("Заказы", OrderView.class),
                    new RouterLink("Клиенты", ClientView.class),
                    new RouterLink("Продукты", ProductView.class),
                    new RouterLink("Оформление заказа", MainPage.class)
            ));
            return;
        }
        addToDrawer(new VerticalLayout(
                new RouterLink("Заказы", OrderView.class),
                new RouterLink("Клиенты", ClientView.class),
                new RouterLink("Продукты", ProductView.class),
                new RouterLink("Оформление заказа", MainPage.class)
        ));
    }
}
