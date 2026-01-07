package ru.ifellow.jira.stepdefinitions;

import io.cucumber.java.ru.*;
import ru.ifellow.jira.pages.*;
import ru.ifellow.jira.hooks.Hooks;
import static org.assertj.core.api.Assertions.assertThat;
import static com.codeborne.selenide.Selenide.*;

public class СозданиеШаги {

    private IssuesPage issuesPage;
    private SearchPage searchPage;
    private TaskDetailsPage taskDetailsPage;
    private CreateTaskPage createTaskPage;
    private String urlBeforeClick;
    private String createdTaskKey;
    private String existingTaskKey;
    private static final String TASK_TO_SEARCH = "TestSeleniumATHomework";
    private static final String EXPECTED_STATUS = "СДЕЛАТЬ";
    private static final String EXPECTED_VERSION = "Version 2.0";
    private static final String TEST_SUMMARY = Hooks.getTestTaskSummary();
    private static final String TASK_DESCRIPTION = Hooks.getTaskDescription();
    private static final String TASK_ENVIRONMENT = Hooks.getTaskEnvironment();

    @Дано("я авторизован и нахожусь в проекте TEST")
    public void яАвторизованИНахожусьВПроектеTEST() {
        LoginPage loginPage = new LoginPage();
        loginPage.open();
        loginPage.enterUsername(Hooks.getUsername());
        loginPage.enterPassword(Hooks.getPassword());
        loginPage.clickLoginButton();

        open("https://edujira.ifellow.ru/browse/TEST");
        System.out.println("✅ Авторизован и в проекте TEST");
    }

    @Дано("я проверяю доступность меню задач")
    public void яПроверяюДоступностьМенюЗадач() {
        issuesPage = new IssuesPage();
        assertThat(issuesPage.isIssuesMenuDisplayed())
                .as("Меню 'Задачи' должно быть доступно")
                .isTrue();
        System.out.println("✅ Меню задач доступно");
    }

    @Дано("я открываю список задач в проекте TEST")
    public void яОткрываюСписокЗадачВПроектеTEST() {
        issuesPage.openIssues();
        System.out.println("✅ Список задач в проекте TEST открыт");
    }

    @Тогда("я проверяю что в проекте есть задачи")
    public void яПроверяюЧтоВПроектеЕстьЗадачи() {
        int taskCount = issuesPage.getTaskCountFromCounter();
        assertThat(taskCount)
                .as("Количество задач должно быть положительным")
                .isGreaterThan(0);
        System.out.println("✅ Задач в проекте: " + taskCount);
    }

    @Когда("я перехожу на страницу всех задач")
    public void яПерехожуНаСтраницуВсехЗадач() {
        searchPage = new SearchPage();
        urlBeforeClick = com.codeborne.selenide.WebDriverRunner.url();
        searchPage.clickViewAllIssues();

        String urlAfterClick = com.codeborne.selenide.WebDriverRunner.url();
        assertThat(urlAfterClick)
                .as("URL должен измениться после клика")
                .isNotEqualTo(urlBeforeClick);
        System.out.println("✅ Перешли на страницу всех задач");
    }

    @Когда("я выполняю поиск задачи {string}")
    public void яВыполняюПоискЗадачи(String задача) {
        searchPage.enterSearchText(задача);

        assertThat(searchPage.getSearchInputValue())
                .as("Поле поиска должно содержать введенный текст")
                .isEqualTo(задача);
        System.out.println("✅ Выполняем поиск задачи: " + задача);

        searchPage.clickSearchButton();

        assertThat(searchPage.isSearchResultDisplayed())
                .as("Поиск должен найти хотя бы одну задачу")
                .isTrue();
    }

    @Когда("я открываю детали найденной задачи")
    public void яОткрываюДеталиНайденнойЗадачи() {
        searchPage.clickSearchResult();
        taskDetailsPage = new TaskDetailsPage();

        assertThat(taskDetailsPage.isTaskDetailsPageLoaded())
                .as("Страница деталей задачи должна загрузиться")
                .isTrue();
        System.out.println("✅ Открыли детали найденной задачи");
    }

    @Тогда("я проверяю формат ключа задачи")
    public void яПроверяюФорматКлючаЗадачи() {
        existingTaskKey = taskDetailsPage.getTaskKey();
        assertThat(existingTaskKey)
                .as("Ключ задачи должен начинаться с 'TEST-'")
                .startsWith("TEST-");
        System.out.println("✅ Формат ключа верный: " + existingTaskKey);
    }

    @Тогда("я проверяю текущий статус задачи")
    public void яПроверяюТекущийСтатусЗадачи() {
        String actualStatus = taskDetailsPage.getStatus();
        assertThat(actualStatus)
                .as("Статус задачи должен быть 'СДЕЛАТЬ'")
                .isEqualTo(EXPECTED_STATUS);
        System.out.println("✅ Текущий статус задачи: " + actualStatus);
    }

    @Тогда("я проверяю установленную версию задачи")
    public void яПроверяюУстановленнуюВерсиюЗадачи() {
        assertThat(taskDetailsPage.isVersion20Displayed())
                .as("Версия 'Version 2.0' должна отображаться")
                .isTrue();

        String actualVersion = taskDetailsPage.getVersion20Text();
        assertThat(actualVersion)
                .as("Текст версии должен быть 'Version 2.0'")
                .isEqualTo(EXPECTED_VERSION);
        System.out.println("✅ Установленная версия задачи: " + actualVersion);
    }

    @Когда("я возвращаюсь на главный дашборд")
    public void яВозвращаюсьНаГлавныйДашборд() {
        open("https://edujira.ifellow.ru/secure/Dashboard.jspa");
        System.out.println("✅ Вернулись на главный дашборд");
    }

    @Когда("я начинаю создание новой задачи")
    public void яНачинаюСозданиеНовойЗадачи() {
        createTaskPage = new CreateTaskPage();
        createTaskPage.clickCreateButton();
        createTaskPage.waitForFormToLoad();
        System.out.println("✅ Начали создание новой задачи");
    }

    @Когда("я заполняю форму создания задачи")
    public void яЗаполняюФормуСозданияЗадачи() {
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

        System.out.println("✅ Заполнили форму создания задачи");
    }

    @Тогда("я подтверждаю создание задачи")
    public void яПодтверждаюСозданиеЗадачи() {
        createdTaskKey = createTaskPage.clickCreateAndGetKey();

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

        System.out.println("✅ Подтвердили создание задачи: " + createdTaskKey);
    }

    @Когда("я произвожу переход по статусам задачи")
    public void яПроизвожуПереходПоСтатусамЗадачи() {
        String initialStatus = createTaskPage.getCurrentStatus();
        assertThat(initialStatus)
                .as("Начальный статус должен быть 'СДЕЛАТЬ'")
                .isEqualTo("СДЕЛАТЬ");

        createTaskPage.transitionThroughStatuses();
        System.out.println("✅ Произвели переход по статусам задачи");
    }

    @Тогда("я проверяю финальный статус задачи")
    public void яПроверяюФинальныйСтатусЗадачи() {
        String finalStatus = createTaskPage.getCurrentStatus();
        assertThat(finalStatus.toUpperCase())
                .as("Финальный статус должен содержать 'ГОТОВО'")
                .contains("ГОТОВО");

        System.out.println("✅ Финальный статус задачи: " + finalStatus);
    }
}