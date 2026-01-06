package ru.ifellow.jira.tests;

import org.junit.jupiter.api.*;
import ru.ifellow.jira.hooks.Hooks;
import ru.ifellow.jira.pages.*;

import static org.junit.jupiter.api.Assertions.*;

public class CountTasksTest {

    @BeforeAll
    static void setup() {
        Hooks.setupBeforeTests();
    }

    @Test
    void testCountTasks() {
        LoginPage loginPage = new LoginPage();
        loginPage.open();
        loginPage.loginWithCredentials();

        ProjectPage projectPage = new ProjectPage();
        projectPage.clickProjectsMenu();
        projectPage.clickTestProject();

        IssuesPage issuesPage = new IssuesPage();
        issuesPage.openIssues();

        int counterValue = issuesPage.getTaskCountFromCounter();
        assertTrue(counterValue > 0, "Должны быть задачи в проекте");

        System.out.println(counterValue + " задач в проекте");
    }

    @AfterEach
    void cleanup() {
        Hooks.cleanup();
    }
}