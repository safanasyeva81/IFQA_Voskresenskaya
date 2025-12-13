package ru.ifellow.jira.pages;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.sleep;

public class ProjectPage {

    private final SelenideElement projectsMenu = $x("//a[@id='browse_link']");
    private final SelenideElement testProjectLink = $x("//a[@id='admin_main_proj_link_lnk']");

    public void clickProjectsMenu() {
        projectsMenu.shouldBe(visible).click();
        sleep(1000);
    }

    public void clickTestProject() {
        testProjectLink.shouldBe(visible).click();
        sleep(3000);
    }

    public boolean isProjectsMenuDisplayed() {
        return projectsMenu.isDisplayed();
    }
}