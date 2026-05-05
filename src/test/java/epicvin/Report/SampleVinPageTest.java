package epicvin.Report;

import annotations.epicvin.EpicvinTest;
import org.junit.jupiter.api.Assertions;
import pages.epicvin.Main.MainPage;
import pages.epicvin.Precheck.PrecheckPage;
import pages.epicvin.Report.SampleVinPage;

import static constants.Constants.VALID_VIN;
import static interfaces.Sample.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SampleVinPageTest extends base.BaseTest {

    @EpicvinTest
    public void sampleVin() {
        MainPage mainPage = new MainPage();
        SampleVinPage sampleVinPage = mainPage.clickSampleVinButton();
        assertAll(
                () -> assertEquals("SAMPLE VEHICLE HISTORY REPORT", sampleVinPage.epicVinTitleText()),
                () -> assertEquals("Ownership history", sampleVinPage.reportBlock(titleOwnershipHistory)),
                () -> assertEquals("Vehicle specifications", sampleVinPage.reportBlock(titleVehicleSpecifications)),
                () -> assertEquals("All history events", sampleVinPage.reportBlock(titleAllHistoryEvents)),
                () -> assertEquals("Safety recall check", sampleVinPage.reportBlock(titleRecallCheck)),
                () -> assertEquals("Stolen vehicle check", sampleVinPage.reportBlock(titleStolenCheck)),
                () -> assertEquals("Junk / Salvage / Insurance records", sampleVinPage.reportBlock(titleJunkSalvage)),
                () -> assertEquals("Odometer check", sampleVinPage.getTitleOdometerCheck()),
                () -> assertEquals("Title history information", sampleVinPage.reportBlock(titleHistoryInformation)),
                () -> assertEquals("Major title brand check", sampleVinPage.reportBlock(titleBrandMajorCheck)),
                () -> assertEquals("Other title brand check", sampleVinPage.reportBlock(titleBrandOtherCheck)),
                () -> assertEquals("Vehicle damages", sampleVinPage.reportBlock(titleVehicleDamages)),
                () -> assertEquals("Sales history", sampleVinPage.reportBlock(titleSalesHistory)),
                () -> assertEquals("Market price analysis", sampleVinPage.reportBlock(titleMarketPrice))
        );
    }

    @EpicvinTest
    public void searchLotByVinFromNavigationForm() {
        MainPage mainPage = new MainPage();
        SampleVinPage sampleVinPage = mainPage.clickSampleVinButton();
        PrecheckPage precheckPage = sampleVinPage.searchLotByVinFromNavForm(VALID_VIN);
        if (precheckPage.greatText().contains("Get unlimited access to detailed reports")) {
            assertAll(
                    () -> assertTrue(precheckPage.textVehicleEngine().contains("Engine")),
                    () -> assertTrue(precheckPage.textLastOdometer().contains("Last mileage")),
                    () -> assertTrue(precheckPage.textLastSellingPrice().contains("Last price")),
                    () -> assertTrue(precheckPage.textCountry().contains("Country"))
            );
        } else if (precheckPage.precheckPageAll().contains("So sorry!")) {
            String sorryText = precheckPage.sorryText();
            Assertions.assertTrue(sorryText.contains("So sorry!"));
        } else {
            Assertions.fail("Test failed");
        }
    }
}
