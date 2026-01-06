package ru.ifellow.jira.tests;

import org.junit.jupiter.api.*;
import ru.ifellow.jira.hooks.Hooks;
import ru.ifellow.jira.pages.*;
import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.Assertions.assertThat;

public class CreateTaskTest {

    private static final String TEST_SUMMARY;
    private static final String TASK_TO_SEARCH;
    private static final String EXPECTED_STATUS;
    private static final String EXPECTED_VERSION;
    private static final String TASK_DESCRIPTION;
    private static final String TASK_ENVIRONMENT;

    static {
        TEST_SUMMARY = Hooks.getTestTaskSummary();
        TASK_TO_SEARCH = Hooks.getTaskToSearch();
        EXPECTED_STATUS = Hooks.getExpectedStatus();
        EXPECTED_VERSION = Hooks.getExpectedVersion();
        TASK_DESCRIPTION = Hooks.getTaskDescription();
        TASK_ENVIRONMENT = Hooks.getTaskEnvironment();

    }

    @BeforeAll
    static void setupAllTests() {
        Hooks.setupBeforeTests();
    }

    @BeforeEach
    void prepare() {
        LoginPage loginPage = new LoginPage();
        loginPage.open();
        loginPage.loginWithCredentials();

        open("https://edujira.ifellow.ru/browse/TEST");
    }

    @AfterEach
    void cleanup() {
        Hooks.cleanup();
    }

    @Test
    void createNewTaskInTestProject() {
        IssuesPage issuesPage = new IssuesPage();
        assertThat(issuesPage.isIssuesMenuDisplayed())
                .as("Меню 'Задачи' должно быть доступно")
                .isTrue();

        issuesPage.openIssues();

        int taskCountFromCounter = issuesPage.getTaskCountFromCounter();
        assertThat(taskCountFromCounter)
                .as("Количество задач должно быть положительным")
                .isGreaterThan(0);

        System.out.println("Текущее количество задач: " + taskCountFromCounter);

        SearchPage searchPage = new SearchPage();

        String urlBeforeClick = getCurrentUrl();
        searchPage.clickViewAllIssues();

        String urlAfterClick = getCurrentUrl();
        assertThat(urlAfterClick)
                .as("URL должен измениться после клика")
                .isNotEqualTo(urlBeforeClick);

        searchPage.enterSearchText(TASK_TO_SEARCH);

        assertThat(searchPage.getSearchInputValue())
                .as("Поле поиска должно содержать введенный текст")
                .isEqualTo(TASK_TO_SEARCH);

        searchPage.clickSearchButton();

        assertThat(searchPage.isSearchResultDisplayed())
                .as("Поиск должен найти хотя бы одну задачу")
                .isTrue();

        searchPage.clickSearchResult();

        TaskDetailsPage taskDetailsPage = new TaskDetailsPage();
        assertThat(taskDetailsPage.isTaskDetailsPageLoaded())
                .as("Страница деталей задачи должна загрузиться")
                .isTrue();

        String existingTaskKey = taskDetailsPage.getTaskKey();
        assertThat(existingTaskKey)
                .as("Ключ задачи должен принадлежать проекту TEST")
                .startsWith("TEST-");

        String actualStatus = taskDetailsPage.getStatus();
        assertThat(actualStatus)
                .as("Статус задачи должен быть 'СДЕЛАТЬ'")
                .isEqualTo(EXPECTED_STATUS);

        assertThat(taskDetailsPage.isVersion20Displayed())
                .as("Версия 'Version 2.0' должна отображаться")
                .isTrue();

        String actualVersion = taskDetailsPage.getVersion20Text();
        assertThat(actualVersion)
                .as("Текст версии должен быть 'Version 2.0'")
                .isEqualTo(EXPECTED_VERSION);

        System.out.println("=".repeat(40));
        System.out.println("ПРОВЕРКА ЗАДАЧИ УСПЕШНА");
        System.out.println("Найдена задача: " + existingTaskKey);
        System.out.println("Статус: " + actualStatus);
        System.out.println("Версия: " + actualVersion);
        System.out.println("=".repeat(40));

        open("https://edujira.ifellow.ru/secure/Dashboard.jspa");

        CreateTaskPage createTaskPage = new CreateTaskPage();

        createTaskPage.clickCreateButton();
        createTaskPage.waitForFormToLoad();
        createTaskPage.setSummary(TEST_SUMMARY);
        createTaskPage.fillDescription(TASK_DESCRIPTION);
        createTaskPage.selectFixVersion();
        createTaskPage.selectPriorityByIndex();
        createTaskPage.selectLabelTest();
        createTaskPage.fillEnvironment(TASK_ENVIRONMENT);
        createTaskPage.selectAffectedVersion();
        createTaskPage.selectRelatedTaskType();
        createTaskPage.selectLinkedTask();
        createTaskPage.clickAssignToMe();
        createTaskPage.verifyFormStillOpen();
        createTaskPage.selectEpic();
        createTaskPage.selectSprint();
        createTaskPage.selectSeverity();

        System.out.println("=".repeat(50));
        System.out.println("ВСЕ ПОЛЯ ФОРМЫ ЗАДАЧИ ЗАПОЛНЕНЫ");
        System.out.println("=".repeat(50));

        String createdTaskKey = createTaskPage.clickCreateAndGetKey();

        assertThat(createdTaskKey)
                .as("Ключ созданной задачи не должен быть пустым")
                .isNotNull()
                .isNotEmpty()
                .startsWith("TEST-");

        TaskDetailsPage newTaskDetailsPage = new TaskDetailsPage();
        assertThat(newTaskDetailsPage.isTaskDetailsPageLoaded())
                .as("Страница созданной задачи должна загрузиться")
                .isTrue();

        String actualTaskKey = newTaskDetailsPage.getTaskKey();
        assertThat(actualTaskKey)
                .as("Ключ задачи должен совпадать с созданным")
                .isEqualTo(createdTaskKey);

        String initialStatus = createTaskPage.getCurrentStatus();
        assertThat(initialStatus)
                .as("Начальный статус должен быть 'СДЕЛАТЬ'")
                .isEqualTo("СДЕЛАТЬ");

        createTaskPage.transitionThroughStatuses();
        String finalStatus = createTaskPage.getCurrentStatus();
        assertThat(finalStatus.toUpperCase())
                .as("Финальный статус должен быть 'ГОТОВО'")
                .contains("ГОТОВО");
    }

    private String getCurrentUrl() {
        return com.codeborne.selenide.WebDriverRunner.url();
    }
}