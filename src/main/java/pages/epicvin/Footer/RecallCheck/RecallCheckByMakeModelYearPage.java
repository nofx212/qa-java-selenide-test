package pages.epicvin.Footer.RecallCheck;

import com.codeborne.selenide.SelenideElement;
import interfaces.Meta;

import static com.codeborne.selenide.Selenide.$x;

public class RecallCheckByMakeModelYearPage implements Meta {

    public static SelenideElement titleH1 = $x("//div[@class='container holder']//h1");
    public static SelenideElement block1 = $x("//section[@id='section1']//h2");
    public static SelenideElement block2 = $x("//div[@class='form-widget__wrap']//h2");
    public static SelenideElement block3 = $x("//section[@id='section2']//h2");
    public static SelenideElement block4 = $x("//section[@id='section3']//h2");
    public static SelenideElement block5 = $x("//section[@id='section4']//h2");
    public static SelenideElement block6 = $x("//section[@id='section5']//h2");


    public String recallCheckBlock(SelenideElement block) {
        return block.getText();
    }
}
