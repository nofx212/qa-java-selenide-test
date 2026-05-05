package pages.epicvin.Cars;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public class AdvancedSearchPage {

    public static SelenideElement title = $x("//h1[@class='header-text fw-900 lh-12 mt-1 mb-0 mb-lg-4 c-000']");
    public static SelenideElement block1 = $x("//h2[contains(text(),'Top Cities')]");
    public static SelenideElement block2 = $x("//h2[contains(text(),'Body Styles')]");
    public static SelenideElement block3 = $x("//h2[contains(text(),'Makes')]");
    public static SelenideElement block4 = $x("//h2[contains(text(),'Popular Used Cars for Sale')]");
    public static SelenideElement block5 = $x("//h2[contains(text(),'Used Cars by Price')]");
    public static SelenideElement block6 = $(By.id("popular-control"));
    private final SelenideElement makeAudi = $x("//div[@class='col-lg search__col pt-2']//div[4]//div[3]//a");
    private final SelenideElement filter = $x("//div[@class='search-head-filter-items d-flex flex-wrap align-items-center']//div[1]");
    private final SelenideElement bodyStyleSedan = $x("//div[@id='model-slider']//div[2]//a//p");

    public String advancedSearchBlock(SelenideElement block) {
        return block.getText();
    }

    private String getMakeAudi(){
        return makeAudi.getText();
    }

    private void clickMakeAudi(){
        makeAudi.click();
    }

    public String searchMakeAudi(){
        sleep(2000);
        var audi = getMakeAudi();
        clickMakeAudi();
        return audi;
    }

    public String filterOnSearchPage(){
        sleep(2000);
        return filter.getText();
    }

    private String getBodyStyleSedan(){
        return bodyStyleSedan.getText();
    }

    private void clickBodyStyleSedan(){
        bodyStyleSedan.click();
    }

    public String searchBodyStyleSedan(){
        var sedan = getBodyStyleSedan();
        clickBodyStyleSedan();
        return sedan;
    }
}
