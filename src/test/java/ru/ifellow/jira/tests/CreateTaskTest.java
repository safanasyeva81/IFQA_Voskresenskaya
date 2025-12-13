package ru.ifellow.jira.tests;

import org.junit.jupiter.api.Test;
import ru.ifellow.jira.pages.*;
import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.Assertions.assertThat;

public class CreateTaskTest {

    private static final String USERNAME = "AT1";
    private static final String PASSWORD = "Qwerty123";
    private static final String TEST_SUMMARY = "Test-Voskresenskaya";

    @Test
    void createNewTaskInTestProject() {

        // === ЛОГИН И ПЕРЕХОД В ПРОЕКТ ===
        LoginPage loginPage = new LoginPage();
        loginPage.open();

        com.codeborne.selenide.WebDriverRunner.getWebDriver().manage().window().maximize();

        loginPage.login(USERNAME, PASSWORD);
        loginPage.waitAfterLogin();

        open("https://edujira.ifellow.ru/browse/TEST");
        sleep(3000);

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

        System.out.println("Текущее количество задач: " + taskCountFromCounter);

        // === ШАГ 4: ПЕРЕХОД К ПОИСКУ ВСЕХ ЗАДАЧ ===
        SearchPage searchPage = new SearchPage();

        String urlBeforeClick = com.codeborne.selenide.WebDriverRunner.url();
        searchPage.clickViewAllIssues();

        String urlAfterClick = com.codeborne.selenide.WebDriverRunner.url();
        assertThat(urlAfterClick)
                .as("URL должен измениться после клика")
                .isNotEqualTo(urlBeforeClick);

        // === ШАГ 5: ПОИСК КОНКРЕТНОЙ ЗАДАЧИ ===
        String TASK_TO_SEARCH = "TestSeleniumATHomework";
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
        String EXPECTED_STATUS = "СДЕЛАТЬ";
        String actualStatus = taskDetailsPage.getStatus();
        assertThat(actualStatus)
                .as("Статус задачи должен быть 'СДЕЛАТЬ'")
                .isEqualTo(EXPECTED_STATUS);

        // === ШАГ 8: ПРОВЕРКА ВЕРСИИ ИСПРАВЛЕНИЯ ===
        String EXPECTED_VERSION = "Version 2.0";
        assertThat(taskDetailsPage.isVersion20Displayed())
                .as("Версия 'Version 2.0' должна отображаться")
                .isTrue();

        String actualVersion = taskDetailsPage.getVersion20Text();
        assertThat(actualVersion)
                .as("Текст версии должен быть 'Version 2.0'")
                .isEqualTo(EXPECTED_VERSION);

        System.out.println("=".repeat(40));
        System.out.println("ПРОВЕРКА ЗАДАЧИ УСПЕШНА!");
        System.out.println("Найдена задача: " + taskKey);
        System.out.println("Статус: " + actualStatus + " ✓");
        System.out.println("Версия: " + actualVersion + " ✓");
        System.out.println("=".repeat(40));

        // === СОЗДАНИЕ ЗАДАЧИ ===
        CreateTaskPage createTaskPage = new CreateTaskPage();

        createTaskPage.clickCreateButton();
        sleep(2000);

        createTaskPage.waitForFormToLoad();

        createTaskPage.setSummary(TEST_SUMMARY);


        createTaskPage.selectFixVersion();

        createTaskPage.selectPriorityByIndex();

        createTaskPage.selectLabelTest();

        createTaskPage.selectAffectedVersion();

        createTaskPage.selectRelatedTaskType();

        createTaskPage.selectLinkedTask();

        createTaskPage.verifyFormStillOpen();

        createTaskPage.selectEpic();

        createTaskPage.selectSprint();

        createTaskPage.selectSeverity();

        createTaskPage.clickAssignToMe();

        System.out.println("=".repeat(50));
        System.out.println("ВСЕ ПОЛЯ ФОРМЫ ЗАПОЛНЕНЫ!");
        System.out.println("=".repeat(50));

        String createdTaskKey = createTaskPage.clickCreateAndGetKey();

        System.out.println("=".repeat(50));
        System.out.println("ЗАДАЧА СОЗДАНА УСПЕШНО!");
        System.out.println("Ключ задачи: " + createdTaskKey);
        System.out.println("=".repeat(50));

        sleep(3000);

        createTaskPage.transitionThroughStatuses();

        String finalStatus = createTaskPage.getCurrentStatus();
        System.out.println("Финальный статус задачи: " + finalStatus);

        if (finalStatus.contains("Выполнено")) {
            System.out.println("Задача успешно завершена!");
        } else {
            System.out.println("Задача в другом статусе");
        }

        System.out.println("=".repeat(50));
        System.out.println("ТЕСТ УСПЕШНО ВЫПОЛНЕН!");
        System.out.println("Создана и проведена задача: " + createdTaskKey);
        System.out.println("=".repeat(50));
    }
}