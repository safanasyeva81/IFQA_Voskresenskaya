package ru.ifellow.jira.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/создание_задачи.feature",
        glue = {"ru.ifellow.jira.stepdefinitions"},
        plugin = {"pretty"},
        monochrome = true,
        tags = "@создание_задачи"
)
public class CreateTaskRunner {
}