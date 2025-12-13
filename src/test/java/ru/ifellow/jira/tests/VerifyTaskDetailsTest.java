package ru.ifellow.jira.tests;

import org.junit.jupiter.api.Test;
import ru.ifellow.jira.pages.*;
import static org.assertj.core.api.Assertions.assertThat;
import static com.codeborne.selenide.Selenide.sleep;

public class VerifyTaskDetailsTest {

    private static final String USERNAME = "AT1";
    private static final String PASSWORD = "Qwerty123";
    private static final String TASK_TO_SEARCH = "TestSeleniumATHomework";
    private static final String EXPECTED_STATUS = "СДЕЛАТЬ";
    private static final String EXPECTED_VERSION = "Version 2.0";

    @Test
    void verifyTaskDetailsInTestProject() {

        // === ШАГ 1: АВТОРИЗАЦИЯ ===
        LoginPage loginPage = new LoginPage();
        loginPage.open();

        com.codeborne.selenide.WebDriverRunner.getWebDriver().manage().window().maximize();

        assertThat(loginPage.isOnLoginPage())
                .as("Должны находиться на странице логина")
                .isTrue();

        loginPage.login(USERNAME, PASSWORD);
        loginPage.waitAfterLogin();

        DashboardPage dashboardPage = new DashboardPage();
        assertThat(dashboardPage.isUserLoggedIn())
                .as("Пользователь должен быть авторизован")
                .isTrue();

        // === ШАГ 2: ПЕРЕХОД В ПРОЕКТ TEST ===
        ProjectPage projectPage = new ProjectPage();

        assertThat(projectPage.isProjectsMenuDisplayed())
                .as("Меню 'Проекты' должно быть доступно")
                .isTrue();

        projectPage.clickProjectsMenu();
        projectPage.clickTestProject();
        sleep(3000);

        String currentUrl = com.codeborne.selenide.WebDriverRunner.url();
        assertThat(currentUrl)
                .as("URL должен содержать TEST")
                .containsIgnoringCase("TEST");

        // === ШАГ 3: ПЕРЕХОД В МЕНЮ ЗАДАЧ И ПОДСЧЕТ ===
        IssuesPage issuesPage = new IssuesPage();

        assertThat(issuesPage.isIssuesMenuDisplayed())
                .as("Меню 'Задачи' должно быть доступно")
                .isTrue();

        issuesPage.clickIssuesMenu();
        issuesPage.waitForIssuesToLoad();

        int taskCountFromCounter = issuesPage.getTaskCountFromCounter();
        assertThat(taskCountFromCounter)
                .as("Количество задач должно быть положительным")
                .isGreaterThan(0);

        // === ШАГ 4: ПЕРЕХОД К ПОИСКУ ВСЕХ ЗАДАЧ ===
        SearchPage searchPage = new SearchPage();

        String urlBeforeClick = com.codeborne.selenide.WebDriverRunner.url();

        searchPage.clickViewAllIssues();

        String urlAfterClick = com.codeborne.selenide.WebDriverRunner.url();
        assertThat(urlAfterClick)
                .as("URL должен измениться после клика")
                .isNotEqualTo(urlBeforeClick);

        // === ШАГ 5: ПОИСК КОНКРЕТНОЙ ЗАДАЧИ ===
        searchPage.enterSearchText(TASK_TO_SEARCH);

        assertThat(searchPage.getSearchInputValue())
                .as("Поле поиска должно содержать введенный текст")
                .isEqualTo(TASK_TO_SEARCH);

        searchPage.clickSearchButton();

        assertThat(searchPage.isSearchResultDisplayed())
                .as("Поиск должен найти хотя бы одну задачу")
                .isTrue();

        // === ШАГ 6: ПЕРЕХОД К ДЕТАЛЯМ ЗАДАЧИ ===
        searchPage.clickSearchResult();

        TaskDetailsPage taskDetailsPage = new TaskDetailsPage();

        assertThat(taskDetailsPage.isTaskDetailsPageLoaded())
                .as("Страница деталей задачи должна загрузиться")
                .isTrue();

        String taskKey = taskDetailsPage.getTaskKey();
        assertThat(taskKey)
                .as("Ключ задачи должен принадлежать проекту TEST")
                .startsWith("TEST-");

        // === ШАГ 7: ПРОВЕРКА СТАТУСА ЗАДАЧИ ===
        String actualStatus = taskDetailsPage.getStatus();
        assertThat(actualStatus)
                .as("Статус задачи должен быть 'СДЕЛАТЬ'")
                .isEqualTo(EXPECTED_STATUS);

        // === ШАГ 8: ПРОВЕРКА ВЕРСИИ ИСПРАВЛЕНИЯ ===
        assertThat(taskDetailsPage.isVersion20Displayed())
                .as("Версия 'Version 2.0' должна отображаться")
                .isTrue();

        String actualVersion = taskDetailsPage.getVersion20Text();
        assertThat(actualVersion)
                .as("Текст версии должен быть 'Version 2.0'")
                .isEqualTo(EXPECTED_VERSION);

        System.out.println("=".repeat(50));
        System.out.println("ТЕСТ ПРОЙДЕН УСПЕШНО!");
        System.out.println("Найдена задача: " + taskKey);
        System.out.println("Статус: " + actualStatus);
        System.out.println("Версия: " + actualVersion);
        System.out.println("=".repeat(50));
    }
}