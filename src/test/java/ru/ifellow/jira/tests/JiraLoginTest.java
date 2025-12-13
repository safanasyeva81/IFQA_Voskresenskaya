package ru.ifellow.jira.tests;

import org.junit.jupiter.api.Test;
import ru.ifellow.jira.pages.DashboardPage;
import ru.ifellow.jira.pages.LoginPage;
import static org.assertj.core.api.Assertions.assertThat;

public class JiraLoginTest {
        private static final String VALID_USERNAME = "AT1";
        private static final String VALID_PASSWORD = "Qwerty123";


        @Test
        void successfulLoginTest() {

            LoginPage loginPage = new LoginPage();
            DashboardPage dashboardPage=new DashboardPage();
            loginPage.open();

            com.codeborne.selenide.WebDriverRunner.getWebDriver().manage().window().maximize();

            loginPage.login(VALID_USERNAME, VALID_PASSWORD);

            assertThat(dashboardPage.isUserLoggedIn())
                    .as("После успешного логина пользователь должен быть авторизован")
                    .isTrue();

            System.out.println("ТЕСТ ПРОЙДЕН: Успешная авторизация");
        }
}
