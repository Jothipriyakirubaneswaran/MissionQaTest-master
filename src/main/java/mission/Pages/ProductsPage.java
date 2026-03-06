package mission.Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import mission.utils.ExtentReportManager;
import mission.utils.ReportUtil;

import java.time.Duration;
import java.util.List;

public class ProductsPage {

    WebDriver driver;
    WebDriverWait wait;

    public ProductsPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    @FindBy(className = "inventory_item")
    List<WebElement> items;

    @FindBy(className = "shopping_cart_link")
    WebElement cartIcon;

    By cartBadge = By.className("shopping_cart_badge");


    // Add single product
    public void addItemByName(String productName) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        String productId = productName
                .toLowerCase()
                .replace(" ", "-");

        By addButton = By.id("add-to-cart-" + productId);

        WebElement button = wait.until(
                ExpectedConditions.elementToBeClickable(addButton)
        );

        button.click();
    }

    // Add multiple products
    public void addMultipleProducts(List<String> productNames) {
        for (String product : productNames) {
            addItemByName(product);
            
        }
        ReportUtil.logStep("Selected 4 products and added them to cart");
    }


    // Get cart count safely
    public String getCartCount() {

        List<WebElement> badge = driver.findElements(cartBadge);

        if (badge.size() > 0) {
            return badge.get(0).getText();
        }

        return "0";
        
    }


    public void clickCart(){
        cartIcon.click();
        ReportUtil.logStep("Navigated to Cart page");
    }
}