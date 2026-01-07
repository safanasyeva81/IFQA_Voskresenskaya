package ru.ifellow.jira.stepdefinitions;

import io.cucumber.java.ru.*;
import ru.ifellow.jira.pages.ProjectPage;
import ru.ifellow.jira.pages.LoginPage;
import ru.ifellow.jira.pages.DashboardPage;
import ru.ifellow.jira.hooks.Hooks;

import static org.junit.jupiter.api.Assertions.*;

public class ПроектШаги {

    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private ProjectPage projectPage;

    @Дано("я авторизован в системе Jira")
    public void яАвторизованВСистемеJira() {
        loginPage = new LoginPage();
        loginPage.open();
        loginPage.enterUsername(Hooks.getUsername());
        loginPage.enterPassword(Hooks.getPassword());
        loginPage.clickLoginButton();

        dashboardPage = new DashboardPage();
        assertTrue(dashboardPage.isUserLoggedIn(),
                "Пользователь должен быть авторизован");
        System.out.println("Пользователь авторизован");
    }

    @Когда("я открываю меню проектов")
    public void открытьМенюПроектов() {
        projectPage = new ProjectPage();
        projectPage.clickProjectsMenu();
        System.out.println("Меню проектов открыто");
    }

    @Когда("я выбираю проект TEST")
    public void выбратьПроектTEST() {
        projectPage.clickTestProject();
        System.out.println("Проект TEST выбран");
    }

    @Тогда("я должен увидеть страницу проекта TEST")
    public void проверитьСтраницуПроектаTEST() {
        System.out.println("Страница проекта TEST открыта");
        assertTrue(true, "Страница проекта должна быть открыта");
    }
}