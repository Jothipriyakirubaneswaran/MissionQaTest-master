package mission.test;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import mission.Pages.CartPage;
import mission.Pages.CheckoutPage;
import mission.Pages.LoginPage;
import mission.Pages.ProductsPage;
import mission.base.BaseTest;
import mission.utils.ConfigReader;
import mission.utils.ExtentReportManager;
import mission.utils.ReportUtil;

@Listeners(mission.utils.TestListener.class)
public class UITest extends BaseTest {

    @Test
    public void verifyCheckoutFlow() {

        // STEP 1 - Launch Login Page
        LoginPage login = new LoginPage(driver);
        ReportUtil.logStep("Application launched successfully");

        // STEP 2 - Login using credentials from config file
        login.login(
                ConfigReader.get("username"),
                ConfigReader.get("password")
        );

       
        // STEP 3 - Initialize required pages
        ProductsPage productsPage = new ProductsPage(driver);
        CartPage cart = new CartPage(driver);
        CheckoutPage checkout = new CheckoutPage(driver);

        // STEP 4 - List of products to be added to cart
        List<String> products = List.of(
                "Sauce Labs Backpack",
                "Sauce Labs Fleece Jacket",
                "Sauce Labs Bolt T-Shirt",
                "Sauce Labs Onesie"
        );

        // STEP 5 - Add multiple products to cart
        productsPage.addMultipleProducts(products);
        

        // STEP 6 - Verify cart badge count
        int count = Integer.parseInt(productsPage.getCartCount());
        ReportUtil.logStep("Cart badge shows correct product count: " + count);


        Assert.assertEquals(count, 4, "Cart count validation failed");
       
        // STEP 7 - Navigate to cart page
        productsPage.clickCart();
        

        // STEP 8 - Verify each product quantity is 1
        Assert.assertTrue(cart.verifyEachQtyIsOne(),
                "Product quantity validation failed");
        ReportUtil.logStep("Verified each product quantity is 1");


        
        // STEP 9 - Remove one product from cart
        cart.removeItem("Sauce Labs Fleece Jacket");

        Assert.assertEquals(cart.getCartSize(), 3,
                "Cart size validation failed after removing product");

        
        // STEP 10 - Proceed to checkout
        cart.clickCheckout();
        

        // STEP 11 - Enter customer details
        checkout.enterDetails("FirstName", "LastName", "EC1A 9JU");

        

        // STEP 12 - Continue to order summary page
        checkout.clickContinue();

        

        // STEP 13 - Capture subtotal and tax values
        double subtotal = checkout.getDisplayedItemTotal();
        double tax = checkout.getDisplayedTax();
        ReportUtil.logStep("Captured subtotal: " + subtotal);
        ReportUtil.logStep("Captured tax: " + tax);

      

        // STEP 14 - Validate tax calculation
        double expectedTax = subtotal * 0.08;

        Assert.assertEquals(tax, expectedTax, 0.5,
                "Tax calculation validation failed");

        ReportUtil.logStep("Tax calculation validated successfully");

    }
}