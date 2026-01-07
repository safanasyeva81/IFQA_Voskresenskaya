package ru.ifellow.jira.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/открытие_проекта.feature",
        glue = {
                "ru.ifellow.jira.stepdefinitions",
                "ru.ifellow.jira.hooks"
        },
        plugin = {
                "pretty",
        },
        monochrome = true,
        tags = "@проект"
)
public class ProjectRunner {
}
