package interfaces;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import pages.epicvin.Precheck.PrecheckPage;

import java.time.Duration;

import static com.codeborne.selenide.Condition.clickable;
import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static interfaces.Dispute.disputeModal;

public interface SearchByVinPlate {

    SelenideElement licensePlate = $(By.id("plateNumberpromo"));
    SelenideElement stateCode = $(By.id("stateCode"));
    SelenideElement btnCheckPlate = $x("//button[contains(@class,'find-vin__plate-btn')]");
    SelenideElement btnCheckVin = $x("//div[@id='checkVinPanelpromo']//button[@data-type='sb']");
    SelenideElement inputVin = $(By.id("vin-inputpromo"));
    SelenideElement tabByPlate = $(By.id("checkPlateTabpromo"));
    SelenideElement tabByVin = $(By.id("checkVinTabpromo"));
    SelenideElement inputNavVin = $(By.id("vin-inputnav-page"));
    SelenideElement inputNavPlate = $(By.id("plateNumbernav-page"));
    SelenideElement inputNavState = $x("//aside[@class='page-nav']//select[@id='stateCode']");
    SelenideElement navBtnCheckPlate = $x("//aside[@class='page-nav']//button[contains(@class,'find-vin__plate-btn')]");

    default void setLicensePlate(String plate) {
        licensePlate.setValue(plate);
    }

    default void setStateCode() {
        stateCode.selectOptionByValue("fl");
    }

    default void clickCheckPlate() {
        btnCheckPlate.click();
    }

    default void clickTabByVin() {
        tabByVin.click();
    }

    default void setInputVin(String vin) {
        inputVin.setValue(vin);
    }

    default void clickTabByPlate() {
        sleep(2000);
        tabByPlate.click();
    }

    default void clickCheckVin() {
        sleep(2000);
        btnCheckVin.click();
    }

    default void setLicensePlateFromNavForm(String plate) {
        sleep(2000);
        inputNavPlate.setValue(plate);
    }

    default void setStateCodeFromNavForm() {
        inputNavState.selectOptionByValue("fl");
    }

    default void setVinFromNavForm(String vin) {
        inputNavVin.setValue(vin);
    }

    default void clickNavBtnCheckPlate() {
        navBtnCheckPlate.click();
    }

    default PrecheckPage searchLotByPlate(String plate) {
        clickTabByPlate();
        setLicensePlate(plate);
        setStateCode();
        clickCheckPlate();
        return new PrecheckPage();
    }

    default void searchByPlateWithoutState(String plate) {
        clickTabByPlate();
        setLicensePlate(plate);
        clickCheckPlate();
    }

    default void searchByStateWithoutPlate() {
        clickTabByPlate();
        setStateCode();
        clickCheckPlate();
    }

    default void validatePlate(String plate) {
        clickTabByPlate();
        setLicensePlate(plate);
        setStateCode();
        clickCheckPlate();
    }

    default PrecheckPage searchLotByVin(String vin) {
        clickTabByVin();
        setInputVin(vin);
        sleep(2000);
        if (!disputeModal.is(visible)) {
            System.out.println("Search button is visible - trying to click");
            try {
                btnCheckVin.shouldBe(enabled, Duration.ofSeconds(10))
                        .shouldBe(clickable, Duration.ofSeconds(10))
                        .click();
                System.out.println("Search button clicked");
            } catch (ElementNotFound | NoSuchElementException | StaleElementReferenceException e) {
                System.out.println("Search button disappeared during click - page already navigated to precheck");
            }
        }
        return new PrecheckPage();
    }

    default PrecheckPage searchLotByPlateFromNavForm(String plate) {
        setLicensePlateFromNavForm(plate);
        setStateCodeFromNavForm();
        clickNavBtnCheckPlate();
        return new PrecheckPage();
    }

    default PrecheckPage searchLotByVinFromNavForm(String vin) {
        setVinFromNavForm(vin);
        return new PrecheckPage();
    }
}
