package runner;

import io.cucumber.java.it.Data;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"steps"},
        tags = "not @smoke",
        plugin = {"pretty", "html:C:\\Users\\farha\\IdeaProjects\\BDD-Selenium\\reports\\cucumber-reports.html"}
)
public class TestRunner extends AbstractTestNGCucumberTests {
}
