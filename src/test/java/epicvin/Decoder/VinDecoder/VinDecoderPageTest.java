package epicvin.Decoder.VinDecoder;

import annotations.epicvin.EpicvinTest;
import org.junit.jupiter.api.Assertions;
import pages.epicvin.Main.MainPage;
import pages.epicvin.Precheck.PrecheckPage;
import pages.epicvin.Decoder.VinDecoder.VinDecoderPage;

import static com.codeborne.selenide.Selenide.title;
import static constants.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static pages.epicvin.Decoder.VinDecoder.VinDecoderPage.*;

public class VinDecoderPageTest extends base.BaseTest {

    @EpicvinTest
    public void searchLotByVin() {
        MainPage mainPage = new MainPage();
        VinDecoderPage vinDecoderPage = mainPage.clickVinDecoder();
        PrecheckPage precheckPage = vinDecoderPage.searchLotByVin(VALID_VIN);
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
    public void searchLotByLicensePlate() {
        MainPage mainPage = new MainPage();
        VinDecoderPage vinDecoderPage = mainPage.clickVinDecoder();
        PrecheckPage precheckPage = vinDecoderPage.searchLotByPlate(LICENSE_PLATE);
        if (precheckPage.greatText().contains("Get unlimited access to detailed reports")) {
            assertAll(
                    () -> assertTrue(precheckPage.getVin().contains("******")),
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
    public void checkContentOnVinDecoderPage() {
        MainPage mainPage = new MainPage();
        VinDecoderPage vinDecoderPage = mainPage.clickVinDecoder();
        assertAll(
                () -> assertEquals("Free VIN Decoder & Lookup – Car VIN Number Search", title()),
                () -> assertEquals("Run a free VIN Lookup to decode any car’s VIN number ⚡ reveal trim, engine, build sheet, recalls, and hidden liens in seconds—no credit card needed.", vinDecoderPage.getDescription()),
                () -> assertEquals("by Robert P Allred", vinDecoderPage.vinDecoderBlock(authorBlock)),
                () -> assertEquals("VIN Decoder & Lookup", vinDecoderPage.vinDecoderBlock(titleH1)),
                () -> assertEquals("EpicVIN’s Free VIN Decoder instantly interprets any car’s VIN to reveal key vehicle characteristics and history highlights at no cost", vinDecoderPage.vinDecoderBlock(subHeader)),
                () -> assertEquals("Why Choose EpicVIN's VIN Decoder?", vinDecoderPage.vinDecoderBlock(block1)),
                () -> assertEquals("How to Use EpicVIN's Free VIN Decoder", vinDecoderPage.vinDecoderBlock(block2)),
                () -> assertEquals("Select a Vehicle Make to Review", vinDecoderPage.vinDecoderBlock(block3)),
                () -> assertEquals("VIN Decoder Case Study: How Report Images Revealed Tampering", vinDecoderPage.vinDecoderBlock(videoBlock)),
                () -> assertEquals("What is a VIN (Vehicle Identification Number)?", vinDecoderPage.vinDecoderBlock(block4)),
                () -> assertEquals("How is a sequence structured?", vinDecoderPage.vinDecoderBlock(block5)),
                () -> assertEquals("Where Do I Find the VIN in My Car?", vinDecoderPage.vinDecoderBlock(block6)),
                () -> assertEquals("Why Decode a VIN Number?", vinDecoderPage.vinDecoderBlock(block7)),
                () -> assertEquals("Used Cars Can Hide Damage – Real EpicVIN Data", vinDecoderPage.vinDecoderBlock(block8)),
                () -> assertEquals("What Information Can You Obtain from a VIN Number?", vinDecoderPage.vinDecoderBlock(block9)),
                () -> assertEquals("VIN Decoder vs. Vehicle History Report", vinDecoderPage.vinDecoderBlock(block10)),
                () -> assertEquals("Useful Resources and Links", vinDecoderPage.vinDecoderBlock(block11)),
                () -> assertEquals("Further reading", vinDecoderPage.vinDecoderBlock(block12)),
                () -> assertEquals("Frequently Asked Questions", vinDecoderPage.vinDecoderBlock(faq)),
                () -> assertEquals("Similar Articles", vinDecoderPage.vinDecoderBlock(similarArticles)),
                () -> assertTrue(vinDecoderPage.vinDecoderBlock(ratingWidget).contains("98% of users"))
        );
    }

    @EpicvinTest
    public void checkAuthorPageFromVinDecoderPage() {
        MainPage mainPage = new MainPage();
        VinDecoderPage vinDecoderPage = mainPage.clickVinDecoder();
        if (vinDecoderPage.vinDecoderBlock(authorBlock).contains("by Robert P Allred")) {
            vinDecoderPage.clickAuthorPageFromVinDecoder();
            Assertions.assertEquals("Robert P. Allred", vinDecoderPage.getBlogBlocks(authorsPageTitle));
        } else if (vinDecoderPage.vinDecoderBlock(authorBlock).contains("John C. Baldwin")) {
            vinDecoderPage.clickAuthorPageFromVinDecoder();
            Assertions.assertEquals("John C. Baldwin", vinDecoderPage.getBlogBlocks(authorsPageTitle));
        } else {
            System.out.println("UNKNOWN AUTHOR");
        }
    }
}
