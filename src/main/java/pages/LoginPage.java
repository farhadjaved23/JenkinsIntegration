package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.*;

public class LoginPage {
    private WebDriver driver;

    private By cookieDisagreeBtn = By.xpath("//div[@class=\"qc-cmp2-summary-buttons\"]//span[text()=\"DISAGREE\"]");
    private By menMenu = By.linkText("Men");
    private By menSubMenu = By.xpath("//*[@id=\"ui-id-17\"]");
    private By menLink = By.xpath("//*[@id=\"ui-id-2\"]/li[3]/ul/li[1]/ul/li[1]");
    private By usernameField = By.id("email");
    private By passwordField = By.id("pass");
    private By loginButton = By.id("send2");
    private  By item_color = By.xpath("//*[@id=\"maincontent\"]/div[3]/div/div[2]/div[5]/div/div/ol/li[1]/div/div/div[3]/div[2]/div");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickDisagreeBtn() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(cookieDisagreeBtn));
        driver.findElement(cookieDisagreeBtn).click();
    }

    public void enterUsername(String username) {
        driver.findElement(usernameField).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    public void hoverMenu(){
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(menMenu))
                .moveToElement(driver.findElement(menSubMenu))
                .build().perform();
    }
    public ArrayList<String> getMenuText(){
        ArrayList<String> menuValues = new ArrayList<>();
        List<WebElement> menuItems = driver.findElements(By.xpath("//*[@id=\"ui-id-2\"]/li[3]/ul/li[1]/ul/li"));
        for (WebElement item : menuItems) {
            menuValues.add(item.getText());
        }
        return menuValues;
    }
    public Boolean compareMenuText(){
        Boolean isTrue = false;
        String[] arr = {"Jackets","Hoodies & Sweatshirts","Tees","Tanks"};
        ArrayList<String> arrayListFromArray = new ArrayList<>(Arrays.asList(arr));
        ArrayList<String> list = getMenuText();
        if(list.equals(arrayListFromArray)){
            isTrue=true;
        }
        else{
            isTrue=false;
        }
        return isTrue;
    }
    public void openMenuOnNewTab(){
        Actions action = new Actions(driver);
        action.contextClick(driver.findElement(menLink)).perform();
        action.keyDown(Keys.CONTROL).click(driver.findElement(menLink)).keyUp(Keys.CONTROL).perform();
        Set<String> listOfTabs = new HashSet<>();
        listOfTabs = driver.getWindowHandles();
        System.out.println(listOfTabs);
        ArrayList<String> list = new ArrayList<>(listOfTabs);
        driver.switchTo().window(list.get(1));
    }
    public void getAllLinks() throws IOException {
        List<WebElement> links = driver.findElements(By.tagName("a"));
        for (WebElement link : links) {
            FluentWait<WebDriver> wait = new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(5))
                    .pollingEvery(Duration.ofSeconds(2))
                    .ignoring(StaleElementReferenceException.class);
            String href = link.getAttribute("href");
            if (href == null || href.isEmpty()) {
                System.out.println("Skipping null or empty link.");
                continue;
            }
            System.out.println("Checking URL: " + href);
            checkBrokenLinks(href);
        }
    }

    public static void checkBrokenLinks(String url) throws IOException {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.connect();

            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == 200) {
                System.out.println(url + " is valid. Response: " + httpURLConnection.getResponseMessage());
            } else {
                System.out.println(url + " is broken. Response Code: " + responseCode + ", Message: " + httpURLConnection.getResponseMessage());
            }
            httpURLConnection.disconnect();
        } catch (Exception e) {
            System.out.println(url + " is invalid. Exception: " + e.getMessage());
        }
    }
    public void SelectRandomColor(){
        int rand = utils.commonMethods.randomNumber(1,3);
        driver.findElement(By.xpath("(//*[@id=\"maincontent\"]/div[3]/div[1]/div[3]/ol/li[1]/div/div/div[2]/div[2]/div//div[@class=\"swatch-option color\"])["+rand+"]"))
                .click();
    }
    public void SelectRandomItem(){
        List<WebElement> item = driver.findElements(By.xpath("//ol[@class=\"products list items product-items\"]//li"));
        int rand = utils.commonMethods.randomNumber(1,item.size());
        driver.findElement(By.xpath("(//*[@id=\"maincontent\"]/div[3]/div[1]/div[3]//li)["+rand+"]"))
                .click();
    }
}
