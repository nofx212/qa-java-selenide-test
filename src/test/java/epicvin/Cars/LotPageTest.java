package epicvin.Cars;

import annotations.epicvin.EpicvinTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import pages.epicvin.Cars.CarsPage;
import pages.epicvin.Cars.CarsSearchPage;
import pages.epicvin.Cars.LotPage;
import pages.epicvin.Authentication.LoginPage;
import pages.epicvin.Main.MainPage;

import static constants.Constants.*;
@Disabled("Removed Cars Block")
public class LotPageTest extends base.BaseTest {

    @EpicvinTest
    public void sendContactDealerMessage() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();
        loginPage.login(VALID_EMAIL, VALID_PASSWORD);
        CarsPage carsPage = mainPage.clickCarsForSale();
        CarsSearchPage carsSearchPage = carsPage.searchVehicleByMakeModel(ZIP);
        LotPage lotPage = carsSearchPage.clickOnLot();
        lotPage.sendContactDealerMessage();
        Assertions.assertTrue(lotPage.successDealerContactMessage().contains("Message sent to the seller"));
    }

    @Disabled("Wait response from CarsDirect")
    @EpicvinTest
    public void sendLeadDealerMessage() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();
        loginPage.login(VALID_EMAIL1, VALID_PASSWORD);
        CarsPage carsPage = mainPage.clickCarsForSale();
        CarsSearchPage carsSearchPage = carsPage.clickSearch();
        LotPage lotPage = carsSearchPage.getFirstLot();
        lotPage.sendDealerLeadMessage(AMOUNT_PRICE);
        Assertions.assertEquals("Thank You! Your message was sent.", lotPage.successDealerLeadMessage());
    }
}
