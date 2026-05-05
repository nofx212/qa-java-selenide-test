package base;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import decorator.LogsExtension;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.chrome.ChromeOptions;
import sql.SQLConnection;

import java.sql.SQLException;

public class BaseTest {

    public static void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--user-agent=selenide-test");

        boolean headless = Boolean.parseBoolean(
                System.getProperty("headless", System.getenv().getOrDefault("HEADLESS", "false"))
        );
        if (headless) {
            options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage", "--window-size=1920,1080");
        }

        Configuration.browser = "chrome";
        Configuration.browserCapabilities = options;
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 80000;
        Configuration.pageLoadTimeout = 80000;
        Configuration.headless = headless;

        SelenideLogger.addListener(
                "AllureSelenide",
                new AllureSelenide().screenshots(true).savePageSource(false)
        );
    }

    @BeforeEach
    public void init(TestInfo testInfo) {
        setUp();
        LogsExtension.installSystemOutOnce();
        LogsExtension.setTestName(LogsExtension.buildTestName(testInfo));
    }

    @AfterEach
    public void tearDown() throws SQLException {
        Selenide.closeWebDriver();
        SQLConnection.closeAllConnections();
    }
}
