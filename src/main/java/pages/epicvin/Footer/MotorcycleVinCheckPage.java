package pages.epicvin.Footer;

import com.codeborne.selenide.SelenideElement;
import interfaces.Meta;
import interfaces.SearchByVinPlate;

import static com.codeborne.selenide.Selenide.$x;

public class MotorcycleVinCheckPage implements SearchByVinPlate, Meta {

    public static SelenideElement title = $x("//h1[contains(@class,'heading')]");
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
    public static SelenideElement block14 = $x("//section[@id='section14']//h2");


    public String motoVinCheckBlock(SelenideElement block) {
        return block.getText();
    }
}
