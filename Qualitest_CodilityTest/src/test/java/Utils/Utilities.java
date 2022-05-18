package Utils;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.function.Function;

//Singleton class to be called only once
public class Utilities {

    private WebDriver driver = null;
    final int DEFAULT_TIMEOUT = 30;
    private static Utilities singleInstance = null;

    private Utilities(WebDriver driver) {
        this.driver = driver;
    }

    //static method to create single instance of Utilities class
    public static Utilities getInstance(WebDriver driver) {
        if (singleInstance == null) {
            singleInstance = new Utilities(driver);
        }

        return singleInstance;
    }

    public WebElement waitForMe(By by) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public WebElement waitForMe(By by, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public WebElement waitForMe(WebElement locatorname) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        return wait.until(ExpectedConditions.visibilityOf(locatorname));
    }

    public WebElement waitForMe(WebElement locatorname, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOf(locatorname));
    }

    public WebElement waitForMeToBeClickable(WebElement locatorname) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        return wait.until(ExpectedConditions.elementToBeClickable(locatorname));
    }

    public WebElement waitForMeToBeClickable(WebElement locatorname, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.elementToBeClickable(locatorname));
    }

    public void FluentWait(By by) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException .class);

        WebElement tempWebElement = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(by);
            }
        });
    }
}
