package pages.epicvin.Footer.LicensePlate;

import com.codeborne.selenide.SelenideElement;
import interfaces.Meta;
import interfaces.SearchByVinPlate;
import org.openqa.selenium.By;
//import org.openqa.selenium.By;
//import pages.epicvin.Precheck.PrecheckPage;

import static com.codeborne.selenide.Selenide.*;

public class LicensePlateLookupPage implements SearchByVinPlate, Meta {

    public static SelenideElement title = $x("//h1[contains(@class,'heading')]");
    public static SelenideElement subTitle = $x("//p[@class='promo__text']");
    public static SelenideElement block1 = $x("//section[@id='section1']//h2");
    public static SelenideElement block2 = $x("//section[@id='section2']//h2");
    public static SelenideElement block3 = $x("//section[@id='section3']//h2");

    public static SelenideElement block4 = $x("//section[@id='section4']//h2");
    public static SelenideElement block5 = $x("//section[@id='section5']//h2");
    public static SelenideElement block6 = $x("//section[@id='section5']//section//h3");
    public static SelenideElement block7 = $x("//section[@id='section5']//section[2]//h3");
    public static SelenideElement block8 = $x("//section[@id='section6']//h2");
    public static SelenideElement block9 = $x("//section[@id='section7']//h2");
    public static SelenideElement block10 = $x("//section[@id='section8']//h2");
    public static SelenideElement block11 = $x("//section[@id='section9']//h2");
    public static SelenideElement block12 = $x("//section[@id='section10']//p");
    public static SelenideElement faq = $x("//section[@id='faq']//h2");
    private final SelenideElement stateAlabama = $(By.partialLinkText("Alabama"));


    public String licensePlateBlock(SelenideElement block) {
        return block.getText();
    }

    public LicensePlateLookupByStatePage clickStateAl() {
        executeJavaScript("arguments[0].click()", stateAlabama);
        return new LicensePlateLookupByStatePage();
    }
}
