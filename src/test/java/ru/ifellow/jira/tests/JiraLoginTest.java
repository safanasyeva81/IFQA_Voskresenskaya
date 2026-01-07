package ru.ifellow.jira.tests;

import org.junit.jupiter.api.*;
import ru.ifellow.jira.hooks.Hooks;
import ru.ifellow.jira.pages.DashboardPage;
import ru.ifellow.jira.pages.LoginPage;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JiraLoginTest {

    @BeforeAll
    static void setupAllTests() {
        Hooks.setupBeforeTests();
    }

    @BeforeEach
    void openSite() {
        new LoginPage().open();
    }

    @AfterEach
    void cleanup() {
        Hooks.cleanup();
    }

    @Test
    void testSuccessfulLogin() {
        LoginPage loginPage = new LoginPage();
        DashboardPage dashboardPage = loginPage.loginWithCredentials();
        assertTrue(dashboardPage.isUserLoggedIn(),
                "Пользователь должен быть авторизован");
        System.out.println("Авторизация успешна");
    }
}