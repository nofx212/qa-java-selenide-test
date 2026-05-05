package pages.epicvin.Footer.VehicleQuestions;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public class VehicleQuestionsByMakePage {

    public static SelenideElement titleH1 = $x("//div[@class='container holder']//h1");
    public static SelenideElement block1 = $(By.id("browseQuestionsLabel"));
    public static SelenideElement block2 = $(By.id("makeForSaleLabel"));
    private final SelenideElement model = $x("//ul[@class='questions__makes']//li//a");

    public String vehicleQuestionsByMakeBlock(SelenideElement block) {
        return block.getText();
    }

    public VehicleQuestionsByMakeModelPage selectModel() {
        executeJavaScript("arguments[0].click()", model);
        return new VehicleQuestionsByMakeModelPage();
    }
}
