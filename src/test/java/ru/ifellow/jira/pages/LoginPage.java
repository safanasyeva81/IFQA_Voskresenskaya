package ru.ifellow.jira.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import ru.ifellow.jira.hooks.Hooks;
import java.time.Duration;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class LoginPage {
    private final SelenideElement usernameField = $x("//input[@id='login-form-username']");
    private final SelenideElement passwordField = $x("//input[@id='login-form-password']");
    private final SelenideElement loginButton = $x("//input[@id='login-form-submit']");
    private final SelenideElement errorMessage = $x("//div[contains(@class, 'aui-message-error')]");

    public LoginPage open() {

        Selenide.open(Hooks.getLoginPageUrl());
        loginButton.shouldBe(visible, Duration.ofSeconds(10));
        return this;
    }

    public DashboardPage loginWithCredentials() {
        enterUsername(Hooks.getUsername());
        enterPassword(Hooks.getPassword());
        clickLoginButton();
        return new DashboardPage();
    }

    public LoginPage enterUsername(String username) {
        usernameField.shouldBe(visible, Duration.ofSeconds(5)).setValue(username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        passwordField.shouldBe(visible, Duration.ofSeconds(5)).setValue(password);
        return this;
    }

    public void clickLoginButton() {
        loginButton.shouldBe(visible, Duration.ofSeconds(5)).click();
            $x("//a[@id='header-details-user-fullname']")
                .shouldBe(visible, Duration.ofSeconds(10));
    }

    public boolean isErrorMessageDisplayed() {
        return errorMessage.shouldBe(visible, Duration.ofSeconds(5)).isDisplayed();
    }

    public String getErrorMessageText() {
        return errorMessage.shouldBe(visible, Duration.ofSeconds(5)).getText();
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    public boolean isOnLoginPage() {
        return loginButton.shouldBe(visible, Duration.ofSeconds(5)).isDisplayed();
    }
}