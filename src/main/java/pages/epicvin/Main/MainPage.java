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
import pages.epicvin.Cars.CarsPage;
import pages.epicvin.Footer.*;
import pages.epicvin.Footer.Dealer.DealerPage;
import pages.epicvin.Footer.LicensePlate.LicensePlateLookupPage;
import pages.epicvin.Footer.VehicleQuestions.VehicleQuestionsPage;
import pages.epicvin.Footer.RecallCheck.RecallCheckPage;
import pages.epicvin.Footer.VinCheck.VinCheckPage;
import pages.epicvin.Footer.VinDecoder.VinDecoderPage;
import pages.epicvin.Precheck.VinNotFoundPage;
import pages.epicvin.Price.PricePage;
import pages.epicvin.Report.SampleVinPage;

import java.time.Duration;

import static com.codeborne.selenide.AuthenticationType.BASIC;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.currentFrameUrl;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static constants.Constants.*;

public class MainPage extends CarsPage implements Menu<MyReportsPage, SubscriptionsPage>, SearchByVinPlate, Dispute, Meta {

    private final SelenideElement loginButton = $x("//a[contains(@class,'button--login')]");
    private final SelenideElement tabLogin = $x("//a[contains(text(),'Login')]");
    private final SelenideElement accountId = $x("//span[@class='acc-menu__id']");
    private final SelenideElement sampleReport = $(By.partialLinkText("Sample Report"));
    private final SelenideElement vinDecoder = $(By.partialLinkText("VIN Decoder"));
    private final SelenideElement forDealers = $(By.partialLinkText("Become a Dealer"));
    private final SelenideElement carsForSale = $x("//header[@id='top-menu']//ul//li[7]//a");
    private final SelenideElement ourServices = $(By.id("dropdownMenuButton"));
    private final SelenideElement vinCheckByState = $(By.partialLinkText("VIN Check by State"));
    private final SelenideElement titleCheck = $(By.partialLinkText("Free Title Check by VIN"));
    private final SelenideElement salvageTitleCheck = $(By.partialLinkText("Salvage Title Check"));
    private final SelenideElement vinFraudCheck = $(By.partialLinkText("VIN Fraud Check"));
    private final SelenideElement motorcycleCheck = $(By.partialLinkText("Motorcycle VIN Check"));
    private final SelenideElement dmvCheck = $(By.partialLinkText("DMV VIN Check"));
    private final SelenideElement ownerLookup = $(By.partialLinkText("Vehicle Owner Lookup"));
    private final SelenideElement rvLookup = $(By.partialLinkText("RV VIN Lookup"));
    private final SelenideElement floodDamageCheck = $(By.partialLinkText("Flood Damage Check"));
    private final SelenideElement vehicleHistoryReport = $(By.partialLinkText("Vehicle History Report"));
    private final SelenideElement recallCheck = $x("//a[contains(@class,'link--recall')]");
    private final SelenideElement freeOdometerCheck = $(By.partialLinkText("Free Odometer Check"));
    private final SelenideElement licensePlateLookup = $(By.partialLinkText("License Plate Lookup"));
    private final SelenideElement getChromeExtension = $(By.linkText("Chrome Extension"));
    private final SelenideElement getWindowSticker = $(By.linkText("Window Sticker"));
    private final SelenideElement bannerCookies = $x("//div[@class='cookie-card']//p");
    private final SelenideElement closeCookies = $x("//div[@class='cookie-card']//button");
    private final SelenideElement langDropDown = $(By.id("langDropdownDesktop"));
    private final SelenideElement ES = $(new By.ByPartialLinkText("Español"));
    private final SelenideElement FR = $(new By.ByPartialLinkText("Français"));
    private final SelenideElement PL = $(new By.ByPartialLinkText("Polski"));
    private final SelenideElement RU = $(new By.ByPartialLinkText("Русский"));
    private final SelenideElement AR = $(new By.ByPartialLinkText("العربية"));
    private final SelenideElement epicReports = $x("//a[contains(text(),'Get EpicVIN reports')]");
    private final SelenideElement refundPolicy = $(By.partialLinkText("Refund Policy"));
    private final SelenideElement aboutUs = $(By.partialLinkText("About Us"));
    private final SelenideElement reviews = $(By.partialLinkText("Reviews"));
    private final SelenideElement affiliate = $(By.partialLinkText("Affiliate Program"));
    private final SelenideElement grabVin = $x("//span[contains(@class,'vin-value')]");
    private final SelenideElement contactUs = $(By.partialLinkText("Contact Us"));
    private final SelenideElement blog = $(By.partialLinkText("Blog"));
    private final SelenideElement vehicleQuestions = $x("//ul[@class='extra-menu__list']//li[4]//a");
    private final SelenideElement findVinPopup = $x("//div[contains(@class,'find-vin-info')]//span");
    private final SelenideElement findVinPopupTitle = $x("//p[@class='find-vin-popup__title']");
    private final SelenideElement getMoreInfo = $(By.linkText("Get more info"));
    private final SelenideElement subscribeEmail = $(By.id("subscribeEmail"));
    private final SelenideElement btnSubscribe = $x("//button[contains(@class,'sub-btn')]");
    public static SelenideElement successSub = $x("//div[class='subscribe-form']//p");
    public static SelenideElement mainTitle = $x("//span[@class='desktop']");
    public static SelenideElement checkHistoryBlock = $x("//section[@class='check-histoty']//h2");
    public static SelenideElement videoBlock = $x("//section[@class='video']//h2");
    public static SelenideElement reviewsBlock = $x("//h2[contains(@class,'reviews__title')]");
    public static SelenideElement supportBlock = $x("//h2[contains(@class,'support__title')]");
    public static SelenideElement carSearchBlock = $x("//section[@class='search-car']//h2");
    public static SelenideElement similarBlogBlock = $x("//section[@class='blog-similar']//h2");
    public static SelenideElement faqBlock = $x("//section[@class='faq']//h2");
    public static SelenideElement saveBlock = $x("//section[@class='save-banner']//h2");
    private final SelenideElement btnSearch = $x("//button[contains(@class,'find-car__button')]");
    private final SelenideElement ratingWidget = $x("//div[@class='rating-widget']//p");
    private final SelenideElement ratingStars = $x("//div[@class='rating-widget__rating full']//input[@value='5']");
    public static SelenideElement ratingNote = $x("//p[@class='rating-widget__note']");
    private final SelenideElement accessibilityWidget = $x("//button[contains(@class,'footer__bottom-ally')]");
    public static SelenideElement accessibilityWidgetMenu = $x("//div[contains(@class,'asw-translate')]");
    private final SelenideElement googlePlayIcon = $x("//img[@alt='Google Play']");
    private final SelenideElement validationState = $x("//span[contains(@class,'find-vin__tooltip--state')]");
    private final SelenideElement validationPlate = $x("//span[contains(@class,'find-vin__tooltip--plate')]");
    public final SelenideElement validationError = $x("//div[contains(@class,'bs-tooltip-bottom show')]//div[@class='tooltip-inner']");

    public MainPage() {
        open(BASE_URL_EPICVIN, BASIC,
                new BasicAuthCredentials("stage.epicvin.com", DEV_LOGIN_BASIC_AUTH_EPICVIN, DEV_PASS_BASIC_AUTH_EPICVIN));
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

    public VinCheckPage clickVinCheck() {
        executeJavaScript("arguments[0].click()", vinCheckByState);
        return new VinCheckPage();
    }

    public FreeTitleCheckByVinPage clickTitleCheck() {
        executeJavaScript("arguments[0].click()", titleCheck);
        return new FreeTitleCheckByVinPage();
    }

    public SalvageTitleCheckByVinPage clickSalvageTitleCheck() {
        executeJavaScript("arguments[0].click()", salvageTitleCheck);
        return new SalvageTitleCheckByVinPage();
    }

    public VinFraudCheckPage clickVinFraudCheck() {
        executeJavaScript("arguments[0].click()", vinFraudCheck);
        return new VinFraudCheckPage();
    }

    public VehicleOwnerLookupPage clickOwnerLookup() {
        executeJavaScript("arguments[0].click()", ownerLookup);
        return new VehicleOwnerLookupPage();
    }

    public MotorcycleVinCheckPage clickMotorcycleCheck() {
        executeJavaScript("arguments[0].click()", motorcycleCheck);
        return new MotorcycleVinCheckPage();
    }

    public DmvVinCheckPage clickDmvVinCheck() {
        executeJavaScript("arguments[0].click()", dmvCheck);
        return new DmvVinCheckPage();
    }

    public RVVinLookupPage clickRVVinLookup() {
        executeJavaScript("arguments[0].click()", rvLookup);
        return new RVVinLookupPage();
    }

    public FloodDamageCheckPage clickFloodDamageCheck() {
        executeJavaScript("arguments[0].click()", floodDamageCheck);
        return new FloodDamageCheckPage();
    }

    public VehicleHistoryReportPage clickVehicleHistoryReport() {
        executeJavaScript("arguments[0].click()", vehicleHistoryReport);
        return new VehicleHistoryReportPage();
    }

    public RecallCheckPage clickRecallCheck() {
        ourServices.click();
        executeJavaScript("arguments[0].click()", recallCheck);
        return new RecallCheckPage();
    }

    public FreeOdometerCheckPage clickFreeOdometerCheck() {
        executeJavaScript("arguments[0].click()", freeOdometerCheck);
        return new FreeOdometerCheckPage();
    }

    public LicensePlateLookupPage clickLicensePlateLookup() {
        executeJavaScript("arguments[0].click()", licensePlateLookup);
        return new LicensePlateLookupPage();
    }

    public ChromeExtensionPage clickChromeExtension() {
        executeJavaScript("arguments[0].click()", getChromeExtension);
        return new ChromeExtensionPage();
    }

    public WindowStickerPage clickWindowSticker() {
        executeJavaScript("arguments[0].click()", getWindowSticker);
        return new WindowStickerPage();
    }

    public CarsPage clickCarsForSale() {
        executeJavaScript("arguments[0].click()", carsForSale);
        return new CarsPage();
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

    public DealerPage clickForDealers() {
        executeJavaScript("arguments[0].click()", forDealers);
        return new DealerPage();
    }

    public String checkCookies() {
        return bannerCookies.getText();
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

    public PricePage clickEpicReports() {
        epicReports.click();
        return new PricePage();
    }

    private void clickCloseCookies() {
        closeCookies.click();
    }

    public RefundPolicyPage clickRefundPolicy() {
        if (bannerCookies.isDisplayed()) {
            System.out.println("Cookies are displayed");
            clickCloseCookies();
        }
        executeJavaScript("arguments[0].click()", refundPolicy);
        sleep(3000);
        return new RefundPolicyPage();
    }

    public AboutUsPage clickAboutUs() {
        executeJavaScript("arguments[0].click()", aboutUs);
        return new AboutUsPage();
    }

    public ReviewPage clickReviews() {
        executeJavaScript("arguments[0].click()", reviews);
        return new ReviewPage();
    }

    public AffiliatePage clickAffiliate() {
        executeJavaScript("arguments[0].click()", affiliate);
        return new AffiliatePage();
    }

    public String getGrabBanner(String vin) {
        setInputVin(vin);
        sleep(3000);
        new MainPage();
        return vin;
    }

    public String getGrabVin() {
        sleep(2000);
        return grabVin.getText().replaceAll("(?i)vin#\\s+(\\w+)", "$1");
    }

    public ContactUsPage clickContactUs() {
        executeJavaScript("arguments[0].click()", contactUs);
        return new ContactUsPage();
    }

    public BlogPage clickBlog() {
        executeJavaScript("arguments[0].click()", blog);
        return new BlogPage();
    }

    public VehicleQuestionsPage clickVehicleQuestions() {
        executeJavaScript("arguments[0].click()", vehicleQuestions);
        return new VehicleQuestionsPage();
    }

    public WhereToFindVinPage clickFindVinPopup() {
        findVinPopup.hover();
        sleep(1000);
        if (findVinPopupTitle.getText().equals("Looking for the VIN?")) {
            getMoreInfo.click();
        }
        return new WhereToFindVinPage();
    }

    public void clickFindVinWidget() {
        findVinPopup.hover();
        sleep(1000);
    }

    public String getFindVinPopupTitle() {
        return findVinPopupTitle.getText();
    }

    public void subscribeToNews(String email) {
        executeJavaScript("arguments[0].value = arguments[1];", subscribeEmail, email);
        executeJavaScript("arguments[0].click()", btnSubscribe);
        sleep(1000);
    }

    public String getMainTitle() {
        return mainTitle.getText();
    }

    public String mainBlocks(SelenideElement block) {
        return block.getText();
    }

    @Override
    protected void clickSearchButtonByMakeModel() {
        btnSearch.click();
    }

    public void addRating() {
        if (ratingWidget.getText().contains("98% of users recommend our service.")) {
            executeJavaScript("arguments[0].click()", ratingStars);
        }
    }

    public void clickAccessibilityWidget() {
        if (accessibilityWidget.isDisplayed()) {
            sleep(3000);
            accessibilityWidget.click();
            accessibilityWidgetMenu.shouldBe(visible, Duration.ofSeconds(5));
        }
    }

    public VinNotFoundPage searchWithInvalidVin(String vin) {
        clickTabByVin();
        setInputVin(vin);
        clickCheckVin();
        return new VinNotFoundPage();
    }

    @Override
    public MyReportsPage clickMyReports(Class<MyReportsPage> pageClass) {
        clickMyAccountNew();
        myReportsNew.click();
        try {
            return pageClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создании экземпляра " + pageClass.getSimpleName(), e);
        }
    }

    public GoogleStorePage clickGooglePlayIcon() {
        executeJavaScript("arguments[0].click()", googlePlayIcon);
        switchTo().window(1);
        return new GoogleStorePage();
    }

    public String getValidationState() {
        return validationState.getAttribute("data-original-title");
    }

    public String getValidationPlate() {
        return validationPlate.getAttribute("data-original-title");
    }
}
