package PageObjects;

import Utils.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class CartPage {

    private WebDriver driver;

    Utilities utilities = null;

    //Locators
    //Cart icon
    @FindBy(how = How.XPATH, using = "//div[preceding-sibling::div/div[@class='header-search-form']]//a[@title='Cart']")
    private WebElement wbCartIcon;

    //Cart table
    final String strCartTable = "//form[@class='woocommerce-cart-form']//tbody";
    @FindBy(how = How.XPATH, using = strCartTable)
    private WebElement wbCartTable;

    //Constructor
    public CartPage(WebDriver driver){
        this.driver=driver;
        utilities = Utilities.getInstance(driver);

        //Initialize Elements
        PageFactory.initElements(driver, this);
    }

    //click on cart icon
    public void clickOnCartIcon(){
        utilities.waitForMeToBeClickable(wbCartIcon).click();
        WebElement wbWishlistTableLoaded = utilities.waitForMe(By.xpath(strCartTable), 30);
    }

    //check the cart contains the minimum price product added from wishlist
    public String VerifyProductAddedToCart(){
        return wbCartTable.findElement(By.xpath("//tr/td[@class='product-name']")).getText().trim();
    }


}
