package clearAccount;

import com.codeborne.selenide.Selenide;

public class ClearTrialSubscriptions {

    public ClearTrialSubscriptions(String url) {
        Selenide.open(url);
    }
}
