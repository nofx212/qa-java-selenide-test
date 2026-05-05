package pages.epicvin.Footer.VehicleQuestions;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public class VehicleQuestionsPage {

    public static SelenideElement titleH1 = $x("//div[@class='container holder']//h1");
    public static SelenideElement subTitle = $x("//p[@class='promo__text']");
    public static SelenideElement block1 = $(By.id("browseQuestionsLabel"));
    private final SelenideElement searchInput = $(By.id("searchQuestion"));
    private final SelenideElement btnSearch = $x("//div[@class='questions__search-wrap']//button");
    public static SelenideElement successResult = $x("//section[@class='questions__section']//div");
    public static SelenideElement noResult = $x("//section[@class='questions__section']");
    private final SelenideElement make = $(By.partialLinkText("Audi"));
    private final SelenideElement firstAnswer = $x("//ul[@class='questions__list']//li[1]//h3//a");
    public static SelenideElement answerBlock = $(By.id("yourAnswerLabel"));
    private final SelenideElement inputName = $(By.id("answerName"));
    private final SelenideElement inputAnswer = $(By.id("answerText"));
    private final SelenideElement btnSubmit = $x("//form[@class='answer-form']//button");
    public static SelenideElement successAnswer = $(By.id("answerSuccessLabel"));



    public String vehicleQuestionsBlock(SelenideElement block) {
        return block.getText();
    }

    public void searchQuestion(String question) {
        searchInput.setValue(question);
        btnSearch.click();
    }

    public VehicleQuestionsByMakePage selectMake() {
        executeJavaScript("arguments[0].click()", make);
        return new VehicleQuestionsByMakePage();
    }

    public String getFirstAnswer() {
        var answer = firstAnswer.getText();
        executeJavaScript("arguments[0].click()", firstAnswer);
        return answer;
    }


    public void submitAnswer(String name, String answer) {
        inputName.setValue(name);
        inputAnswer.setValue(answer);
        executeJavaScript("arguments[0].click()", btnSubmit);
        sleep(1000);
    }
}
