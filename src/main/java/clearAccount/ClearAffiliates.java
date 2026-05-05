package clearAccount;

import com.codeborne.selenide.Selenide;

public class ClearAffiliates {

    public ClearAffiliates(String url) {
        Selenide.open(url);
    }
}
