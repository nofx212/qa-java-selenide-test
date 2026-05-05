package epicvin.Cars;

import annotations.epicvin.EpicvinTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import pages.epicvin.Cars.AdvancedSearchPage;
import pages.epicvin.Cars.CarsPage;
import pages.epicvin.Main.MainPage;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static pages.epicvin.Cars.AdvancedSearchPage.*;
@Disabled("Removed Cars Block")
public class AdvancedSearchPageTest extends base.BaseTest {

    @EpicvinTest
    public void checkContentOnAdvancedSearchPage() {
        MainPage mainPage = new MainPage();
        CarsPage carsPage = mainPage.clickCarsForSale();
        AdvancedSearchPage advancedSearchPage = carsPage.clickAdvancedSearch();
        assertAll(
                () -> assertEquals("Used Cars Finder", advancedSearchPage.advancedSearchBlock(title)),
                () -> assertEquals("Top Cities Show All", advancedSearchPage.advancedSearchBlock(block1)),
                () -> assertEquals("Body Styles", advancedSearchPage.advancedSearchBlock(block2)),
                () -> assertEquals("Makes Show All", advancedSearchPage.advancedSearchBlock(block3)),
                () -> assertEquals("Popular Used Cars for Sale Show All", advancedSearchPage.advancedSearchBlock(block4)),
                () -> assertEquals("Used Cars by Price", advancedSearchPage.advancedSearchBlock(block5)),
                () -> assertEquals("Popular Vehicles", advancedSearchPage.advancedSearchBlock(block6))
        );
    }

    @EpicvinTest
    public void checkMakeFromAdvancedSearchPage() {
        MainPage mainPage = new MainPage();
        CarsPage carsPage = mainPage.clickCarsForSale();
        AdvancedSearchPage advancedSearchPage = carsPage.clickAdvancedSearch();
        var searchAudiCheck = advancedSearchPage.searchMakeAudi();
        var filterAudiCheck = advancedSearchPage.filterOnSearchPage();
        Assertions.assertEquals(searchAudiCheck, filterAudiCheck);
    }

    @EpicvinTest
    public void checkBodyStyleFromAdvancedSearchPage() {
        MainPage mainPage = new MainPage();
        CarsPage carsPage = mainPage.clickCarsForSale();
        AdvancedSearchPage advancedSearchPage = carsPage.clickAdvancedSearch();
        var sedanBodyStyle = advancedSearchPage.searchBodyStyleSedan();
        var filterSedan = advancedSearchPage.filterOnSearchPage();
        Assertions.assertEquals(sedanBodyStyle, filterSedan);
    }
}
