package PageObjects;

import Utils.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    private WebDriver driver;

    Utilities utilities = null;

    //Page URL
    private static String WEBSITE_URL="https://testscriptdemo.com";

    //Locators
    //Women's Bikini
    final String strBikiniPath = "//div[preceding-sibling::a/h2[text()='Bikini']]/div[@class='product-wishlist']//a";
    @FindBy(how = How.XPATH, using = strBikiniPath)
    private WebElement wbWomensClothingBikini;

    //Women's Bikini added to Wishlist
    @FindBy(how = How.XPATH, using = "//div[preceding-sibling::a/h2[text()='Bikini']]/div[@class='product-wishlist']//span[@class='feedback']")
    private WebElement wbWomensClothingBikiniAddedToWishlist;

    //Women's Hard top
    @FindBy(how = How.XPATH, using = "//div[preceding-sibling::a/h2[text()='Hard top']]/div[@class='product-wishlist']//a")
    private WebElement wbWomensClothingHardTop;

    //Women's Hard top added to Wishlist
    @FindBy(how = How.XPATH, using = "//div[preceding-sibling::a/h2[text()='Hard top']]/div[@class='product-wishlist']//span[@class='feedback']")
    private WebElement wbWomensClothingHardTopAddedToWishlist;

    //Men's Clothing Tab
    @FindBy(how = How.ID, using = "elementor-tab-title-9292")
    private WebElement wbMensClothingTab;

    //Womens's Clothing Tab
    @FindBy(how = How.ID, using = "elementor-tab-title-9291")
    private WebElement wbWomensClothingTab;

    //Watches Tab
    @FindBy(how = How.ID, using = "elementor-tab-title-9293")
    private WebElement wbWatchesTab;

    //Men's Black trousers
    @FindBy(how = How.XPATH, using = "//div[preceding-sibling::a/h2[text()='Black trousers']]/div[@class='product-wishlist']//a")
    private WebElement wbMensClothingBlackTrousers;

    //Men's Black trousers added as product to wishlist
    @FindBy(how = How.XPATH, using = "//div[preceding-sibling::a/h2[text()='Black trousers']]/div[@class='product-wishlist']//span[@class='feedback']")
    private WebElement wbMensClothingBlackTrousersAddedToWishlist;

    //Watches
    @FindBy(how = How.XPATH, using = "//div[preceding-sibling::a/h2[text()='Modern']]/div[@class='product-wishlist']//a")
    private WebElement wbWatchesModern;

    //Watches added as product to wishlist
    @FindBy(how = How.XPATH, using = "//div[preceding-sibling::a/h2[text()='Modern']]/div[@class='product-wishlist']//span[@class='feedback']")
    private WebElement wbWatchesModernAddedToWishlist;

    //Constructor
    public HomePage(WebDriver driver){
        this.driver=driver;
        driver.get(WEBSITE_URL);

        utilities = Utilities.getInstance(driver);

        //wait for the page to load. Waiting for Featured text on the home page
        utilities.FluentWait(By.xpath("//span/b[text()='Featured']"));
        utilities.waitForMe(By.xpath("//div[preceding-sibling::a/h2[text()='Black pants']]/div[@class='product-wishlist']//a"), 60);
        System.out.println("Page loaded now");

        //Initialize Elements
        PageFactory.initElements(driver, this);
    }

    //click on Men's Clothing tab
    public void clickOnMensClothingTab(){
        utilities.waitForMeToBeClickable(wbMensClothingTab).click();
    }

    //click on Women's Clothing tab
    public void clickOnWomesClothingTab(){
        utilities.waitForMeToBeClickable(wbWomensClothingTab).click();
    }

    //click on Watches tab
    public void clickOnWatchesTab(){
        utilities.waitForMeToBeClickable(wbWatchesTab).click();
    }

    public void AddToWishlist(String strProductName){
        switch (strProductName) {
            case "Bikini":
                //pull the element into view
                WebElement wbBikini = driver.findElement(By.xpath("//div[preceding-sibling::a/h2[text()='Bikini']]/div[@class='product-wishlist']//a"));
                //WebElement wbBikini = utilities.waitForMe(By.xpath(strBikiniPath), 30);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", wbBikini);

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //click to add to wishlist
                //utilities.waitForMe(By.xpath(strBikiniPath), 30);
                utilities.waitForMeToBeClickable(wbWomensClothingBikini).click();
                //wait for the element to be added
                utilities.waitForMe(wbWomensClothingBikiniAddedToWishlist, 60);
                System.out.println("Bikini added");
                break;
            case "Hard top":
                utilities.waitForMeToBeClickable(wbWomensClothingHardTop).click();
                utilities.waitForMe(wbWomensClothingHardTopAddedToWishlist, 60);
                System.out.println("Hard top added");
                break;
            case "Watch Modern":
                clickOnWatchesTab();
                utilities.waitForMeToBeClickable(wbWatchesModern).click();
                utilities.waitForMe(wbWatchesModernAddedToWishlist, 60);
                System.out.println("Watch oldies added");
                break;
            case "Black trousers":
                clickOnMensClothingTab();
                utilities.waitForMeToBeClickable(wbMensClothingBlackTrousers).click();
                utilities.waitForMe(wbMensClothingBlackTrousersAddedToWishlist, 60);
                System.out.println("Black Pants added");
                break;
        }
    }
}
