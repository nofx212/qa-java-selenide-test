package pages.epicvin.Footer.RecallCheck;

import com.codeborne.selenide.SelenideElement;
import interfaces.Meta;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.executeJavaScript;

public class RecallCheckByMakePage implements Meta {

    public static SelenideElement titleH1 = $x("//div[@class='container holder']//h1");
    public static SelenideElement block1 = $x("//section[@id='section1']//h2");
    public static SelenideElement block2 = $x("//section[@id='section2']//h2");
    public static SelenideElement block3 = $x("//section[@id='section3']//h2");
    public static SelenideElement block4 = $x("//section[@class='reg-content__section'][3]//h2");
    public static SelenideElement block5 = $x("//section[@class='reg-content__section'][4]//h2");
    private final SelenideElement btnShowMore = $x("//button[contains(@class,'reg-content__view-btn')]");
    private final SelenideElement block6 = $x("//div[contains(@class,'hidden-content')]//section[@class='reg-content__section']//h2");
    public static SelenideElement faq = $x("//section[contains(@class,'section-faq')]//h2");


    public String recallCheckBlock(SelenideElement block) {
        return block.getText();
    }

    public String showBlock() {
        executeJavaScript("arguments[0].click()", btnShowMore);
        return block6.getText();
    }
}
