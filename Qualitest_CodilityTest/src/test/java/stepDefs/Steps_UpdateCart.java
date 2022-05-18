package stepDefs;

import PageObjects.CartPage;
import PageObjects.HomePage;
import PageObjects.WishlistPage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Steps_UpdateCart {

    WebDriver driver =null;
    HomePage homePage = null;
    WishlistPage wishListPage = null;
    CartPage cartPage = null;

    WebElement wbRowWithMinimumPrice = null;
    String strLowestPriceProductName = null;

    @Before
    public void beforeAll() {
        //String edgePath = "C:\\Users\\Jatin\\Downloads\\edgeDriver_Exe\\msedgedriver.exe";
        String chromePath = "C:\\Users\\srish\\Downloads\\chromedriver_win32\\chromedriver.exe";

        /*System.setProperty("webdriver.edge.driver", edgePath);*/
        System.setProperty("webdriver.chrome.driver", chromePath);
        // Instantiate a EdgeDriver class.
        //driver = new EdgeDriver();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        // Maximize the browser
        driver.manage().window().maximize();

        //initialize the Home page
        homePage = new HomePage(driver);

        System.out.println("Before");
    }

    @Given("I add four different products to my wishlist")
    public void i_add_four_different_products_to_my_wishlist(io.cucumber.datatable.DataTable dataTable) {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
        // Double, Byte, Short, Long, BigInteger or BigDecimal.
        //
        // For other transformations you can register a DataTableType.
        //throw new io.cucumber.java.PendingException();
        List<String> rows = dataTable.asList(String.class);
        for(String strProduct: rows)
        {
            System.out.println(strProduct);
            homePage.AddToWishlist(strProduct);
        }
    }

    @When("I view my wishlist table")
    public void ViewWishlistTable() {
        // Write code here that turns the phrase above into concrete actions
        //throw new io.cucumber.java.PendingException();
        wishListPage = new WishlistPage(driver);
        wishListPage.clickOnWishlistIcon();
        System.out.println("My Wish Table");
    }
    @Then("I find total four selected items in my wishlist")
    public void CheckFourItemsAddedToWishlist() {
        // Write code here that turns the phrase above into concrete actions
        //throw new io.cucumber.java.PendingException();
        Assert.assertEquals(wishListPage.getNumberOfProductsAdded(), 4);
        System.out.println("Check Four Items");
    }
    @When("I search for lowest price product")
    public void SearchForLowestPriceProduct() {
        // Write code here that turns the phrase above into concrete actions
        //throw new io.cucumber.java.PendingException();
        wbRowWithMinimumPrice = wishListPage.searchForMinimumPriceProduct();
        System.out.println("Search for lowest price product");
    }
    @When("I am able to add the lowest price item to my cart")
    public void AddLowestPriceItemToCart() {
        // Write code here that turns the phrase above into concrete actions
        //throw new io.cucumber.java.PendingException();
        strLowestPriceProductName = wishListPage.addToCartForMinimumPrice(wbRowWithMinimumPrice);
        System.out.println("Add lowest price item in cart " + strLowestPriceProductName);
    }
    @Then("I am able to verify the item in my cart")
    public void VerifyAddedItemInCart() {
        // Write code here that turns the phrase above into concrete actions
        //throw new io.cucumber.java.PendingException();
        System.out.println("Verify items added in cart");
        cartPage = new CartPage(driver);
        //navigate to cart
        cartPage.clickOnCartIcon();
        //check the minimum price added product
        String strCartProduct = cartPage.VerifyProductAddedToCart();
        Assert.assertEquals(strCartProduct,strLowestPriceProductName);
        System.out.println("Minimum price product: " + strLowestPriceProductName + " is added to the cart and verified successfully");
    }

    @After
    public void afterAll() {
        driver.quit();
        System.out.println("After");
    }
}
