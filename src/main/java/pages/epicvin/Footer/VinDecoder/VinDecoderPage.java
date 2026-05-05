package pages.epicvin.Footer.VinDecoder;

import com.codeborne.selenide.SelenideElement;
import interfaces.Meta;
import interfaces.SearchByVinPlate;
import pages.epicvin.Footer.BlogPage;
import pages.epicvin.Precheck.PrecheckPage;

import static com.codeborne.selenide.Selenide.*;

public class VinDecoderPage extends BlogPage implements SearchByVinPlate, Meta {
    private final SelenideElement audiFreeVinDecoder = $x("//div[@class='alphabetical-list']//div[1]//ul//li[4]//a");
    public static SelenideElement titleH1 = $x("//h1[contains(@class,'heading')]");
    public static SelenideElement subHeader = $x("//p[@class='promo__text']");
    public static SelenideElement authorBlock = $x("//div[@class='article-head__bottom']//div//a");
    public static SelenideElement block1 = $x("//section[@id='section1']//h2");
    public static SelenideElement block2 = $x("//section[@id='section2']//h2");
    public static SelenideElement block3 = $x("//section[@id='section3']//h2");
    public static SelenideElement videoBlock = $x("//section[@id='sectionVideo']//h2");
    public static SelenideElement block4 = $x("//section[@id='section4']//h2");
    public static SelenideElement block5 = $x("//section[@id='section5']//h2");
    public static SelenideElement block6 = $x("//section[@id='section6']//h2");
    public static SelenideElement block7 = $x("//section[@id='section7']//h2");
    public static SelenideElement block8 = $x("//section[@id='section8']//h2");
    public static SelenideElement block9 = $x("//section[@id='section9']//h2");
    public static SelenideElement block10 = $x("//section[@id='section10']//h2");
    public static SelenideElement block11 = $x("//section[@id='section11']//h2");
    public static SelenideElement block12 = $x("//section[@id='section12']//p");
    public static SelenideElement faq = $x("//section[@id='faq']//h2");
    public static SelenideElement similarArticles = $x("//section[@id='similar-blog']//h2");
    public static SelenideElement ratingWidget = $x("//div[@class='rating-widget']");
    public static SelenideElement authorNameVinDecoder = $x("//div[@class='article-author']//a");
    private final SelenideElement precheckPreview = $x("//section[@class='report-head']//div[@class='report-head__img']//img");

    public VinDecoderByMakePage clickModelAudi() {
        executeJavaScript("arguments[0].click()", audiFreeVinDecoder);
        return new VinDecoderByMakePage();
    }

    public PrecheckPage vehicleFromPrecheckPreview() {
        executeJavaScript("arguments[0].click()", precheckPreview);
        return new PrecheckPage();
    }

    public String vinDecoderBlock(SelenideElement block) {
        return block.getText();
    }

    public void clickAuthorPageFromVinDecoder() {
        executeJavaScript("arguments[0].click()", authorNameVinDecoder);
    }
}
