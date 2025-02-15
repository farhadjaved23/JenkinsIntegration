package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import pages.LoginPage;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertTrue;

public class LoginSteps {
    private static final Logger log = LoggerFactory.getLogger(LoginSteps.class);
    private WebDriver driver;
    private LoginPage loginPage;
    String exptectedResult = "Jackets - Tops - Men";

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
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
        driver.get("https://magento.softwaretestingboard.com/customer/account/login/referer/aHR0cHM6Ly9tYWdlbnRvLnNvZnR3YXJldGVzdGluZ2JvYXJkLmNvbS8%2C/");
    }

    @When("I enter {string} and {string}")
    public void iEnterUserCredentials(String username, String password) throws InterruptedException {
        HashMap<String,String> userData = new HashMap<>();
        userData.put("username",username);
        userData.put("password",password);
        loginPage.clickDisagreeBtn();
        loginPage.enterUsername(userData.get("username"));
        loginPage.enterPassword(userData.get("password"));
        loginPage.clickLoginButton();
    }


   @Then("I should see the {string}")
    public void iShouldSeeThePOutcome(String msg) {
        String actualMessage = driver.getTitle();
        Assert.assertEquals(msg,actualMessage);
    }

    @Then("I should get the list of men menus")
    public void iShouldGetTheListOfMenMenus() throws IOException {
        loginPage.hoverMenu();
        loginPage.getMenuText();
        loginPage.compareMenuText();
        loginPage.openMenuOnNewTab();
        String actualTitle = driver.getTitle();
//        Assert.assertEquals(exptectedResult,actualTitle,"Unable to redirect on the correct page");
//        loginPage.getAllLinks();
        loginPage.SelectRandomItem();
//        loginPage.SelectRandomColor();
    }
}
