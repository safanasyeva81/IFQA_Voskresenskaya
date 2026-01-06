package ru.ifellow.jira.pages;

import com.codeborne.selenide.SelenideElement;
import java.time.Duration;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class DashboardPage {
    private final SelenideElement userProfile = $x("//a[@id='header-details-user-fullname']");
    private final SelenideElement dashboardHeader = $x("//h1[contains(text(),'Dashboard')]");
    private final SelenideElement projectsMenu = $x("//a[@id='browse_link']");

    public boolean isUserLoggedIn() {
        userProfile.shouldBe(visible, Duration.ofSeconds(10));
        return userProfile.exists() && userProfile.isDisplayed();
    }

    public boolean isUserProfileVisible() {
        return userProfile.shouldBe(visible, Duration.ofSeconds(5)).isDisplayed();
    }

    public boolean isDashboardVisible() {
        return dashboardHeader.shouldBe(visible, Duration.ofSeconds(5)).isDisplayed();
    }

    public String getUserProfileText() {
        return userProfile.shouldBe(visible, Duration.ofSeconds(5)).getText();
    }
}