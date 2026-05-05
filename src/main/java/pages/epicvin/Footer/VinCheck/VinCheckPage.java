package pages.epicvin.Footer.VinCheck;

import com.codeborne.selenide.SelenideElement;
import interfaces.Meta;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public class VinCheckPage implements Meta {

    private final SelenideElement stateAlabama = $(By.partialLinkText("Alabama VIN Check"));
    public static SelenideElement titleH1 = $x("//h1[contains(@class,'heading')]");
    public static SelenideElement block1 = $x("//div[@class='reg-content__main']//section[1]//h2");
    public static SelenideElement block2 = $x("//div[@class='reg-content__main']//section[2]//h2");
    public static SelenideElement block3 = $x("//div[@class='reg-content__main']//section[3]//h2");
    public static SelenideElement block4 = $x("//div[@class='reg-content__main']//section[4]//h2");
    public static SelenideElement block5 = $x("//div[@class='reg-content__main']//section[5]//h2");
    public static SelenideElement block6 = $x("//div[@class='reg-content__main']//section[6]//h2");
    public static SelenideElement block7 = $x("//section[@id='similar-blog']//h2");


    public VinCheckByStatePage chooseStateAlabama() {
        executeJavaScript("arguments[0].click()", stateAlabama);
        return new VinCheckByStatePage();
    }

    public String vinCheckBlock(SelenideElement block) {
        return block.getText();
    }
}
