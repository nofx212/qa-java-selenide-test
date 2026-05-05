package pages.epicvin.Report;

import com.codeborne.selenide.SelenideElement;
import interfaces.Menu;
import interfaces.Sample;
import interfaces.SearchByVinPlate;
import pages.epicvin.Account.MyReportsPage;
import pages.epicvin.Account.SubscriptionsPage;
import pages.epicvin.Precheck.PrecheckPage;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.switchTo;

public class SampleVinPage implements Sample, Menu<MyReportsPage, SubscriptionsPage>, SearchByVinPlate {
    private final SelenideElement titleText = $x("//p[contains(@class,'sub-title')]");
    public static SelenideElement titleOdometerCheck = $x("//section[contains(@class,'report__odometer')][2]//h2");


    public String epicVinTitleText() {
        return titleText.getText();
    }

    public String getTitleOdometerCheck() {
        return titleOdometerCheck.getText();
    }

    @Override
    public PrecheckPage searchLotByVinFromNavForm(String vin) {
        setVinFromNavForm(vin);
        switchTo().window(1);
        return new PrecheckPage();
    }
}
