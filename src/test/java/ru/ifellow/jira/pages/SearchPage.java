package ru.ifellow.jira.pages;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.sleep;

public class SearchPage {

    // Локаторы из ТЗ
    private final SelenideElement viewAllIssuesButton = $x("//div[@id='full-issue-navigator']");
    private final SelenideElement searchInput = $x("//input[@id='searcher-query']");
    private final SelenideElement searchButton = $x("//button[@original-title='Поиск задач']");
    private final SelenideElement searchResultLink = $x("//a[@class='issue-link']");

    public void clickViewAllIssues() {
        viewAllIssuesButton.shouldBe(visible).click();
        sleep(5000); // Увеличили ожидание
    }

    public void enterSearchText(String searchText) {
        searchInput.shouldBe(visible).clear();
        searchInput.setValue(searchText);
        sleep(1000);
    }

    public String getSearchInputValue() {
        return searchInput.shouldBe(visible).getValue();
    }

    public void clickSearchButton() {
        searchButton.shouldBe(visible).click();
        sleep(5000); // Увеличили ожидание
    }

    public boolean isSearchResultDisplayed() {
        sleep(2000);
        return searchResultLink.exists() && searchResultLink.isDisplayed();
    }

    public void clickSearchResult() {
        searchResultLink.shouldBe(visible).click();
        sleep(3000);
    }

    public String getTaskTitleFromSearchResult() {
        return searchResultLink.getText().trim();
    }
}