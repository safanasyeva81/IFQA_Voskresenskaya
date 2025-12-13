package ru.ifellow.jira.pages;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class DashboardPage {
    private final SelenideElement userProfile = $x("//a[@id='header-details-user-fullname']");
    private final SelenideElement dashboardHeader = $x("//h1[contains(text(),'Dashboard')]");
    private final SelenideElement projectsMenu = $x("//a[@id='browse_link']");

    public boolean isUserLoggedIn() {

        return userProfile.exists() || dashboardHeader.exists() || projectsMenu.exists();
    }

    public boolean isUserProfileVisible() {
        return userProfile.isDisplayed();
    }

    public boolean isDashboardVisible() {
        return dashboardHeader.isDisplayed();
    }

    public String getUserProfileText() {
        return userProfile.getText();
    }
}
