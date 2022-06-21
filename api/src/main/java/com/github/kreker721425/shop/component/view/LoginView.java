package com.github.kreker721425.shop.component.view;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("login")
@PageTitle("Login | Shop")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private final LoginForm login = new LoginForm();

    public LoginView(){
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        var i18n = LoginI18n.createDefault();
        var i18nForm = i18n.getForm();
        i18nForm.setTitle("Войдите в систему");
        i18nForm.setUsername("Логин");
        i18nForm.setPassword("Пароль");
        i18nForm.setSubmit("Войти");
        i18n.setForm(i18nForm);
        var error = new LoginI18n.ErrorMessage();
        error.setTitle("Неверное имя пользователя или пароль");
        error.setMessage("Убедитесь, что вы ввели правильное имя пользователя и пароль, и повторите попытку.");
        i18n.setErrorMessage(error);

        login.setAction("login");
        login.setI18n(i18n);
        login.setForgotPasswordButtonVisible(false);

        add(new H1("Shop"), login);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            login.setError(true);
        }
    }
}
