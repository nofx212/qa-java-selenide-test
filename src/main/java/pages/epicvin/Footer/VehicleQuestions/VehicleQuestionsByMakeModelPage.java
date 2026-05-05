package pages.epicvin.Footer.VehicleQuestions;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public class VehicleQuestionsByMakeModelPage {

    public static SelenideElement titleH1 = $x("//div[@class='container holder']//h1");
    public static SelenideElement block1 = $(By.id("browseQuestionsLabel"));
    public static SelenideElement block2 = $(By.id("makeForSaleLabel"));
    private final SelenideElement year = $x("//ul[@class='questions__makes-sub-list']//li//a");



    public String vehicleQuestionsByMakeModelBlock(SelenideElement block) {
        return block.getText();
    }

    public VehicleQuestionsByMakeModelYearPage selectYear() {
        executeJavaScript("arguments[0].click()", year);
        return new VehicleQuestionsByMakeModelYearPage();
    }
}
