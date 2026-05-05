package pages.epicvin.Cars;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public class CarsPage {

    private final SelenideElement selectMake = $x("//select[contains(@id,'formByMake')]//option[contains(text(),'Audi')]");
    private final SelenideElement selectModel = $x("//select[contains(@id,'model')]//option[contains(text(),'A5')]");
    private final SelenideElement selectPrice = $x("//select[contains(@id,'searchMaxPrice')]//option[contains(text(),'$75,000')]");
    private final SelenideElement selectMinPrice = $x("//select[contains(@id,'searchPriceMinPrice')]//option[contains(text(),'$5,000')]");
    private final SelenideElement selectMaxPrice = $x("//select[contains(@id,'searchPriceMaxPrice')]//option[contains(text(),'$50,000')]");
    private final SelenideElement selectType = $x("//select[@id='searchTypeTop']//option[contains(text(),'Sedans')]");
    private final SelenideElement inputZIP = $x("//input[contains(@id,'searchZip')]");
    private final SelenideElement btnSearchByMakeModel = $x("//div[@id='searchByMakeFormTop']//button");
    private final SelenideElement btnSearchByDealer = $x("//div[@id='searchByDealerFormTop']//button");
    private final SelenideElement btnSearchByPrice = $x("//div[@id='searchByPriceFormTop']//button");
    private final SelenideElement btnSearchByType = $x("//div[@id='searchByTypeFormTop']//button");
    public static SelenideElement title = $x("//h1[contains(@class,'cars-top__title')]");
    public static SelenideElement block1 = $(By.id("carsBrandsLabel"));
    public static SelenideElement block2 = $(By.id("carsShopLabel"));
    public static SelenideElement block3 = $(By.id("carsModelsLabel"));
    public static SelenideElement block4 = $x("//h2[contains(@class,'check-histoty__title')]");
    public static SelenideElement block5 = $(By.id("topCitiesLabel"));
    public static SelenideElement block6 = $x("//section[@class='cars-price']//h2");
    public static SelenideElement block7 = $x("//section[@class='cars-offer']//h2");
    public static SelenideElement block8 = $(By.id("topStatesLabel"));
    public static SelenideElement block9 = $(By.id("carsDealershipLabel"));
    public static SelenideElement block10 = $(By.id("carsDescriptionLabel"));
    public static SelenideElement block11 = $x("//section[contains(@class,'cars-blog')]//h2");
    public static SelenideElement faq = $x("//section[@id='faq']//h2");
    private final SelenideElement btnNewCars = $(By.linkText("New cars"));
    private final SelenideElement tabByDealer = $(By.id("searchByDealerTabTop"));
    private final SelenideElement tabByPrice = $(By.id("searchByPriceTabTop"));
    private final SelenideElement tabByType = $(By.id("searchByTypeTabTop"));
    private final SelenideElement yourSubscription =
            $x("//div[@class='modal-body popup__body flex-column']//h2");
    private final SelenideElement btnClose =
            $x("//button[@class='popup__btn btn popup__btn--border']");
    private final SelenideElement getTheFacts = $x("//ol[@class='cars-shop__steps']//li[3]//a");
    private final SelenideElement advancedSearch = $x("//a[contains(@class,'find-car__link')]");
    private final SelenideElement tabVin = $(By.id("checkVinTab"));
    private final SelenideElement inputVin = $(By.id("findVin"));
    private final SelenideElement btnGetStarted = $x("//button[contains(@class,'selling-search__button')]");


    protected void clickMake() {
        selectMake.click();
    }

    protected void clickModel() {
        selectModel.click();
    }

    protected void clickPrice() {
        selectPrice.click();
    }

    protected void clickSearchButtonByMakeModel() {
        btnSearchByMakeModel.click();
    }

    public CarsSearchPage clickSearch() {
        btnSearchByMakeModel.click();
        return new CarsSearchPage();
    }

    public CarsSearchPage searchVehicleByMakeModel(String zip) {
        clickMake();
        clickModel();
        clickPrice();
        setInputZIP(zip);
        clickSearchButtonByMakeModel();
        sleep(2000);
        return new CarsSearchPage();
    }

    public CarsSearchPage searchVehicleByPrice() {
        clickSearchByPrice();
        clickMinPrice();
        clickMaxPrice();
        clickSearchButtonByPrice();
        return new CarsSearchPage();
    }

    public CarsSearchPage searchVehicleByType() {
        clickSearchByType();
        clickType();
        clickSearchButtonByType();
        return new CarsSearchPage();
    }

    private void clickSearchButtonByType() {
        btnSearchByType.click();
    }

    private void clickType() {
        selectType.click();
    }

    private void clickSearchByType() {
        tabByType.click();
    }

    private void clickMinPrice() {
        selectMinPrice.click();
    }

    private void clickMaxPrice() {
        selectMaxPrice.click();
    }

    private void clickSearchButtonByPrice() {
        btnSearchByPrice.click();
    }

    public String usedCarsBlock(SelenideElement block) {
        return block.getText();
    }

    public void clickNewCars() {
        executeJavaScript("arguments[0].click()", btnNewCars);
    }

    private void clickSearchByDealer() {
        tabByDealer.click();
    }

    private void clickSearchByPrice() {
        tabByPrice.click();
    }

    private void clickSearchButtonByDealer() {
        executeJavaScript("arguments[0].click()", btnSearchByDealer);
    }

    public CarsDealersPage searchByDealer() {
        clickSearchByDealer();
        clickSearchButtonByDealer();
        return new CarsDealersPage();
    }

    protected void setInputZIP(String zip) {
        inputZIP.setValue(zip);
    }

    public String successSubscription() {
        return yourSubscription.getText();
    }

    public void clickClose() {
        btnClose.click();
    }

    public CarLoanPage clickCarLoan() {
        executeJavaScript("arguments[0].click()", getTheFacts);
        return new CarLoanPage();
    }

    public AdvancedSearchPage clickAdvancedSearch() {
        advancedSearch.click();
        return new AdvancedSearchPage();
    }

    private void clickVinTab() {
        tabVin.click();
    }

    private void setVin(String vin) {
        inputVin.setValue(vin);
    }

    private void clickGetStarted() {
        executeJavaScript("arguments[0].click()", btnGetStarted);
    }
}
