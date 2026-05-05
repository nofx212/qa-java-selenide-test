package clearAccount;

import com.codeborne.selenide.Selenide;

import static com.codeborne.selenide.Selenide.sleep;

public class Dispute {

    public Dispute(String url) {
        sleep(2000);
        Selenide.open(url);
    }
}
