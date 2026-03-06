package mission.test;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import mission.Pages.CartPage;
import mission.Pages.CheckoutPage;
import mission.Pages.LoginPage;
import mission.Pages.ProductsPage;
import mission.base.BaseTest;
import mission.utils.ConfigReader;

public class UITest extends BaseTest {

    @Test
    public void verifyCheckoutFlow() {

        LoginPage login = new LoginPage(driver);
       
        CartPage cart = new CartPage(driver);
        CheckoutPage checkout = new CheckoutPage(driver);

        login.login(
                ConfigReader.get("username"),
                ConfigReader.get("password")
        );
        
        ProductsPage productsPage = new ProductsPage(driver);

        List<String> products = List.of(
                "Sauce Labs Backpack",
                "Sauce Labs Fleece Jacket",
                "Sauce Labs Bolt T-Shirt",
                "Sauce Labs Onesie"
        );

        productsPage.addMultipleProducts(products);

        int count = Integer.parseInt(productsPage.getCartCount());
        Assert.assertEquals(count, 4);

            

        productsPage.clickCart();
        Assert.assertTrue(cart.verifyEachQtyIsOne());

        cart.removeItem("Sauce Labs Fleece Jacket");
        Assert.assertEquals(cart.getCartSize(), 3);

        cart.clickCheckout();

        checkout.enterDetails("FirstName", "LastName", "EC1A 9JU");
        checkout.clickContinue();

        double subtotal = checkout.getDisplayedItemTotal();
        double tax = checkout.getDisplayedTax();

        double expectedTax = subtotal * 0.08;

        Assert.assertEquals(tax, expectedTax, 0.5);
}
}