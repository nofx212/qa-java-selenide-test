package clearAccount;

import com.codeborne.selenide.Selenide;

public class ClearReviews {

    public ClearReviews(String url) {
        Selenide.open(url);
    }
}
