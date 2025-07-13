package com.framework.sample.kpnfresh.pages;

import com.framework.interfaces.IElement;
import com.framework.pages.BasePage;
import org.openqa.selenium.Keys;
import lombok.extern.log4j.Log4j2;
import java.util.List;

@Log4j2
public class HomePage extends BasePage {
    
    public void search(String searchTerm) {
        IElement searchInput = getElement("header.search");
        searchInput.type(searchTerm);
        searchInput.getWebElement().sendKeys(Keys.ENTER);
    }
    
    public boolean areSearchResultsDisplayed() {
        return getElement("product.grid").isDisplayed();
    }
    
    public List<String> getProductNames() {
        return getElements("product.name").stream()
            .map(IElement::getText)
            .toList();
    }
    
    public void openCart() {
        getElement("header.cart").click();
    }
    
    public int getCartItemsCount() {
        return Integer.parseInt(getElement("cart.count").getText());
    }
} 