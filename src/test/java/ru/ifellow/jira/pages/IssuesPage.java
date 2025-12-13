package ru.ifellow.jira.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.sleep;
import static com.codeborne.selenide.Selenide.$$x;

public class IssuesPage {

    private final SelenideElement issuesMenu = $x("//a[@data-link-id='com.atlassian.jira.jira-projects-issue-navigator:sidebar-issue-navigator']");
    private final SelenideElement showingCounter = $x("//div[@class='showing']/span");
    private final ElementsCollection issuesList = $$x("//ol[@id='issue-content']//li[contains(@class, 'issue-item')]");

    public void clickIssuesMenu() {
        issuesMenu.shouldBe(visible).click();
        sleep(2000);
    }

    public boolean isIssuesMenuDisplayed() {
        return issuesMenu.isDisplayed();
    }

    public String getShowingCounterText() {
        return showingCounter.shouldBe(visible).getText();
    }

    public int getTaskCountFromCounter() {
        String counterText = getShowingCounterText();
        String[] parts = counterText.split(" ");
        for (int i = parts.length - 1; i >= 0; i--) {
            try {
                String cleanNumber = parts[i].replaceAll("[^0-9]", "");
                if (!cleanNumber.isEmpty()) {
                    return Integer.parseInt(cleanNumber);
                }
            } catch (NumberFormatException e) {
                continue;
            }
        }
        return -1;
    }

    public int getActualVisibleTasksCount() {
        return issuesList.size();
    }

    public void waitForIssuesToLoad() {
        sleep(3000);
    }
}