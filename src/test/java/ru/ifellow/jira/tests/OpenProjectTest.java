package ru.ifellow.jira.tests;

import org.junit.jupiter.api.*;
import ru.ifellow.jira.hooks.Hooks;
import ru.ifellow.jira.pages.*;

public class OpenProjectTest {

    @BeforeAll
    static void setup() {
        Hooks.setupBeforeTests();
    }

    @Test
    void testOpenProject() {
        LoginPage loginPage = new LoginPage();
        loginPage.open();
        loginPage.loginWithCredentials();

        ProjectPage projectPage = new ProjectPage();
        projectPage.clickProjectsMenu();
        projectPage.clickTestProject();
        System.out.println("Проект TEST открыт");

    }
    @AfterEach
    void cleanup() {
        Hooks.cleanup();
    }
}