package pages.epicvin.SocialReviews;

import com.codeborne.selenide.WebDriverRunner;

public class GooglePage {

    public String currentUrl() {
        return WebDriverRunner.url();
    }
}
