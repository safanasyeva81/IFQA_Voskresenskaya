package ru.ifellow.jira.pages;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class TaskDetailsPage {

    private final SelenideElement taskKeyElement = $x("//a[@id='key-val']");
    private final SelenideElement taskSummaryElement = $x("//h1[@id='summary-val']");
    private final SelenideElement statusValue = $x("//span[@id='status-val']");
    private final SelenideElement fixVersionsField = $x("//span[@id='fixVersions-field']");

    private final SelenideElement version20Link = $x("//span[@id='fixVersions-field']//a[text()='Version 2.0']");

    public String getTaskKey() {
        return taskKeyElement.shouldBe(visible).getText().trim();
    }

    public String getTaskSummary() {
        return taskSummaryElement.shouldBe(visible).getText().trim();
    }

    public String getStatus() {
        return statusValue.shouldBe(visible).getText().trim();
    }

    public String getFixVersionsText() {
        return fixVersionsField.shouldBe(visible).getText().trim();
    }

    public boolean isVersion20Displayed() {
        return version20Link.exists() && version20Link.isDisplayed();
    }

    public String getVersion20Text() {
        return version20Link.shouldBe(visible).getText().trim();
    }

    public boolean isTaskDetailsPageLoaded() {
        return taskKeyElement.exists() && statusValue.exists();
    }

    public String getPageTitle() {
        return com.codeborne.selenide.Selenide.title();
    }
}
