package pages.epicvin.Main;

import com.codeborne.selenide.BasicAuthCredentials;
import com.codeborne.selenide.SelenideElement;
import interfaces.Dispute;
import interfaces.Menu;
import interfaces.Meta;
import interfaces.SearchByVinPlate;
import org.openqa.selenium.By;
import pages.epicvin.Account.MyReportsPage;
import pages.epicvin.Account.SubscriptionsPage;
import pages.epicvin.Authentication.LoginPage;
import pages.epicvin.Authentication.RegistrationPage;
import pages.epicvin.Decoder.VinDecoder.VinDecoderPage;
import pages.epicvin.Report.SampleVinPage;

import java.time.Duration;

import static com.codeborne.selenide.AuthenticationType.BASIC;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.currentFrameUrl;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static constants.Constants.*;

public class MainPage implements Menu<MyReportsPage, SubscriptionsPage>, SearchByVinPlate, Dispute, Meta {

    private final SelenideElement loginButton = $x("//a[contains(@class,'button--login')]");
    private final SelenideElement tabLogin = $x("//a[contains(text(),'Login')]");
    private final SelenideElement accountId = $x("//span[@class='acc-menu__id']");
    private final SelenideElement sampleReport = $(By.partialLinkText("Sample Report"));
    private final SelenideElement vinDecoder = $(By.partialLinkText("VIN Decoder"));
    private final SelenideElement langDropDown = $(By.id("langDropdownDesktop"));
    private final SelenideElement ES = $(new By.ByPartialLinkText("Español"));
    private final SelenideElement FR = $(new By.ByPartialLinkText("Français"));
    private final SelenideElement PL = $(new By.ByPartialLinkText("Polski"));
    private final SelenideElement RU = $(new By.ByPartialLinkText("Русский"));
    private final SelenideElement AR = $(new By.ByPartialLinkText("العربية"));
    public static SelenideElement mainTitle = $x("//span[@class='desktop']");
    public static SelenideElement checkHistoryBlock = $x("//section[@class='check-histoty']//h2");
    public static SelenideElement videoBlock = $x("//section[@class='video']//h2");
    public static SelenideElement reviewsBlock = $x("//h2[contains(@class,'reviews__title')]");
    public static SelenideElement supportBlock = $x("//h2[contains(@class,'support__title')]");
    public static SelenideElement carSearchBlock = $x("//section[@class='search-car']//h2");
    public static SelenideElement similarBlogBlock = $x("//section[@class='blog-similar']//h2");
    public static SelenideElement faqBlock = $x("//section[@class='faq']//h2");
    public static SelenideElement saveBlock = $x("//section[@class='save-banner']//h2");
    private final SelenideElement accessibilityWidget = $x("//button[contains(@class,'footer__bottom-ally')]");
    public static SelenideElement accessibilityWidgetMenu = $x("//div[contains(@class,'asw-translate')]");
    private final SelenideElement validationState = $x("//span[contains(@class,'find-vin__tooltip--state')]");
    private final SelenideElement validationPlate = $x("//span[contains(@class,'find-vin__tooltip--plate')]");
    public final SelenideElement validationError = $x("//div[contains(@class,'bs-tooltip-bottom show')]//div[@class='tooltip-inner']");

    public MainPage() {
        open(BASE_URL_EPICVIN, BASIC,
                new BasicAuthCredentials(BASE_URL_DEV, DEV_LOGIN_BASIC_AUTH_EPICVIN, DEV_PASS_BASIC_AUTH_EPICVIN));
        sleep(1000);
    }

    public String getUrlMainPage() {
        getWebDriver().getCurrentUrl();
        return currentFrameUrl();
    }

    @Override
    public String accountID() {
        return accountId.getText().replaceAll("[^\\d.]", "");
    }


    public VinDecoderPage clickVinDecoder() {
        executeJavaScript("arguments[0].click()", vinDecoder);
        sleep(3000);
        return new VinDecoderPage();
    }

    public LoginPage clickLoginButton() {
        loginButton.click();
        sleep(1000);
        executeJavaScript("arguments[0].click()", tabLogin);
        return new LoginPage();
    }

    public RegistrationPage clickRegistration() {
        loginButton.click();
        return new RegistrationPage();
    }

    public SampleVinPage clickSampleVinButton() {
        executeJavaScript("arguments[0].click()", sampleReport);
        return new SampleVinPage();
    }

    public void clickLangDropDown() {
        langDropDown.click();
    }

    public String langES() {
        return ES.getText();
    }

    public String langFR() {
        return FR.getText();
    }

    public String langPL() {
        return PL.getText();
    }

    public String langRU() {
        return RU.getText();
    }

    public String langAR() {
        return AR.getText();
    }

    public String mainBlocks(SelenideElement block) {
        return block.getText();
    }

    public void clickAccessibilityWidget() {
        if (accessibilityWidget.isDisplayed()) {
            sleep(3000);
            accessibilityWidget.click();
            accessibilityWidgetMenu.shouldBe(visible, Duration.ofSeconds(5));
        }
    }

    @Override
    public MyReportsPage clickMyReports(Class<MyReportsPage> pageClass) {
        clickMyAccountNew();
        myReportsNew.click();
        try {
            return pageClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Error while creating an instance of " + pageClass.getSimpleName(), e);
        }
    }

    public String getValidationState() {
        return validationState.getAttribute("data-original-title");
    }

    public String getValidationPlate() {
        return validationPlate.getAttribute("data-original-title");
    }
}
