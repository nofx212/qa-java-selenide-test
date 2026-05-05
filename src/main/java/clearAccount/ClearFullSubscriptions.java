package clearAccount;

import com.codeborne.selenide.Selenide;

public class ClearFullSubscriptions {

    public ClearFullSubscriptions(String url) {
        Selenide.open(url);
    }
}
