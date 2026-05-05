package pages.epicvin.Decoder.VinDecoder;

import com.codeborne.selenide.SelenideElement;
import interfaces.Meta;
import interfaces.SearchByVinPlate;

import static com.codeborne.selenide.Selenide.*;

public class VinDecoderPage implements SearchByVinPlate, Meta {
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
    public static SelenideElement authorsPageTitle = $x("//div[@class='blog__head-author']//h1");


    public String getBlogBlocks(SelenideElement blog) {
        return blog.getText();
    }

    public String vinDecoderBlock(SelenideElement block) {
        return block.getText();
    }

    public void clickAuthorPageFromVinDecoder() {
        executeJavaScript("arguments[0].click()", authorNameVinDecoder);
    }
}
