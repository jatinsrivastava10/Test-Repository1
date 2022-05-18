package PageObjects;

import Utils.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;

public class WishlistPage {

    private WebDriver driver;

    Utilities utilities = null;

    //Locators
    //Wishlist icon
    @FindBy(how = How.XPATH, using = "//div[preceding-sibling::div[@class='header-my-account']]/a[@title='Wishlist']")
    private WebElement wbWishlistIcon;

    //Wishlist table
    final String strWishlistTable = "//tbody[@class='wishlist-items-wrapper']";
    @FindBy(how = How.XPATH, using = strWishlistTable)
    private WebElement wbWishlistTable;

    //Product added successfully
    @FindBy(how = How.XPATH, using = "//div[text()='Product added to cart successfully']")
    private WebElement wbProductAddedToCart;


    //Constructor
    public WishlistPage(WebDriver driver){
        this.driver=driver;
        utilities = Utilities.getInstance(driver);

        //Initialize Elements
        PageFactory.initElements(driver, this);
    }

    //click on wishlist icon
    public void clickOnWishlistIcon(){
        utilities.waitForMeToBeClickable(wbWishlistIcon).click();
        WebElement wbWishlistTableLoaded = utilities.waitForMe(By.xpath(strWishlistTable), 30);
    }

    //Count of number of products in wishlist table
    public int getNumberOfProductsAdded(){
        WebElement wbWishlistTableLoaded = utilities.waitForMe(wbWishlistTable);
        return wbWishlistTableLoaded.findElements(By.xpath(".//tr")).size();
    }

    /*
    Add To cart for minimum price product in the wishlist and returns the product name to be verified in the cart
     */
    public WebElement searchForMinimumPriceProduct()
    {
        List<WebElement> AllRows = wbWishlistTable.findElements(By.xpath(".//tr"));
        //loop through all product rows
        WebElement wbPrice, wbRowWithMinimumPrice;
        String strTempPrice = null, strMinimumPriceProductName = null;
        By byPriceXPath = By.xpath(".//td[@class= 'product-price']//span[@class='woocommerce-Price-amount amount'][not(parent::del[@aria-hidden = 'true'])]/bdi");
        //assume the first available price as minimum, substring removes the currency and extracts the numeric part
        float fMinimumPrice = Float.parseFloat(AllRows.get(0).findElement(byPriceXPath).getText().substring(1).trim());
        wbRowWithMinimumPrice = AllRows.get(0);
        System.out.println("Initial minimum price: " + wbRowWithMinimumPrice);
        float fTempPrice;
        //loop through all rows to get the minimum price and the "add to cart" webelement
        for(WebElement wbRow: AllRows)
        {
            //get only the first price in a range (ex. 25-30 should give 25) and not striked off price (ex. 25(strikedoff) 19 must give 19 and not 25)
            wbPrice = wbRow.findElement(byPriceXPath);
            strTempPrice = wbPrice.getText().trim();
            //substring of $34.00 is 34.00- numeric extraction
            fTempPrice = Float.parseFloat(strTempPrice.substring(1));
            if(fMinimumPrice > fTempPrice)
            {
                fMinimumPrice = fTempPrice;
                wbRowWithMinimumPrice = wbRow;
            }
        }

        System.out.println("Minimum price: " + fMinimumPrice);
        
        return wbRowWithMinimumPrice;
    }

    //add the minimum price product in cart
    public String addToCartForMinimumPrice(WebElement wbRowWithMinimumPrice){
        //get the add to cart webElement for minimum price
        WebElement wbAddToCartForMinimumPrice = wbRowWithMinimumPrice.findElement(By.xpath(".//td[@class='product-add-to-cart']/a"));
        //get the product name for minimum price
        String strMinimumPriceProductName = wbRowWithMinimumPrice.findElement(By.xpath(".//td[@class= 'product-name']/a")).getText().trim();

        //add to cart the item which has the minimum price
        utilities.waitForMeToBeClickable(wbAddToCartForMinimumPrice).click();
        Assert.assertTrue(wbProductAddedToCart.isDisplayed(), "Product with minimum price is added to cart successfully");

        return strMinimumPriceProductName;
    }


}
