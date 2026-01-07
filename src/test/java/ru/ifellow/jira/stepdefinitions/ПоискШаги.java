package ru.ifellow.jira.stepdefinitions;

import io.cucumber.java.ru.*;
import com.codeborne.selenide.Selenide;
import ru.ifellow.jira.pages.*;
import ru.ifellow.jira.hooks.Hooks;
import static org.assertj.core.api.Assertions.assertThat;

public class ПоискШаги {

    private SearchPage searchPage;
    private TaskDetailsPage taskDetailsPage;
    private String urlBeforeClick;

    @Дано("я нахожусь на странице списка задач проекта TEST")
    public void наСтраницеСпискаЗадач() {
        LoginPage loginPage = new LoginPage();
        loginPage.open();
        loginPage.enterUsername(Hooks.getUsername());
        loginPage.enterPassword(Hooks.getPassword());
        loginPage.clickLoginButton();

        ProjectPage projectPage = new ProjectPage();
        projectPage.clickProjectsMenu();
        projectPage.clickTestProject();

        IssuesPage issuesPage = new IssuesPage();
        issuesPage.openIssues();

        System.out.println("✅ На странице списка задач");
    }

    @Когда("я открываю страницу всех задач")
    public void открытьСтраницуВсехЗадач() {
        searchPage = new SearchPage();
        urlBeforeClick = Selenide.webdriver().driver().url();
        searchPage.clickViewAllIssues();
        System.out.println("✅ Открыта страница всех задач");
    }

    // ДВЕ аннотации!
    @Когда("ввожу в поиск {string}")
    @Когда("я ввожу в поиск {string}")
    public void ввестиВПоиск(String текст) {
        searchPage.enterSearchText(текст);
        String value = searchPage.getSearchInputValue();
        assertThat(value).isEqualTo(текст);
        System.out.println("✅ Введено в поиск: " + текст);
    }

    @Когда("я нажимаю кнопку поиска")
    public void яНажимаюКнопкуПоиска() {
        searchPage.clickSearchButton();
        System.out.println("✅ Кнопка поиска нажата");
    }

    @Когда("я выбираю найденную задачу")
    public void выбратьНайденнуюЗадачу() {
        searchPage.clickSearchResult();
        taskDetailsPage = new TaskDetailsPage();
        System.out.println("✅ Задача выбрана");
    }

    @Тогда("статус задачи должен быть {string}")
    public void проверитьСтатус(String статус) {
        String actual = taskDetailsPage.getStatus();
        assertThat(actual).isEqualTo(статус);
        System.out.println("✅ Статус верный: " + actual);
    }

    @Тогда("версия задачи должна быть {string}")
    public void проверитьВерсию(String версия) {
        String actual = taskDetailsPage.getVersion20Text();
        assertThat(actual).isEqualTo(версия);
        System.out.println("✅ Версия верная: " + actual);
    }
}