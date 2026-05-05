package pages.epicvin.Footer;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class WhereToFindVinPage {

    public static SelenideElement title = $x("//h1[contains(@class,'heading')]");
    public static SelenideElement block1 = $(By.id("nav0"));
    public static SelenideElement block2 = $(By.id("nav1"));
    public static SelenideElement block3 = $(By.id("nav2"));
    public static SelenideElement block4 = $(By.id("nav3"));
    public static SelenideElement block5 = $(By.id("nav4"));
    public static SelenideElement block6 = $(By.id("nav5"));
    public static SelenideElement block7 = $(By.id("nav6"));
    public static SelenideElement block8 = $(By.id("nav7"));
    public static SelenideElement faq = $x("//h2[contains(text(),'Frequently Asked Questions')]");


    public String getText(SelenideElement element) {
        return element.getText();
    }
}
