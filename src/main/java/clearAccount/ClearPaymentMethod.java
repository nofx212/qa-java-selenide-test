package clearAccount;

import com.codeborne.selenide.Selenide;

import static com.codeborne.selenide.Selenide.refresh;

public class ClearPaymentMethod {

    public ClearPaymentMethod(String url) {
        Selenide.open(url);
        refresh();
    }
}
