package pages.epicvin.Footer;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.currentFrameUrl;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class ChromeExtensionPage {

    public final SelenideElement addExtBanner = $x("//a[contains(@class,'banner-ext__add')]");
    public static SelenideElement extBannerText = $x("//div[@class='banner-ext']//p[@class='banner-ext__text desktop']");
    public static SelenideElement titleH1 = $x("//h1[contains(@class,'ext-top__title')]");
    public static SelenideElement block1 = $x("//section[@class='ext-video']//h2");
    public static SelenideElement block2 = $x("//section[@class='ext-snake']//h2");
    public static SelenideElement block3 = $x("//section[@class='ext-snake']//h3");
    public static SelenideElement block4 = $x("//section[@class='ext-snake__section']//h3");
    public static SelenideElement block5 = $x("//section[@class='ext-snake__section reverse'][2]//h3");
    public static SelenideElement block6 = $x("//section[@class='ext-snake__section'][2]//h3");
    public static SelenideElement howItWorks = $x("//section[@class='ext-work']//h2");
    public static SelenideElement helpToSave = $x("//section[@class='ext-partners']//h2");
    public static SelenideElement faq = $x("//section[@class='faq']//h2");
    public static SelenideElement titleExtText = $x("//h1[contains(text(),'EpicVin Extension')]");
    public static SelenideElement googleCookieBanner = $x("//div[@class='box']//h1");
    public static SelenideElement acceptGoogleCookie = $x("//input[@value='Accept all']");


    public String getUrl() {
        getWebDriver().getCurrentUrl();
        return currentFrameUrl();
    }

    public void addExtension() {
        executeJavaScript("arguments[0].click()", addExtBanner);
        sleep(2000);
    }

    public String getExtText(SelenideElement block) {
        return block.getText();
    }
}
