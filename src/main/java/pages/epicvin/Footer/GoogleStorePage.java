package pages.epicvin.Footer;

import static com.codeborne.selenide.WebDriverRunner.currentFrameUrl;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class GoogleStorePage {

    public String getUrl() {
        getWebDriver().getCurrentUrl();
        return currentFrameUrl();
    }
}
