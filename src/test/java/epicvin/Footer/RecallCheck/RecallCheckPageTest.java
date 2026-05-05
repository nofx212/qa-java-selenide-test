package epicvin.Footer.RecallCheck;

import annotations.epicvin.EpicvinTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import pages.epicvin.Main.MainPage;
import pages.epicvin.Footer.RecallCheck.RecallCheckPage;
import pages.epicvin.Precheck.PrecheckPage;

import static com.codeborne.selenide.Selenide.title;
import static constants.Constants.LICENSE_PLATE;
import static constants.Constants.VALID_VIN;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pages.epicvin.Footer.RecallCheck.RecallCheckByMakeModelPage.similarArticles;
import static pages.epicvin.Footer.RecallCheck.RecallCheckPage.*;

@Disabled("Remove in ES-3438")
public class RecallCheckPageTest extends base.BaseTest {

    @EpicvinTest
    public void checkContentOnRecallPage() {
        MainPage mainPage = new MainPage();
        RecallCheckPage recallCheckPage = mainPage.clickRecallCheck();
        assertAll(
                () -> assertEquals("Car Recall Check By VIN - Vehicle Recalls Lookup | EpicVin", title()),
                () -> assertEquals("Check vehicle recalls easily! Use our tool to find safety notices for cars, trucks, or motorcycles by VIN or model. Ensure your road safety today.", recallCheckPage.getDescription()),
                () -> assertEquals("Vehicle Recalls Check & Lookup", recallCheckPage.recallCheckBlock(titleH1)),
                () -> assertEquals("Gain complete insight into your car's recall status and history. Discover whether your vehicle has any existing or outstanding safety recalls.",
                        recallCheckPage.recallCheckBlock(subTitle)),
                () -> assertEquals("What is a Car Recall?", recallCheckPage.recallCheckBlock(block1)),
                () -> assertEquals("How Does It Work?", recallCheckPage.recallCheckBlock(block2)),
                () -> assertEquals("NHTSA Safety Recall Results", recallCheckPage.recallCheckBlock(block3)),
                () -> assertEquals("Where is the VIN Number on a Car?", recallCheckPage.recallCheckBlock(block4)),
                () -> assertEquals("What Does the Vehicle Recall Report Contain?", recallCheckPage.recallCheckBlock(block5)),
                () -> assertEquals("Get Recall Alerts", recallCheckPage.recallCheckBlock(block6)),
                () -> assertEquals("Frequently Asked Questions", recallCheckPage.recallCheckBlock(block7)),
                () -> assertEquals("Find Recalls by Make", recallCheckPage.recallCheckBlock(block8)),
                () -> assertEquals("Similar Articles", recallCheckPage.recallCheckBlock(similarArticles))
        );
    }

    @EpicvinTest
    public void checkRecallByVin() {
        MainPage mainPage = new MainPage();
        RecallCheckPage recallCheckPage = mainPage.clickRecallCheck();
        PrecheckPage precheckPage = recallCheckPage.checkRecallByVin(VALID_VIN);
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

    @EpicvinTest
    public void checkRecallByLicensePlate() {
        MainPage mainPage = new MainPage();
        RecallCheckPage recallCheckPage = mainPage.clickRecallCheck();
        PrecheckPage precheckPage = recallCheckPage.checkRecallByLicensePlate(LICENSE_PLATE);
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

    @EpicvinTest
    public void checkRecallListContent() {
        MainPage mainPage = new MainPage();
        RecallCheckPage recallCheckPage = mainPage.clickRecallCheck();
        assertAll(
                () -> assertEquals("Recall #:", recallCheckPage.recallCheckBlock(recallNumber)),
                () -> assertEquals("Recall Date:", recallCheckPage.recallCheckBlock(recallDate)),
                () -> assertEquals("Component(s):", recallCheckPage.recallCheckBlock(components))
        );
    }
}


