package ru.ifellow.jira.hooks;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.InputStream;
import java.util.Properties;

public class Hooks {

    private static final Properties testProps = new Properties();
    private static final Properties credentials = new Properties();

    public static void setupBeforeTests() {
        loadProperties();
        WebDriverManager.chromedriver().setup();
        setupSelenide();
    }

    private static void loadProperties() {
        try {
            InputStream testInput = Hooks.class.getClassLoader()
                    .getResourceAsStream("test.properties");
            if (testInput != null) {
                testProps.load(testInput);
                testInput.close();
            } else {
                System.out.println("test.properties не найден, используем значения по умолчанию");
            }
        } catch (Exception e) {
            System.out.println("Ошибка загрузки test.properties: " + e.getMessage());
        }

        try {
            InputStream credInput = Hooks.class.getClassLoader()
                    .getResourceAsStream("credentials.properties");
            if (credInput != null) {
                credentials.load(credInput);
                credInput.close();
            } else {
                System.out.println("Файл credentials.properties не найден");
            }
        } catch (Exception e) {
            System.out.println("Ошибка загрузки credentials.properties: " + e.getMessage());
        }
    }

    private static void setupSelenide() {
        Configuration.browser = testProps.getProperty("browser", "chrome");
        Configuration.timeout = Long.parseLong(testProps.getProperty("timeout", "15000"));
        Configuration.pageLoadTimeout = Long.parseLong(testProps.getProperty("page.load.timeout", "30000"));
        Configuration.browserSize = testProps.getProperty("browser.size", "max");
        Configuration.headless = Boolean.parseBoolean(testProps.getProperty("headless", "false"));

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");

        Configuration.browserCapabilities = options;
        Configuration.holdBrowserOpen = false;
        Configuration.savePageSource = false;
        Configuration.screenshots = true;
    }

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

    public static void cleanup() {
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
        Selenide.closeWindow();
        Selenide.closeWebDriver();
    }
}