package pages.epicvin.Footer.Dealer;

import com.codeborne.selenide.SelenideElement;
import interfaces.Menu;
import pages.epicvin.Account.MyReportsPage;

import static com.codeborne.selenide.Selenide.$x;

public class DealerPage implements Menu<MyReportsPage, DealerSubscriptionPage> {

    private final SelenideElement titleH1 = $x("//section[@id='dealer-top']//h1");


    public String getTitleH1() {
        return titleH1.getText();
    }
}
