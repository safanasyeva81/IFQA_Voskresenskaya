package ru.ifellow.jira.pages;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.interactable;

public class CreateTaskPage {

    private final SelenideElement createButton = $x("//a[@id='create_link']");
    private final SelenideElement summaryField = $x("//input[@id='summary']");
    private final SelenideElement fixVersionsSelect = $x("//select[@id='fixVersions']");
    private final SelenideElement priorityField = $x("//input[@id='priority-field']");
    private final SelenideElement priorityOptionMedium = $x("//li[@id='medium-115']");
    private final SelenideElement labelsField = $x("//textarea[@id='labels-textarea']");
    private final SelenideElement labelsOptionTest = $x("//li[@id='test-49']");
    private final SelenideElement fourthFixVersionOption = $x("//select[@id='fixVersions']/optgroup/option[2]");
    private final SelenideElement affectedVersionsSelect = $x("//select[@id='versions']");
    private final SelenideElement firstAffectedVersionOption = $x("//select[@id='versions']/optgroup/option[1]");
    private final SelenideElement relatedTasksSelect = $x("//select[@id='issuelinks-linktype']");
    private final SelenideElement linkedTasksField = $x("//textarea[@id='issuelinks-issues-textarea']");
    private final SelenideElement linkedTaskOption = $x("//li[contains(@id, 'test-209858---a1')]");
    private final SelenideElement assignToMeButton = $x("//button[@id='assign-to-me-trigger']");
    private final SelenideElement epicField = $x("//input[@id='customfield_10100-field']");
    private final SelenideElement epicOption = $x("//div[@id='customfield_10100-suggestions']//li[contains(text(), 'Epic-(TEST-205869)')]");
    private final SelenideElement sprintField = $x("//input[@id='customfield_10104-field']");
    private final SelenideElement sprintOptionSprint2 = $x("//li[@id='доска-спринт-2-1529']");
    private final SelenideElement severitySelect = $x("//select[@id='customfield_10400']");

    public void clickCreateButton() {
        createButton.shouldBe(visible, Duration.ofSeconds(10)).click();
    }

    public void waitForFormToLoad() {
        summaryField.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void setSummary(String summaryText) {
        summaryField.shouldBe(visible).setValue(summaryText);
    }

    public void selectFixVersion() {
        fixVersionsSelect.scrollIntoView(true)
                .shouldBe(visible, Duration.ofSeconds(5))
                .click();
        sleep(500);
        fourthFixVersionOption.click();
    }

    public void selectPriorityByIndex() {

        priorityField.scrollIntoView(true);
        sleep(500);
        priorityField.click();
        $x("//div[@id='priority-suggestions']").shouldBe(visible, Duration.ofSeconds(3));
        sleep(500);
        $$x("//ul[@class='aui-last']/li").get(2).click();
        System.out.println("Выбран приоритет: Medium");
    }

    public void selectLabelTest() {

        labelsField.scrollIntoView(true);
        sleep(500);

        // Открытие и ввод
        labelsField.click();
        sleep(500);
        labelsField.setValue("test");
        sleep(2000);
        $x("//div[@id='labels-suggestions']").shouldBe(visible, Duration.ofSeconds(5));
        sleep(500);
        $x("//ul[@id='предложения']//a[normalize-space()='test']").click();
        System.out.println("Выбрана метка: test");
    }

    public void selectAffectedVersion() {

        affectedVersionsSelect.scrollIntoView(true);
        sleep(500);
        affectedVersionsSelect.shouldBe(visible, Duration.ofSeconds(5));

        affectedVersionsSelect.click();
        sleep(500);

        firstAffectedVersionOption.click();

        System.out.println("Выбрана версия: Version 1.0");
    }

    public void selectRelatedTaskType() {

        relatedTasksSelect.scrollIntoView(true);
        sleep(500);

        relatedTasksSelect.shouldBe(visible, Duration.ofSeconds(5));

        relatedTasksSelect.selectOption("clones");

        System.out.println("Выбран тип связи: clones");
    }

    public void selectLinkedTask() {

        linkedTasksField.scrollIntoView(true);
        sleep(500);

        linkedTasksField.click();
        sleep(500);

        linkedTasksField.setValue("TEST-209858");
        sleep(1500); // Ждем поиска

        $x("//div[@id='issuelinks-issues-suggestions']").shouldBe(visible, Duration.ofSeconds(3));
        sleep(500);

        linkedTaskOption.click();

        System.out.println("Выбрана связанная задача: TEST-209858 - A1");
    }

    public void clickAssignToMe() {

        assignToMeButton.scrollIntoView(true);
        sleep(500);

        assignToMeButton.shouldBe(visible, Duration.ofSeconds(5)).click();
        sleep(1000);

        SelenideElement assigneeField = $x("//input[@id='assignee-field']");
        assigneeField.shouldHave(value("AT1"), Duration.ofSeconds(3));

        System.out.println("Кнопка 'Назначить меня' нажата. Исполнитель: AT1");
    }

    public void selectEpic() {

        epicField.scrollIntoView(true);
        sleep(500);

        epicField.click();
        sleep(500);

        epicField.setValue("epic-");
        sleep(2000);

        $x("//div[@id='customfield_10100-suggestions']").shouldBe(visible, Duration.ofSeconds(5));
        sleep(500);

        $$x("//div[@id='customfield_10100-suggestions']//li").first().click();

        System.out.println("Выбран первый доступный эпик");
    }

    public void selectSprint() {

        sprintField.scrollIntoView(true);
        sleep(500);
        sprintField.click();
        sleep(500);

        sprintField.setValue("Доска Спринт 2");
        sleep(2000);

        $x("//div[@id='customfield_10104-suggestions']").shouldBe(visible, Duration.ofSeconds(5));
        sleep(500);

        $x("//li[contains(@class, 'aui-list-item-li-доска-спринт-2')]").click();

        System.out.println("Выбран спринт: Доска Спринт 2");
    }

    public void selectSeverity() {

        severitySelect.scrollIntoView(true);
        sleep(500);

        severitySelect.shouldBe(visible, Duration.ofSeconds(5));
        severitySelect.selectOption("S1 Незначительный/Minor");

        System.out.println("Выбрана серьезность: S1 Незначительный/Minor");
    }

    public void verifyFormStillOpen() {
        summaryField.shouldBe(visible);
    }

    public String clickCreateAndGetKey() {

        SelenideElement createButton = $x("//input[@id='create-issue-submit']");
        createButton.scrollIntoView(true);
        createButton.shouldBe(visible, Duration.ofSeconds(5)).click();
        sleep(3000);

        SelenideElement taskKeyElement = $x("//a[@id='key-val']");
        taskKeyElement.shouldBe(visible, Duration.ofSeconds(10));

        String taskKey = taskKeyElement.getText().trim();
        System.out.println("✓ Задача создана: " + taskKey);

        return taskKey;
    }

    public String getCurrentStatus() {
        SelenideElement statusElement = $x("//span[@id='status-val']");
        statusElement.shouldBe(visible, Duration.ofSeconds(5));
        return statusElement.getText().trim();
    }

    public void clickStatusButton(String statusText) {
        System.out.println("Пытаюсь нажать статус: " + statusText);

        SelenideElement statusButton = $x("//a[contains(@class, 'issueaction-workflow-transition')]//span[text()='" + statusText + "']");
        statusButton.scrollIntoView("{behavior: 'instant', block: 'center'}");
        sleep(1000);

        statusButton.shouldBe(visible, Duration.ofSeconds(5))
                .shouldBe(interactable, Duration.ofSeconds(5));

        executeJavaScript("arguments[0].click();", statusButton);
        sleep(3000);

        System.out.println("✓ Статус нажат: " + statusText);
    }

    public void transitionThroughStatuses() {
        System.out.println("=== ПЕРЕВОД ЗАДАЧИ ===");

        executeJavaScript("document.getElementById('action_id_21').click();");
        sleep(3000);

        executeJavaScript(
                "var blankets = document.querySelectorAll('.aui-blanket');" +
                        "for (var i = 0; i < blankets.length; i++) {" +
                        "  blankets[i].style.display = 'none';" +
                        "}"
        );
        sleep(500);

        executeJavaScript("document.getElementById('opsbar-transitions_more').click();");
        sleep(1500);

        executeJavaScript(
                "// Ищем в выпадающем меню" +
                        "var menu = document.getElementById('opsbar-transitions_more_drop');" +
                        "if (menu) {" +
                        "  var links = menu.getElementsByTagName('a');" +
                        "  for (var i = 0; i < links.length; i++) {" +
                        "    if (links[i].textContent.indexOf('Выполнено') !== -1) {" +
                        "      links[i].click();" +
                        "      break;" +
                        "    }" +
                        "  }" +
                        "}"
        );
        sleep(3000);

        System.out.println("Финальный статус: " + getCurrentStatus());
    }
}
