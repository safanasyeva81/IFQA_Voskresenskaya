package ru.ifellow.jira.stepdefinitions;

import io.cucumber.java.ru.*;
import ru.ifellow.jira.pages.DashboardPage;
import ru.ifellow.jira.pages.LoginPage;
import ru.ifellow.jira.hooks.Hooks;

import static org.junit.jupiter.api.Assertions.*;

public class АвторизацияШаги {

    private LoginPage loginPage;
    private DashboardPage dashboardPage;

    @Дано("я открываю страницу авторизации Jira")
    public void открытьСтраницуАвторизации() {
        loginPage = new LoginPage();
        loginPage.open();
    }

    @Когда("я ввожу валидный логин")
    public void ввестиВалидныйЛогин() {
        loginPage.enterUsername(Hooks.getUsername());
    }

    @Когда("я ввожу валидный пароль")
    public void ввестиВалидныйПароль() {
        loginPage.enterPassword(Hooks.getPassword());
    }

    @Когда("я нажимаю кнопку входа")
    public void нажатьКнопкуВхода() {
        loginPage.clickLoginButton();
    }

    @Тогда("я должен быть успешно авторизован в системе")
    public void проверитьУспешнуюАвторизацию() {
        dashboardPage = new DashboardPage();
        assertTrue(dashboardPage.isUserLoggedIn(),
                "Пользователь должен быть авторизован");
        System.out.println("✅ Авторизация успешна через Cucumber!");
    }
}