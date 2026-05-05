package pages.epicvin.Price;

import com.codeborne.selenide.SelenideElement;
import interfaces.Meta;
import pages.epicvin.Precheck.PrecheckPage;

import static com.codeborne.selenide.Selenide.*;

public class PricePage extends PrecheckPage implements Meta {
    private final SelenideElement titleH1 = $x("//h1[contains(@class,'prices__title')]");
    private final SelenideElement getFirstPack = $x("//div[@data-id='1']");

    public String titleH1() {
        return titleH1.getText();
    }
    
    @Override
    public void clickFirstPack() {
        getFirstPack.click();
    }
}
