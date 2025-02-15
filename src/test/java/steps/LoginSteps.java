package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import pages.LoginPage;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class LoginSteps {
    private static final Logger log = LoggerFactory.getLogger(LoginSteps.class);
    private WebDriver driver;
    private LoginPage loginPage;
    private String expectedResult = "Jackets - Tops - Men";
    private String nodeURL = "http://192.168.0.69:4444";  // Removed extra space

    @Before
    public void setup() throws MalformedURLException {
        String browser = System.getProperty("browser", "chrome");  // Get from system properties, default to Chrome
        DesiredCapabilities capabilities = new DesiredCapabilities();

        switch (browser.toLowerCase()) {
            case "chrome":
                capabilities.setBrowserName("chrome");
                break;
            case "firefox":
                capabilities.setBrowserName("firefox");
                break;
            case "edge":
                capabilities.setBrowserName("MicrosoftEdge");
                break;
            default:
                throw new IllegalArgumentException("Browser not supported: " + browser);
        }

        driver = new RemoteWebDriver(new URL(nodeURL), capabilities);
        loginPage = new LoginPage(driver);
    }


    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("I open the login page")
    public void iOpenTheLoginPage() {
        driver.get("https://magento.softwaretestingboard.com/customer/account/login/");
    }

    @When("I enter {string} and {string}")
    public void iEnterUserCredentials(String username, String password) throws InterruptedException {
        HashMap<String, String> userData = new HashMap<>();
        userData.put("username", username);
        userData.put("password", password);

        // Safe Click: Avoid errors if button is not present
        try {
            loginPage.clickDisagreeBtn();
        } catch (Exception ignored) {
            log.info("Disagree button not found, continuing...");
        }

        loginPage.enterUsername(userData.get("username"));
        loginPage.enterPassword(userData.get("password"));
        loginPage.clickLoginButton();
    }

    @Then("I should see the {string}")
    public void iShouldSeeThePOutcome(String msg) {
        String actualMessage = driver.getTitle();
        Assert.assertEquals(actualMessage, msg, "Login page title mismatch!");
    }

    @Then("I should get the list of men menus")
    public void iShouldGetTheListOfMenMenus() {
        loginPage.hoverMenu();
        loginPage.getMenuText();
        loginPage.compareMenuText();
        loginPage.openMenuOnNewTab();
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, expectedResult, "Unable to redirect to the correct page");

        // Random Selection
        loginPage.SelectRandomItem();
    }
}
