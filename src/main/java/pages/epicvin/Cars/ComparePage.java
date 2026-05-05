package pages.epicvin.Cars;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class ComparePage {

    private final SelenideElement textCompare = $x("//div[@class='compare-car__top']//h2");

    public String textCompareVehicles() {
        return textCompare.getText();
    }
}
