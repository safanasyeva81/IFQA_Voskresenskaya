package ru.ifellow.jira.pages;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;

public class SearchPage {

    private final SelenideElement viewAllIssuesButton = $x("//div[@id='full-issue-navigator']");
    private final SelenideElement searchInput = $x("//input[@id='searcher-query']");
    private final SelenideElement searchButton = $x("//button[@original-title='Поиск задач']");
    private final SelenideElement searchResultLink = $x("//a[@class='issue-link']");

    public void clickViewAllIssues() {
        viewAllIssuesButton.shouldBe(visible, Duration.ofSeconds(10)).click();
        searchInput.shouldBe(visible, Duration.ofSeconds(10));
    }

    public void enterSearchText(String searchText) {
        searchInput.shouldBe(visible, Duration.ofSeconds(5))
                .clear();
        searchInput.setValue(searchText);
        searchInput.shouldHave(value(searchText), Duration.ofSeconds(3));
    }

    public String getSearchInputValue() {
        return searchInput.shouldBe(visible, Duration.ofSeconds(5))
                .getValue();
    }

    public void clickSearchButton() {
        searchButton.shouldBe(visible, Duration.ofSeconds(5)).click();
        searchResultLink.shouldBe(visible, Duration.ofSeconds(10));
    }

    public boolean isSearchResultDisplayed() {
        return searchResultLink.shouldBe(visible, Duration.ofSeconds(5))
                .isDisplayed();
    }

    public void clickSearchResult() {
        searchResultLink.shouldBe(visible, Duration.ofSeconds(5)).click();
        $x("//a[@id='key-val']").shouldBe(visible, Duration.ofSeconds(10));
    }

    public String getTaskTitleFromSearchResult() {
        return searchResultLink.shouldBe(visible, Duration.ofSeconds(5))
                .getText().trim();
    }
}