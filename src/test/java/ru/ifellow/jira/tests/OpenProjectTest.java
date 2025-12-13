package ru.ifellow.jira.tests;

import org.junit.jupiter.api.Test;
import ru.ifellow.jira.pages.DashboardPage;
import ru.ifellow.jira.pages.LoginPage;
import ru.ifellow.jira.pages.ProjectPage;
import static org.assertj.core.api.Assertions.assertThat;
import static com.codeborne.selenide.Selenide.sleep;

public class OpenProjectTest {

    private static final String USERNAME = "AT1";
    private static final String PASSWORD = "Qwerty123";

    @Test
    void openTestProject() {

        LoginPage loginPage = new LoginPage();
        loginPage.open();

        com.codeborne.selenide.WebDriverRunner.getWebDriver().manage().window().maximize();

        assertThat(loginPage.isOnLoginPage())
                .as("Должны быть на странице логина")
                .isTrue();
        System.out.println("Шаг 1: Страница логина открыта - OK");

        loginPage.login(USERNAME, PASSWORD);
        loginPage.waitAfterLogin();

        DashboardPage dashboardPage = new DashboardPage();
        assertThat(dashboardPage.isUserLoggedIn())
                .as("Должны быть авторизованы после логина")
                .isTrue();
        System.out.println("Шаг 2: Авторизация прошла успешно - OK");

        ProjectPage projectPage = new ProjectPage();

        assertThat(projectPage.isProjectsMenuDisplayed())
                .as("Меню 'Проекты' должно быть отображено после логина")
                .isTrue();
        System.out.println("Шаг 3: Меню 'Проекты' доступно - OK");

        String urlBeforeClick = com.codeborne.selenide.WebDriverRunner.url();
        System.out.println("URL до клика: " + urlBeforeClick);

        projectPage.clickProjectsMenu();
        sleep(1000);

        projectPage.clickTestProject();
        sleep(3000); // Ждем загрузки

        String urlAfterClick = com.codeborne.selenide.WebDriverRunner.url();
        System.out.println("URL после клика: " + urlAfterClick);

        assertThat(urlAfterClick)
                .as("URL должен измениться после перехода в проект")
                .isNotEqualTo(urlBeforeClick);
        System.out.println("Шаг 4: URL изменился - OK");

        assertThat(urlAfterClick)
                .as("URL должен содержать идентификатор проекта TEST")
                .containsIgnoringCase("TEST");
        System.out.println("Шаг 5: URL содержит TEST - OK");

        System.out.println("ТЕСТ ПРОЙДЕН: Проект Test успешно открыт!");
    }
}