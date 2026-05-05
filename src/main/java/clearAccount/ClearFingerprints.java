package clearAccount;

import com.codeborne.selenide.Selenide;

public class ClearFingerprints {

    public ClearFingerprints(String url) {
        Selenide.open(url);
    }
}
