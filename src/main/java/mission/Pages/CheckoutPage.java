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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckoutPage {

    WebDriver driver;

    public CheckoutPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id="first-name")
    WebElement firstName;

    @FindBy(id="last-name")
    WebElement lastName;

    @FindBy(id="postal-code")
    WebElement postalCode;

    @FindBy(id="continue")
    WebElement continueBtn;

    @FindBy(className="inventory_item_price")
    List<WebElement> itemPrices;

    @FindBy(className="summary_subtotal_label")
    WebElement itemTotalLabel;

    @FindBy(className="summary_tax_label")
    WebElement taxLabel;

    private WebDriverWait getWait(){
        return new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    private void waitForVisibility(WebElement element){
        getWait().until(ExpectedConditions.visibilityOf(element));
    }

    public void enterDetails(String fName, String lName, String zip){
        firstName.sendKeys(fName);
        lastName.sendKeys(lName);
        postalCode.sendKeys(zip);
        ReportUtil.logStep("Entered checkout user details");
    }

    public void clickContinue(){
        continueBtn.click();
        ReportUtil.logStep("Navigated to order summary page");
    }

    public double calculateItemTotal(){
        double total = 0;
        // wait for any price element to be visible
        if(itemPrices != null && !itemPrices.isEmpty()){
            try{
                waitForVisibility(itemPrices.get(0));
            }catch(Exception ignored){ }
            for(WebElement price : itemPrices){
                total += extractFirstNumber(price.getText());
            }
        }
        return total;
    }

    // Local helper: extract first numeric value from a label text
    private double extractFirstNumber(String text){
        if(text == null) return 0.0;
        String normalized = text.replace('\u00A0', ' ').replace("\u00A0", " ").replace(",", "").trim();
        // Remove common currency symbols
        normalized = normalized.replaceAll("[$€£¥]", "");
        Pattern p = Pattern.compile("([0-9]+(?:\\.[0-9]+)?)");
        Matcher m = p.matcher(normalized);
        if(m.find()){
            try{
                return Double.parseDouble(m.group(1));
            }catch(NumberFormatException e){
                return 0.0;
            }
        }
        return 0.0;
    }

    public double getDisplayedItemTotal(){
        // wait for label then extract numeric part robustly
        try{ waitForVisibility(itemTotalLabel); }catch(Exception ignored){ }
        return extractFirstNumber(itemTotalLabel.getText());
    }

    public double getDisplayedTax(){
        try{ waitForVisibility(taxLabel); }catch(Exception ignored){ }
        return extractFirstNumber(taxLabel.getText());
    }

    // Debug helpers: return raw label text (useful when investigating parsing issues)
    public String getRawItemTotalText(){
        try{ waitForVisibility(itemTotalLabel); }catch(Exception ignored){ }
        return itemTotalLabel.getText();
    }

    public String getRawTaxText(){
        try{ waitForVisibility(taxLabel); }catch(Exception ignored){ }
        return taxLabel.getText();
    }

}