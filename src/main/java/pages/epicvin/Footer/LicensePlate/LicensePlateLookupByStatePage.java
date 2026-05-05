package pages.epicvin.Footer.LicensePlate;

import com.codeborne.selenide.SelenideElement;
import interfaces.Meta;
import interfaces.SearchByVinPlate;
import pages.epicvin.Precheck.PrecheckPage;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.executeJavaScript;

public class LicensePlateLookupByStatePage implements Meta, SearchByVinPlate {

    public static SelenideElement titleH1 = $x("//h1[contains(@class,'heading')]");
    public static SelenideElement subtitle = $x("//p[@class='promo__text']");
    public static SelenideElement block1 = $x("//section[@id='section1']//h2");
    public static SelenideElement block2 = $x("//section[@id='section2']//h2");
    public static SelenideElement block3 = $x("//section[@id='section3']//h2");
    public static SelenideElement block4 = $x("//section[@id='section4']//h2");
    public static SelenideElement block5 = $x("//section[@id='section5']//h2");
    public static SelenideElement block6 = $x("//section[@id='section6']//h2");
    public static SelenideElement block7 = $x("//section[@id='section7']//h2");
    public static SelenideElement block8 = $x("//section[@id='section8']//h2");
    public static SelenideElement block9 = $x("//section[@id='section9']//h2");
    public static SelenideElement block10 = $x("//section[@id='section10']//h2");
    public static SelenideElement block11 = $x("//section[@id='section11']//h2");
    public static SelenideElement block12 = $x("//section[@id='section12']//h2");
    public static SelenideElement block13 = $x("//section[@id='section13']//h2");
    public static SelenideElement faq = $x("//section[@id='faq']//h2");
    private final SelenideElement firstPlate = $x("//div[@class='plates']//div//div");


    public String licensePlateByStateBlock(SelenideElement block) {
        return block.getText();
    }

    public PrecheckPage clickFirstPlate() {
        executeJavaScript("arguments[0].click()", firstPlate);
        return new PrecheckPage();
    }
}
