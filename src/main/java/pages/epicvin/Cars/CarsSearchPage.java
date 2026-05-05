package pages.epicvin.Cars;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;
import static pages.epicvin.Cars.CarsDealersPage.filterMake;

public class CarsSearchPage {

    private static final SelenideElement bannerCookies = $x("//div[@class='cookie-card']");
    private final SelenideElement closeCookies = $x("//div[@class='cookie-card']//button");
    private final SelenideElement getFirstLotOnPage = $x("//div[@id='search-body-lots']/div[1]/div/div[4]/div/div/input");
    private final SelenideElement getSecondLotOnPage = $x("//div[@id='search-body-lots']/div[2]/div/div[4]/div/div/input");
    private final SelenideElement getThreadLotOnPage = $x("//div[@id='search-body-lots']/div[3]/div/div[4]/div/div/input");
    private final SelenideElement btnCompare = $x("//button[@class='btn compare__btn']");
    private final SelenideElement btnCompareInPanel = $x("//a[@class='btn compare__btn-panel']");
    private final SelenideElement getLot = $x("//div[@id='search-body-lots']//div[1]//a[contains(text(),'Check Details')]");
    private final SelenideElement saveSearch = $(By.id("dropdownNotifiButton"));
    private final SelenideElement searchName = $(By.id("search-name"));
    private final SelenideElement modalBtnSearch = $(By.id("save-search-btn"));
    private final SelenideElement title = $x("//h1[@class='cars--title']");
    private final SelenideElement filter = $x("//div[@id='search-content-head']//div//div//span");


    private void clickCloseCookies() {
        closeCookies.click();
    }

    private void clickFirsLot() {
        executeJavaScript("arguments[0].click()", getFirstLotOnPage);
    }

    private void clickSecondLot() {
        if (getSecondLotOnPage.is(Condition.not(Condition.visible))) {
            System.out.println("lot is not visible");
            executeJavaScript("scroll(0,1000)");
            executeJavaScript("arguments[0].click()", getThreadLotOnPage);
        } else {
            executeJavaScript("arguments[0].click()", getSecondLotOnPage);
            System.out.println("lot is visible");
        }
    }

    private void clickCompare() {
        btnCompare.click();
    }

    private void clickCompareInPanel() {
        btnCompareInPanel.click();
    }


    public ComparePage compareLots() {
        if (bannerCookies.isDisplayed()) {
            clickCloseCookies();
        }
        executeJavaScript("scroll(0, 500)");
        clickFirsLot();
        clickSecondLot();
        clickCompare();
        clickCompareInPanel();
        return new ComparePage();
    }

    public LotPage clickOnLot() {
        sleep(2000);
        if (filterMake.shouldBe(Condition.visible).getText().equals("Audi")) {
            System.out.println("filter is visible");
            executeJavaScript("arguments[0].click()", getLot);
        }
        return new LotPage();
    }

    public LotPage getFirstLot() {
        executeJavaScript("arguments[0].click()", getLot);
        return new LotPage();
    }

    public void saveSearch(String test) {
        clickSaveSearch();
        setSearchName(test);
        clickModalBtnSearch();
    }

    private void clickSaveSearch() {
        sleep(2000);
        saveSearch.click();
    }

    private void setSearchName(String test) {
        searchName.setValue(test);
    }

    private void clickModalBtnSearch() {
        modalBtnSearch.click();
    }

    public String getTitle() {
        return title.getText();
    }

    public String getFilter() {
        sleep(2000);
        return filter.getText();
    }
}
