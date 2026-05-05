package pages.epicvin.Footer;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class AboutUsPage {

    public static SelenideElement title = $x("//h1[contains(@class,'about__title')]");
    public static SelenideElement block1 = $x("//div[@class='reg-content__main']//section[1]//h2");
    public static SelenideElement block2 = $x("//div[@class='reg-content__main']//section[2]//h2");
    public static SelenideElement block3 = $x("//div[@class='reg-content__main']//section[3]//h2");
    public static SelenideElement block4 = $x("//div[@class='reg-content__main']//section[4]//h2");
    public static SelenideElement block5 = $x("//div[@class='reg-content__main']//section[5]//h2");
    public static SelenideElement block6 = $x("//div[@class='reg-content__main']//section[6]//h2");
    public static SelenideElement block7 = $x("//div[@class='reg-content__main']//section[7]//h2");


    public String aboutUsBlock(SelenideElement block) {
        return block.getText();
    }
}
