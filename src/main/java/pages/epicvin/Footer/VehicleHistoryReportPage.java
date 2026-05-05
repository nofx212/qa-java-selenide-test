package pages.epicvin.Footer;

import com.codeborne.selenide.SelenideElement;
import interfaces.Meta;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class VehicleHistoryReportPage implements Meta {
    public static SelenideElement h1Title = $x("//h1[contains(@class,'heading')]");
    public static SelenideElement block1 = $(By.id("vhSeoLabel"));
    public static SelenideElement block2 = $(By.id("vhHowLabel"));
    public static SelenideElement block3 = $(By.id("vhWhenLabel"));
    public static SelenideElement block4 = $x("//div[@class='vh-where__text-content']//h2");
    public static SelenideElement block5 = $x("//div[@class='vh-where__bottom']//section[1]//h3");
    public static SelenideElement block6 = $x("//div[@class='vh-where__bottom']//section[2]//h3");
    public static SelenideElement block7 = $(By.id("offerBannerLabel"));
    public static SelenideElement block8 = $x("//section[@id='customers']//h2");
    public static SelenideElement faq = $x("//section[@id='faq']//h2");


    public String vehicleHistoryReportBlock(SelenideElement block) {
        return block.getText();
    }
}
