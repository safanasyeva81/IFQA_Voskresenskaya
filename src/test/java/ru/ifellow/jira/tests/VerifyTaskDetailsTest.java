package ru.ifellow.jira.tests;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.*;
import ru.ifellow.jira.hooks.Hooks;
import ru.ifellow.jira.pages.*;
import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VerifyTaskDetailsTest {

    private static final String TASK_TO_SEARCH = "TestSeleniumATHomework";
    private static final String EXPECTED_STATUS = "СДЕЛАТЬ";
    private static final String EXPECTED_VERSION = "Version 2.0";

    @BeforeAll
    static void setupAllTests() {
        Hooks.setupBeforeTests();
    }

    @BeforeEach
    void prepare() {
        LoginPage loginPage = new LoginPage();
        loginPage.open();
        loginPage.loginWithCredentials();

        ProjectPage projectPage = new ProjectPage();
        projectPage.clickProjectsMenu();
        projectPage.clickTestProject();

        IssuesPage issuesPage = new IssuesPage();
        issuesPage.openIssues();
    }

    @AfterEach
    void cleanup() {
        Hooks.cleanup();
    }

    @Test
    void testSearchAndVerifyTaskDetails() {

        SearchPage searchPage = new SearchPage();
        String urlBeforeClick = Selenide.webdriver().driver().url();

        searchPage.clickViewAllIssues();
        String urlAfterClick = Selenide.webdriver().driver().url();
        assertThat(urlAfterClick)
                .as("URL должен измениться после клика")
                .isNotEqualTo(urlBeforeClick);

        searchPage.enterSearchText(TASK_TO_SEARCH);
        String searchValue = searchPage.getSearchInputValue();
        assertThat(searchValue)
                .as("Поле поиска должно содержать введенный текст")
                .isEqualTo(TASK_TO_SEARCH);

        searchPage.clickSearchButton();
        searchPage.clickSearchResult();

        TaskDetailsPage taskDetailsPage = new TaskDetailsPage();
        String actualStatus = taskDetailsPage.getStatus();
        assertThat(actualStatus)
                .as("Статус задачи должен быть '" + EXPECTED_STATUS + "'")
        .isEqualTo(EXPECTED_STATUS);

        String actualVersion = taskDetailsPage.getVersion20Text();
        assertThat(actualVersion)
                .as("Текст версии должен быть '" + EXPECTED_VERSION + "'")
                .isEqualTo(EXPECTED_VERSION);

        System.out.println("=".repeat(50));
        System.out.println("ТЕСТ ПРОЙДЕН УСПЕШНО!");
        System.out.println("Найдена задача: " + TASK_TO_SEARCH);
        System.out.println("Статус: " + actualStatus);
        System.out.println("Версия: " + actualVersion);
        System.out.println("=".repeat(50));
    }
}