package ru.ifellow.jira.stepdefinitions;

import io.cucumber.java.ru.*;
import ru.ifellow.jira.pages.*;
import ru.ifellow.jira.hooks.Hooks;

import static org.junit.jupiter.api.Assertions.*;

public class ЗадачиШаги {

    private LoginPage loginPage;
    private ProjectPage projectPage;
    private IssuesPage issuesPage;
    private int taskCount;

    @Дано("я открываю проект TEST")
    public void открытьПроектTEST() {
        loginPage = new LoginPage();
        loginPage.open();
        loginPage.enterUsername(Hooks.getUsername());
        loginPage.enterPassword(Hooks.getPassword());
        loginPage.clickLoginButton();

        DashboardPage dashboardPage = new DashboardPage();
        assertTrue(dashboardPage.isUserLoggedIn(),
                "Пользователь должен быть авторизован");

        projectPage = new ProjectPage();
        projectPage.clickProjectsMenu();
        projectPage.clickTestProject();

        System.out.println("Проект TEST открыт");
    }

    @Когда("я открываю список задач")
    public void открытьСписокЗадач() {
        issuesPage = new IssuesPage();
        issuesPage.openIssues();
        System.out.println("Список задач открыт");
    }

    @Тогда("я вижу счетчик задач")
    public void проверитьСчетчикЗадач() {
        String counterText = issuesPage.getShowingCounterText();
        assertNotNull(counterText, "Счетчик задач должен отображаться");
        assertFalse(counterText.isEmpty(), "Текст счетчика не должен быть пустым");

        System.out.println("Счетчик задач: " + counterText);
    }

    @Тогда("количество задач больше нуля")
    public void проверитьКоличествоЗадач() {
        taskCount = issuesPage.getTaskCountFromCounter();
        assertTrue(taskCount > 0, "Количество задач должно быть больше нуля");

        System.out.println("Задач в проекте: " + taskCount);
    }
}