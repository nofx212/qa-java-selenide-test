package pages.epicvin.Footer;

import com.codeborne.selenide.SelenideElement;
import interfaces.Meta;
import interfaces.SearchByVinPlate;

import static com.codeborne.selenide.Selenide.$x;

public class VinFraudCheckPage implements Meta, SearchByVinPlate {

    public static SelenideElement titleH1 = $x("//h1[contains(@class,'heading')]");
    public static SelenideElement subTitle = $x("//p[@class='promo__text']");
    public static SelenideElement block1 = $x("//section[@id='section1']//h2");
    public static SelenideElement block2 = $x("//section[@id='section2']//h2");
    public static SelenideElement block3 = $x("//section[@id='section3']//h2");
    public static SelenideElement block4 = $x("//section[@id='section4']//h2");
    public static SelenideElement block5 = $x("//section[@id='section5']//h2");
    public static SelenideElement block6 = $x("//section[@id='section6']//h2");
    public static SelenideElement block7 = $x("//section[@id='section7']//h2");
    public static SelenideElement faq = $x("//section[@id='faq']//h2");
    public static SelenideElement similarArticles = $x("//section[@id='similar-blog']//h2");


    public String vinFraudCheckBlock(SelenideElement block) {
        return block.getText();
    }
}
