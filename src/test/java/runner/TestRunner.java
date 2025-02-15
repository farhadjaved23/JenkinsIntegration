package org.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features", // Path to feature files
        glue = "steps", // Path to step definitions
        plugin = {"pretty", "html:target/cucumber-reports.html"}, // Reporting options
        tags = "@smoke or @regression" // Filters scenarios by tags
)
public class TestRunner extends AbstractTestNGCucumberTests {
}
