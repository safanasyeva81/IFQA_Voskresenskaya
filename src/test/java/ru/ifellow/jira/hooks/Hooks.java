package ru.ifellow.jira.hooks;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.InputStream;
import java.util.Properties;

public class Hooks {

    private static final Properties testProps = new Properties();
    private static final Properties credentials = new Properties();
    private static boolean propertiesLoaded = false;

    // Метод для ваших старых JUnit тестов (оставляем static)
    public static void setupBeforeTests() {
        if (!propertiesLoaded) {
            loadProperties();
        }
        WebDriverManager.chromedriver().setup();
        setupSelenide();
    }

    // Метод для ваших старых JUnit тестов (оставляем static)
    public static void cleanup() {
        try {
            if (WebDriverRunner.hasWebDriverStarted()) {
                Selenide.clearBrowserCookies();
                Selenide.clearBrowserLocalStorage();
                Selenide.closeWindow();
                Selenide.closeWebDriver();
                System.out.println("✅ Браузер успешно закрыт");
            } else {
                System.out.println("⚠️  Браузер уже закрыт или не был открыт");
            }
        } catch (Exception e) {
            System.out.println("⚠️  Ошибка при очистке: " + e.getMessage());
        }
    }

    // Метод для Cucumber тестов (НЕ static)
    @Before
    public void setUpCucumber(Scenario scenario) {
        System.out.println("=== Начало сценария Cucumber: " + scenario.getName() + " ===");

        if (!propertiesLoaded) {
            loadProperties();
            propertiesLoaded = true;
        }

        WebDriverManager.chromedriver().setup();
        setupSelenide();
    }

    // Метод для Cucumber тестов (НЕ static)
    @After
    public void tearDownCucumber(Scenario scenario) {
        System.out.println("=== Завершение сценария: " + scenario.getName() + " ===");

        if (scenario.isFailed()) {
            System.out.println("Сценарий упал! Делаем скриншот...");
            takeScreenshot(scenario);
        }

        // Вызываем статический метод cleanup
        cleanup();
    }

    private void takeScreenshot(Scenario scenario) {
        if (WebDriverRunner.hasWebDriverStarted()) {
            try {
                byte[] screenshot = ((TakesScreenshot) WebDriverRunner.getWebDriver())
                        .getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Скриншот при падении: " + scenario.getName());
            } catch (Exception e) {
                System.out.println("Не удалось сделать скриншот: " + e.getMessage());
            }
        }
    }

    private static void loadProperties() {
        // Ваш существующий код loadProperties()
        // ...
    }

    private static void setupSelenide() {
        // Ваш существующий код setupSelenide()
        // ...
    }

    // Геттеры остаются static
    public static String getLoginPageUrl() {
        return credentials.getProperty("login.page", "https://edujira.ifellow.ru/login.jsp");
    }

    public static String getUsername() {
        return credentials.getProperty("username", "AT1");
    }

    public static String getPassword() {
        return credentials.getProperty("password", "Qwerty123");
    }

    public static String getTestTaskSummary() {
        return credentials.getProperty("test.task.summary", "Test-Voskresenskaya");
    }

    public static String getProjectKey() {
        return testProps.getProperty("project.key", "TEST");
    }

    public static String getDefaultEpic() {
        return testProps.getProperty("default.epic", "Epic-(TEST-205869)");
    }

    public static String getDefaultSprint() {
        return testProps.getProperty("default.sprint", "????? ?????? 2");
    }

    public static String getTaskToSearch() {
        return testProps.getProperty("task.to.search", "TestSeleniumATHomework");
    }

    public static String getExpectedStatus() {
        return testProps.getProperty("expected.status", "СДЕЛАТЬ");
    }

    public static String getExpectedVersion() {
        return testProps.getProperty("expected.version", "Version 2.0");
    }

    public static String getTaskDescription() {
        return testProps.getProperty("task.description", "найден баг");
    }

    public static String getTaskEnvironment() {
        return testProps.getProperty("task.environment", "тестовое окружение");
    }
}