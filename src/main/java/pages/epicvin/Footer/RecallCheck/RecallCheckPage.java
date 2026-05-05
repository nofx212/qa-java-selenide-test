package pages.epicvin.Footer.RecallCheck;

import com.codeborne.selenide.SelenideElement;
import interfaces.Meta;
import org.openqa.selenium.By;
import pages.epicvin.Precheck.PrecheckPage;

import static com.codeborne.selenide.Selenide.*;

public class RecallCheckPage implements Meta {
    public static SelenideElement titleH1 = $x("//div[@class='container holder']//h1");
    public static SelenideElement subTitle = $x("//p[@class='promo__text']");
    public static SelenideElement block1 = $x("//section[@id='section1']//h2");
    public static SelenideElement block2 = $x("//section[@id='section2']//h2");
    public static SelenideElement block3 = $x("//section[@id='section3']//h2");
    public static SelenideElement block4 = $x("//section[@id='section4']//h2");
    public static SelenideElement block5 = $x("//section[@id='section5']//h2");
    public static SelenideElement block6 = $x("//div[@class='form-widget__wrap']//h2");
    public static SelenideElement block7 = $x("//section[@id='section7']//h2");
    public static SelenideElement block8 = $x("//section[@id='section8']//h2");
    private final SelenideElement make = $x("//select[@id='makeCode']//option[contains(text(),'AUDI')]");
    private final SelenideElement model = $x("//select[@id='modelCode']//option[contains(text(),'A5')]");
    private final SelenideElement year = $x("//select[@id='yearCode']//option[contains(text(),'2020')]");
    private final SelenideElement btnCheckRecall = $x("//div[@id='checkMakeModelPanelpromo']//button[@type='submit']");
    private final SelenideElement vinTab = $(By.id("checkVinTabpromo"));
    private final SelenideElement licensePlateTab = $(By.id("checkPlateTabpromo"));
    private final SelenideElement inputVin = $(By.id("vin-inputpromo"));
    private final SelenideElement inputPlate = $(By.id("plateNumberpromo"));
    private final SelenideElement state = $x("//select[@id='stateCode']//option[contains(text(),'Florida')]");
    private final SelenideElement btnCheckPlate = $x("//button[contains(@class,'find-vin__plate-btn')]");
    public static SelenideElement recallNumber = $x("//div[@id='collapse0']//dl//div[1]//dt");
    public static SelenideElement recallDate = $x("//div[@id='collapse0']//dl//div[2]//dt");
    public static SelenideElement components = $x("//div[@id='collapse0']//dl//div[3]//dt");


    public String recallCheckBlock(SelenideElement block) {
        sleep(500);
        return block.getText();
    }

    public RecallCheckByMakePage getMakeRecall() {
        make.click();
        btnCheckRecall.click();
        return new RecallCheckByMakePage();
    }

    public RecallCheckByMakeModelPage getMakeModelRecall() {
        make.click();
        model.click();
        btnCheckRecall.click();
        return new RecallCheckByMakeModelPage();
    }

    public RecallCheckByMakeModelYearPage getMakeModelYearRecall() {
        make.click();
        model.click();
        year.click();
        btnCheckRecall.click();
        return new RecallCheckByMakeModelYearPage();
    }

    public PrecheckPage checkRecallByVin(String vin) {
        vinTab.click();
        inputVin.setValue(vin);
        return new PrecheckPage();
    }

    public PrecheckPage checkRecallByLicensePlate(String plate) {
        licensePlateTab.click();
        inputPlate.setValue(plate);
        state.click();
        btnCheckPlate.click();
        return new PrecheckPage();
    }
}
