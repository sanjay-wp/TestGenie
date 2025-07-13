package com.framework.sample.kpnfresh.stepdefs;

import com.framework.driver.DriverManager;
import com.framework.sample.kpnfresh.pages.HomePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class HomePageSteps {
    
    private HomePage homePage;
    
    @Given("I am on the KPN Fresh home page")
    public void iAmOnTheHomePage() {
        homePage = new HomePage();
        DriverManager.getDriver().get("https://www.google.com");
        log.info("Navigated to Google homepage");
    }
    
    @When("I search for {string}")
    public void iSearchFor(String searchTerm) {
        homePage.search(searchTerm);
    }
    
    @Then("I should see search results")
    public void iShouldSeeSearchResults() {
        Assert.assertTrue(homePage.areSearchResultsDisplayed(), 
            "Search results are not displayed");
    }
    
    @Then("the search results should contain {string}")
    public void theSearchResultsShouldContain(String term) {
        homePage.getProductNames().forEach(name -> 
            Assert.assertTrue(name.toLowerCase().contains(term.toLowerCase()),
                "Product name does not contain search term: " + name));
    }
    
    @When("I click on the shopping cart icon")
    public void iClickOnShoppingCartIcon() {
        homePage.openCart();
    }
    
    @Then("the cart should be empty")
    public void theCartShouldBeEmpty() {
        Assert.assertEquals(homePage.getCartItemsCount(), 0, 
            "Cart is not empty");
    }
} 