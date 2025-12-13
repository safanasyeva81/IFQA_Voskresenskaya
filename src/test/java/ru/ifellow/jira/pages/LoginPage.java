package ru.ifellow.jira.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.sleep;

public class LoginPage {
    private final SelenideElement usernameField = $x("//input[@id='login-form-username']");
    private final SelenideElement passwordField = $x("//input[@id='login-form-password']");
    private final SelenideElement loginButton = $x("//input[@id='login-form-submit']");
    private final SelenideElement errorMessage = $x("//div[contains(@class, 'aui-message-error')]");

    public LoginPage open() {
        Selenide.open("https://edujira.ifellow.ru/login.jsp");
        loginButton.shouldBe(visible);
        return this;
    }

    public LoginPage enterUsername(String username) {
        usernameField.shouldBe(visible).setValue(username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        passwordField.shouldBe(visible).setValue(password);
        return this;
    }

    public void clickLoginButton() {
        loginButton.shouldBe(visible).click();
        sleep(1000);
    }

    public boolean isErrorMessageDisplayed() {
        sleep(500);
        return errorMessage.exists() && errorMessage.isDisplayed();
    }

    public String getErrorMessageText() {
        return errorMessage.shouldBe(visible).getText();
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    public boolean isOnLoginPage() {
        return loginButton.shouldBe(visible).isDisplayed();
    }

    public void waitAfterLogin() {
        sleep(2000);
    }
}
