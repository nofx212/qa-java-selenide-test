package pages.epicvin.Footer.VehicleQuestions;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class VehicleQuestionsByMakeModelYearPage {

    public static SelenideElement titleH1 = $x("//div[@class='container holder']//h1");
    public static SelenideElement block1 = $(By.id("makeForSaleLabel"));

    public String vehicleQuestionsByMakeModelYearBlock(SelenideElement block) {
        return block.getText();
    }
}
