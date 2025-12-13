package ru.ifellow.jira.tests;

import org.junit.jupiter.api.Test;
import ru.ifellow.jira.pages.*;
import static org.assertj.core.api.Assertions.assertThat;
import static com.codeborne.selenide.Selenide.sleep;

public class CountTasksTest {

    private static final String USERNAME = "AT1";
    private static final String PASSWORD = "Qwerty123";

    @Test
    void countTasksInTestProject() {

        System.out.println("=== ШАГ 1: АВТОРИЗАЦИЯ ===");

        LoginPage loginPage = new LoginPage();
        loginPage.open();

        com.codeborne.selenide.WebDriverRunner.getWebDriver().manage().window().maximize();

        assertThat(loginPage.isOnLoginPage())
                .as("Должны находиться на странице логина после открытия")
                .isTrue();
        System.out.println("Страница логина успешно открыта");

        loginPage.login(USERNAME, PASSWORD);
        loginPage.waitAfterLogin();

        DashboardPage dashboardPage = new DashboardPage();
        assertThat(dashboardPage.isUserLoggedIn())
                .as("Пользователь должен быть авторизован после ввода корректных данных")
                .isTrue();
        System.out.println("Успешная авторизация пользователя " + USERNAME);

        System.out.println("\n=== ШАГ 2: ПЕРЕХОД В ПРОЕКТ TEST ===");

        ProjectPage projectPage = new ProjectPage();
        assertThat(projectPage.isProjectsMenuDisplayed())
                .as("Меню 'Проекты' должно быть доступно после авторизации")
                .isTrue();
        System.out.println("Меню 'Проекты' доступно");

        projectPage.clickProjectsMenu();
        projectPage.clickTestProject();
        sleep(2000);

        String currentUrl = com.codeborne.selenide.WebDriverRunner.url();
        assertThat(currentUrl)
                .as("URL должен содержать идентификатор проекта TEST после перехода")
                .containsIgnoringCase("TEST");
        System.out.println("Успешный переход в проект Test");

        System.out.println("\n=== ШАГ 3: ПЕРЕХОД В МЕНЮ ЗАДАЧ И ПОДСЧЕТ ===");

        IssuesPage issuesPage = new IssuesPage();

        assertThat(issuesPage.isIssuesMenuDisplayed())
                .as("Меню 'Задачи' должно быть доступно в проекте")
                .isTrue();
        System.out.println("Меню 'Задачи' доступно");

        String urlBeforeIssues = com.codeborne.selenide.WebDriverRunner.url();

        issuesPage.clickIssuesMenu();
        issuesPage.waitForIssuesToLoad();

        String urlAfterIssues = com.codeborne.selenide.WebDriverRunner.url();
        assertThat(urlAfterIssues)
                .as("URL должен измениться после перехода в меню задач")
                .isNotEqualTo(urlBeforeIssues);
        System.out.println("Успешный переход в меню 'Задачи'");

        String counterText = issuesPage.getShowingCounterText();
        System.out.println("Текст счетчика задач: " + counterText);
        assertThat(counterText)
                .as("Текст счетчика задач не должен быть пустым")
                .isNotEmpty();

        int taskCountFromCounter = issuesPage.getTaskCountFromCounter();
        System.out.println("Количество задач из счетчика: " + taskCountFromCounter);

        assertThat(taskCountFromCounter)
                .as("Количество задач должно быть положительным числом")
                .isGreaterThan(0);
        System.out.println("Количество задач: " + taskCountFromCounter);

        int visibleTasksCount = issuesPage.getActualVisibleTasksCount();
        System.out.println("Фактически видимых задач на странице: " + visibleTasksCount);
        assertThat(visibleTasksCount)
              .as("Количество видимых задач не должно превышать общее количество из счетчика")
              .isLessThanOrEqualTo(taskCountFromCounter);

        System.out.println("\n=========================================");
        System.out.println("ТЕСТ ПРОЙДЕН УСПЕШНО!");
        System.out.println("В проекте Test найдено задач: " + taskCountFromCounter);
        System.out.println("=========================================");
    }
}
