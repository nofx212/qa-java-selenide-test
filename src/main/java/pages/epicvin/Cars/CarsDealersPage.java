package pages.epicvin.Cars;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.sleep;

public class CarsDealersPage {
    private final SelenideElement findDealerText = $x("//div[@id='dealers-list-cont']//h1");
    private final SelenideElement selectMake = $x("//div[contains(@class,'p-0')]//select");
    private final SelenideElement inputZip = $x("//input[@name='zip']");
    private final SelenideElement selectDistance = $x("//div[contains(@class,'p-0 mb-0')]//select");
    private final SelenideElement btnApply = $x("//div[@class='form-apply-btn']");
    public static SelenideElement filterMake = $x("//div[@id='search-content-head']//div//div[1]");
    public static SelenideElement filterZip = $x("//div[@id='search-content-head']//div//div[2]");
    public static SelenideElement filterDistance = $x("//div[@id='search-content-head']//div//div[3]");

    public String titleOnDealerPage() {
        return findDealerText.getText();
    }

    private void clickMake() {
        selectMake.selectOptionByValue("audi");
    }

    public void searchLotByDealer(String zip) {
        clickMake();
        setInputZip(zip);
        clickDistance();
        clickApplyAndShow();
        sleep(2000);
    }

    private void setInputZip(String zip) {
        inputZip.setValue(zip);
    }

    private void clickDistance() {
        selectDistance.selectOptionByValue("100");
    }

    private void clickApplyAndShow() {
        btnApply.click();
    }

    public String carsDealersFilter(SelenideElement filter) {
        return filter.getText();
    }
}
