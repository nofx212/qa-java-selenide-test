package interfaces;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public interface Sample {

    SelenideElement titleOwnershipHistory = $x("//section[@id='ownership']//h2");
    SelenideElement titleVehicleSpecifications = $x("//section[@id='specs']//h2");
    SelenideElement titleAllHistoryEvents = $x("//section[@id='historyEvents']//h2");
    SelenideElement titleRecallCheck = $x("//section[@id='recalls']//h2");
    SelenideElement titleJunkSalvage = $x("//section[@id='salvage']//h2");
    SelenideElement titleOdometerCheck = $x("//section[@id='odometer']//h2");
    SelenideElement titleHistoryInformation = $x("//section[@id='titles']//h2");
    SelenideElement titleStolenCheck = $x("//section[@id='stolen']//h2");
    SelenideElement titleBrandMajorCheck = $x("//section[@id='brand-major']//h2");
    SelenideElement titleBrandOtherCheck = $x("//section[@id='brand-other']//h2");
    SelenideElement titleVehicleDamages = $x("//section[@id='damages']//h2");
    SelenideElement titleSalesHistory = $x("//section[@id='sales']//h2");
    SelenideElement titleMarketPrice = $x("//section[@id='market']//h2");

    default String reportBlock(SelenideElement block) {
        return block.getText();
    }
}
