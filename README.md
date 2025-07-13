# Selenium Test Automation Framework

A scalable and maintainable test automation framework built with Java 17, Selenium WebDriver, TestNG, and Cucumber.

## Features

- Java 17 with modern language features
- Page Object Model design pattern
- YAML-based configuration management
- Multi-browser support
- Thread-safe WebDriver management
- Flexible locator management
- Data-driven testing support
- Parallel test execution
- Comprehensive reporting

## Project Structure

```
selenium-framework/
├── selenium-framework-core/        # Core Framework Module
│   ├── src/
│   │   ├── main/java/
│   │   │   └── com/framework/
│   │   │       ├── config/       # Configuration management
│   │   │       ├── driver/       # WebDriver management
│   │   │       ├── elements/     # Custom element wrappers
│   │   │       └── interfaces/   # Core interfaces
│   │   ├── main/resources/
│   │   │   └── framework-defaults/
│   │   │       ├── test-config.yml
│   │   │       └── common-locators.yml
│   │   └── test/                 # Sample implementation
│   │       ├── java/
│   │       │   └── com/framework/sample/
│   │       │       └── kpnfresh/
│   │       │           ├── pages/
│   │       │           ├── stepdefs/
│   │       │           └── tests/
│   │       └── resources/
│   │           ├── features/
│   │           └── locators/
```

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.8.x or higher
- Chrome/Firefox/Edge browser

### Installation

1. Clone the repository:
```bash
git clone <repository-url>
```

2. Build the framework:
```bash
mvn clean install
```

## Sample Implementation: KPN Fresh Website

The framework includes a sample implementation for testing the KPN Fresh website (https://www.kpnfresh.com/).

### Test Scenarios

1. Home Page Tests:
   - Search functionality
   - Category navigation
   - Newsletter subscription
   - Shopping cart operations

### Page Objects

Example of HomePage implementation:

```java
public class HomePage extends BasePage {
    private final IElement searchBox = getElement("header.search");
    private final IElement cartIcon = getElement("header.cart");
    
    public void search(String searchTerm) {
        searchBox.type(searchTerm);
        searchBox.getWebElement().sendKeys(Keys.ENTER);
    }
    
    public void openCart() {
        cartIcon.click();
        waitUntil(page -> getElement("cart.mini_cart").isDisplayed(), 
                 Duration.ofSeconds(5));
    }
}
```

### Locator Management

Locators are defined in YAML files:

```yaml
header:
  search:
    type: css
    value: "#search"
  cart:
    type: css
    value: ".minicart-wrapper"
```

### Test Execution

Run the sample tests:
```bash
mvn test -Dcucumber.filter.tags="@kpnfresh"
```

## Framework Components

### 1. WebDriver Management

- Thread-safe driver initialization
- Support for Chrome, Firefox, and Edge
- Automatic driver cleanup

```java
// Initialize driver
DriverManager.setDriver(Browser.CHROME);

// Get driver instance
WebDriver driver = DriverManager.getDriver();

// Clean up
DriverManager.removeDriver();
```

### 2. Page Objects

- Fluent interface design
- Automatic wait conditions
- Element lazy loading

```java
public class LoginPage extends BasePage {
    private final IElement emailField = getElement("auth.email");
    private final IElement passwordField = getElement("auth.password");
    
    public void login(String email, String password) {
        emailField.type(email);
        passwordField.type(password);
        getElement("auth.submit").click();
    }
}
```

### 3. Configuration Management

- Environment-specific settings
- Test data management
- Browser configurations

```yaml
browser:
  default: chrome
  timeout: 30
  headless: false
```

### 4. Element Interactions

- Built-in waits and retries
- Safe element operations
- Custom action support

```java
IElement element = getElement("product.add_to_cart")
    .waitUntilClickable(Duration.ofSeconds(10))
    .performAction(e -> e.click());
```

## Best Practices

1. **Page Objects**:
   - Keep page objects focused and maintainable
   - Use fluent interfaces for better readability
   - Implement lazy loading for elements

2. **Test Data**:
   - Use external YAML files for test data
   - Implement data encryption for sensitive information
   - Use environment-specific configurations

3. **Framework Usage**:
   - Follow the provided examples and templates
   - Use the built-in utilities and helpers
   - Maintain proper test organization

## Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct and the process for submitting pull requests.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.