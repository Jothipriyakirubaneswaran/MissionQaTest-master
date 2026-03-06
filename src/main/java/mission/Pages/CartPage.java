package mission.Pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import mission.utils.ReportUtil;

public class CartPage {

    WebDriver driver;

    public CartPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(className = "cart_item")
    List<WebElement> cartItems;

    @FindBy(className = "cart_quantity")
    List<WebElement> quantities;

    @FindBy(id="checkout")
    WebElement checkoutBtn;

    public int getCartSize(){
        return cartItems.size();
    }

    public boolean  verifyEachQtyIsOne(){
        // Return false if any quantity text is not exactly "1", otherwise true.
        if (quantities == null || quantities.isEmpty()) {
            return false;
        }
        for(WebElement qty : quantities){
            if(qty == null) return false;
            if(!qty.getText().trim().equals("1")){
                return false;
            }
        }
        return true;
        
    }

    public void removeItem(String itemName){
        WebElement removeBtn = driver.findElement(
                By.xpath("//div[text()='"+itemName+"']/ancestor::div[@class='cart_item']//button"));
        removeBtn.click();
        ReportUtil.logStep("Removed 'Sauce Labs Fleece Jacket' from cart");

    }

    public void clickCheckout(){
        checkoutBtn.click();
        ReportUtil.logStep("Clicked Checkout button");
    }
}