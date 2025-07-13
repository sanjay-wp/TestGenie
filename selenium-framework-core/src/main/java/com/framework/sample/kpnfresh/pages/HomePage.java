package com.framework.sample.kpnfresh.pages;

import com.framework.pages.BasePage;
import lombok.extern.log4j.Log4j2;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class HomePage extends BasePage {
    
    public void search(String searchTerm) {
        log.info("Searching for: {}", searchTerm);
        getElement("search.input").type(searchTerm);
        getElement("search.button").click();
    }
    
    public void navigateToCategory(String categoryName) {
        log.info("Navigating to category: {}", categoryName);
        getElement("category.menu").click();
        getElement(String.format("category.item.%s", categoryName.toLowerCase())).click();
    }
    
    public void openCart() {
        log.info("Opening shopping cart");
        getElement("cart.icon").click();
    }
    
    public int getCartItemsCount() {
        log.info("Getting cart items count");
        String countText = getElement("cart.count").getText();
        return countText.isEmpty() ? 0 : Integer.parseInt(countText);
    }

    public boolean areSearchResultsDisplayed() {
        log.info("Checking if search results are displayed");
        return getElement("search.results").isDisplayed();
    }

    public List<String> getProductNames() {
        log.info("Getting search result titles");
        return getElements("search.result.titles")
            .stream()
            .map(element -> element.getText())
            .collect(Collectors.toList());
    }
} 