package ru.ifellow.jira.pages;

import com.codeborne.selenide.SelenideElement;
import java.time.Duration;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.Assertions.assertThat;

public class CreateTaskPage {

    private final SelenideElement createButton = $x("//a[@id='create_link']");
    private final SelenideElement summaryField = $x("//input[@id='summary']");
    private final SelenideElement fixVersionsSelect = $x("//select[@id='fixVersions']");
    private final SelenideElement priorityField = $x("//input[@id='priority-field']");
    private final SelenideElement labelsField = $x("//textarea[@id='labels-textarea']");
    private final SelenideElement affectedVersionsSelect = $x("//select[@id='versions']");
    private final SelenideElement firstAffectedVersionOption = $x("//select[@id='versions']/optgroup/option[1]");
    private final SelenideElement relatedTasksSelect = $x("//select[@id='issuelinks-linktype']");
    private final SelenideElement linkedTasksField = $x("//textarea[@id='issuelinks-issues-textarea']");
    private final SelenideElement assignToMeButton = $x("//button[@id='assign-to-me-trigger']");
    private final SelenideElement epicField = $x("//input[@id='customfield_10100-field']");
    private final SelenideElement sprintField = $x("//input[@id='customfield_10104-field']");
    private final SelenideElement severitySelect = $x("//select[@id='customfield_10400']");
    private final SelenideElement fourthFixVersionOption = $x("//select[@id='fixVersions']/optgroup/option[2]");
    private final SelenideElement createSubmitButton = $x("//input[@id='create-issue-submit']");
    private final SelenideElement descriptionTextButton = $x("//div[@id='description-wiki-edit']//button[text()='Текст']");
    private final SelenideElement descriptionTextarea = $x("//div[@id='description-wiki-edit']//textarea");
    private final SelenideElement environmentTextButton = $x("//div[@id='environment-wiki-edit']//button[text()='Текст']");
    private final SelenideElement environmentTextarea = $x("//div[@id='environment-wiki-edit']//textarea");
    private final SelenideElement prioritySuggestions = $x("//div[@id='priority-suggestions']");
    private final SelenideElement labelsSuggestions = $x("//div[@id='labels-suggestions']");
    private final SelenideElement testLabelSuggestion = $x("//ul[@id='предложения']//a[normalize-space()='test']");
    private final SelenideElement linkedTasksSuggestions = $x("//div[@id='issuelinks-issues-suggestions']");
    private final SelenideElement testLinkedTask = $x("//li[contains(@id, 'test-209858---a1')]");
    private final SelenideElement assigneeField = $x("//input[@id='assignee-field']");
    private final SelenideElement epicSuggestions = $x("//div[@id='customfield_10100-suggestions']");
    private final SelenideElement sprintSuggestions = $x("//div[@id='customfield_10104-suggestions']");
    private final SelenideElement sprintOption = $x("//li[contains(@class, 'aui-list-item-li-доска-спринт-2')]");

    public void clickCreateButton() {
        createButton.shouldBe(visible, Duration.ofSeconds(10)).click();
    }

    public void waitForFormToLoad() {
        summaryField.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void setSummary(String summaryText) {
        summaryField.shouldBe(visible).setValue(summaryText);
    }

    public void fillDescription(String text) {
        descriptionTextButton
                .shouldBe(visible, Duration.ofSeconds(10))
                .shouldBe(interactable, Duration.ofSeconds(5));

        assertThat(descriptionTextButton.isEnabled())
                .as("Кнопка 'Текст' должна быть доступна для клика")
                .isTrue();
        descriptionTextButton.click();
        descriptionTextButton.shouldHave(attribute("aria-pressed", "true"), Duration.ofSeconds(3));

        descriptionTextarea
                .shouldBe(visible, Duration.ofSeconds(5))
                .shouldBe(interactable, Duration.ofSeconds(3));
        assertThat(descriptionTextarea.isEnabled())
                .as("Textarea должен быть доступен для ввода")
                .isTrue();
        descriptionTextarea.setValue(text);

        String enteredText = descriptionTextarea.getValue();
        assertThat(enteredText)
                .as("Текст должен быть введен в поле")
                .isNotEmpty();
    }

    public void selectFixVersion() {
        fixVersionsSelect.scrollIntoView(true)
                .shouldBe(visible, Duration.ofSeconds(5))
                .click();
        fourthFixVersionOption.shouldBe(visible, Duration.ofSeconds(3)).click();
    }

    public void selectPriorityByIndex() {
        priorityField.scrollIntoView(true)
                .shouldBe(interactable, Duration.ofSeconds(5))
                .click();

        prioritySuggestions.shouldBe(visible, Duration.ofSeconds(3));

        $$x("//ul[@class='aui-last']/li").get(2)
                .shouldBe(visible, Duration.ofSeconds(2))
                .click();
    }

    public void selectLabelTest() {
        labelsField.scrollIntoView(true)
                .shouldBe(interactable, Duration.ofSeconds(5))
                .click();
        labelsField.setValue("test");

        labelsSuggestions.shouldBe(visible, Duration.ofSeconds(5));
        testLabelSuggestion.shouldBe(visible, Duration.ofSeconds(3)).click();
    }

    public void fillEnvironment(String text) {
        environmentTextButton
                .shouldBe(visible, Duration.ofSeconds(10))
                .shouldBe(interactable, Duration.ofSeconds(5));

        assertThat(environmentTextButton.isEnabled())
                .as("Кнопка 'Текст' должна быть доступна для клика")
                .isTrue();
        environmentTextButton.click();
        environmentTextButton.shouldHave(attribute("aria-pressed", "true"), Duration.ofSeconds(3));

        environmentTextarea
                .shouldBe(visible, Duration.ofSeconds(5))
                .shouldBe(interactable, Duration.ofSeconds(3));

        assertThat(environmentTextarea.isEnabled())
                .as("Textarea должен быть доступен для ввода")
                .isTrue();
        environmentTextarea.setValue(text);

        String enteredText = environmentTextarea.getValue();
        assertThat(enteredText)
                .as("Текст должен быть введен в поле")
                .isNotEmpty();
    }

    public void selectAffectedVersion() {
        affectedVersionsSelect.scrollIntoView(true)
                .shouldBe(visible, Duration.ofSeconds(5))
                .click();

        firstAffectedVersionOption.shouldBe(visible, Duration.ofSeconds(3)).click();
    }

    public void selectRelatedTaskType() {
        relatedTasksSelect.scrollIntoView(true)
                .shouldBe(visible, Duration.ofSeconds(5))
                .selectOption("clones");
    }

    public void selectLinkedTask() {
        linkedTasksField.scrollIntoView(true)
                .shouldBe(interactable, Duration.ofSeconds(5))
                .click();

        linkedTasksField.setValue("TEST-209858");

        linkedTasksSuggestions.shouldBe(visible, Duration.ofSeconds(5));
        testLinkedTask.shouldBe(visible, Duration.ofSeconds(3)).click();
    }

    public void clickAssignToMe() {
        assignToMeButton.scrollIntoView(true)
                .shouldBe(visible, Duration.ofSeconds(5))
                .click();

        assigneeField.shouldHave(value("AT1"), Duration.ofSeconds(3));
    }

    public void selectEpic() {
        epicField.scrollIntoView(true);
        epicField.shouldBe(interactable, Duration.ofSeconds(5)).clear();
        epicField.click();
        epicField.setValue("TEST-Epic");

        epicSuggestions.shouldBe(visible, Duration.ofSeconds(8));
        SelenideElement firstEpic = $x("//div[@id='customfield_10100-suggestions']" +
                "//ul[@id='предложения']" +
                "//li[contains(@class, 'aui-list-item-li-test-epic')][1]" +
                "//a[contains(@class, 'aui-list-item-link')]");

        if (!firstEpic.exists()) {
            firstEpic = $x("//div[@id='customfield_10100-suggestions']" +
                    "//*[contains(text(), 'TEST-Epic')]");
        }

        if (firstEpic.exists()) {
            String epicText = firstEpic.getText().trim();
            firstEpic.shouldBe(visible, Duration.ofSeconds(3)).click();
        } else {
            if (epicSuggestions.exists()) {
                System.out.println("Поле найдено, текст: " + epicSuggestions.getText().substring(0, 100));
            }
            epicField.clear();
            summaryField.click();
        }
    }

    public void selectSprint() {
        sprintField.scrollIntoView(true)
                .shouldBe(interactable, Duration.ofSeconds(5))
                .click();
        sprintField.setValue("Доска Спринт 2");

        sprintSuggestions.shouldBe(visible, Duration.ofSeconds(5));
        sprintOption.shouldBe(visible, Duration.ofSeconds(3)).click();
    }

    public void selectSeverity() {
        severitySelect.scrollIntoView(true)
                .shouldBe(visible, Duration.ofSeconds(5))
                .selectOption("S1 Незначительный/Minor");
    }

    public void verifyFormStillOpen() {
        summaryField.shouldBe(visible);
    }

    public String clickCreateAndGetKey() {
        checkForFormErrors();
        createSubmitButton.scrollIntoView(true)
                .shouldBe(visible, Duration.ofSeconds(5))
                .click();
        SelenideElement successNotification = waitForSuccessNotification();
        SelenideElement taskLink = findTaskLinkInNotification(successNotification);

        String taskKey = extractAndClickTaskKey(taskLink);

        waitForTaskPageToLoad();
        return taskKey;
    }

    private void checkForFormErrors() {
        SelenideElement errorDiv = $x("//div[contains(@class, 'aui-message-error')]");
        if (errorDiv.exists()) {
            String errorText = errorDiv.getText();
            System.out.println("ОШИБКИ В ФОРМЕ: " + errorText);
            throw new RuntimeException("Исправьте ошибки в форме: " + errorText);
        }
    }

    private SelenideElement waitForSuccessNotification() {
        return $x("//div[contains(@class, 'aui-message-success')]")
                .shouldBe(visible, Duration.ofSeconds(15));
    }

    private SelenideElement findTaskLinkInNotification(SelenideElement notification) {
        return notification.$x(".//a[@class='issue-created-key issue-link']")
                .shouldBe(visible, Duration.ofSeconds(5));
    }

    private String extractAndClickTaskKey(SelenideElement taskLink) {
        String linkText = taskLink.getText().trim();
        String taskKey = linkText.split(" ")[0];
        System.out.println("Ключ созданной задачи: " + taskKey);

        taskLink.click();
        return taskKey;
    }

    private void waitForTaskPageToLoad() {
        $x("//h1[@id='summary-val']").shouldBe(visible, Duration.ofSeconds(10));
    }

    public String getCurrentStatus() {
        SelenideElement statusElement = $x("//span[@id='status-val']");
        statusElement.shouldBe(visible, Duration.ofSeconds(5));
        return statusElement.getText().trim();
    }

    public void clickStatusButton(String statusText) {
        SelenideElement statusButton = $x("//a[contains(@class, 'issueaction-workflow-transition')]//span[text()='" + statusText + "']");

        if (!statusButton.exists()) {
            if (statusText.equals("В работе")) {
                statusButton = $x("//a[@id='action_id_21']");
            }
        }

        statusButton.scrollIntoView("{behavior: 'instant', block: 'center'}")
                .shouldBe(visible, Duration.ofSeconds(5))
                .shouldBe(interactable, Duration.ofSeconds(5))
                .click();

        $x("//span[@id='status-val']")
                .shouldNotHave(exactText(statusText), Duration.ofSeconds(3))
                .shouldHave(text(statusText), Duration.ofSeconds(10));

        System.out.println("Статус изменен: " + statusText);
    }

    public void transitionThroughStatuses() {
        $x("//a[@id='action_id_21']").click();
        $x("//span[@id='status-val']").shouldHave(text("В РАБОТЕ"), Duration.ofSeconds(10));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        $x("//a[@id='opsbar-transitions_more']/span").click();

        SelenideElement completeButton = null;
        String[] possibleLocators = {
                "//aui-item-link[@id='action_id_31']",
                "//a[@id='action_id_31']",
                "//div[@id='opsbar-transitions_more_drop']//a[contains(text(), 'Выполнить')]",
                "//a[contains(@class, 'issueaction-workflow-transition')]//span[text()='Выполнить']"
        };

        for (String locator : possibleLocators) {
            completeButton = $x(locator);
            if (completeButton.exists()) {
                break;
            }
        }

        if (completeButton != null && completeButton.exists()) {
            completeButton.click();
        } else {
            throw new RuntimeException("Не найдена кнопка 'Выполнить'");
        }

        $x("//span[@id='status-val']").shouldHave(text("ГОТОВО"), Duration.ofSeconds(10));
    }
}