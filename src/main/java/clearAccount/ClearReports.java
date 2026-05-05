package clearAccount;

import com.codeborne.selenide.Selenide;

public class ClearReports {

    public ClearReports(String url) {
        Selenide.open(url);
//        refresh();
    }
}
