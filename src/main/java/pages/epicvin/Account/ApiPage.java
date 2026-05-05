package pages.epicvin.Account;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class ApiPage {

    public static SelenideElement titleH1 = $x("//h1[contains(@class,'profile__title')]");
    public static SelenideElement balanceBlock = $x("//div[@class='profile__balance']//p[1]");
    public static SelenideElement transactionsBlock = $x("//section[@id='payments']//h2");
    public static SelenideElement lastVinsBlock = $x("//section[@id='vins']//h2");
    private final SelenideElement langDropDown = $(By.id("langDropdownDesktop"));
    private final SelenideElement getPL = $x("//div[contains(@class,'lang-dropdown__menu show')]//ul[contains(@class,'menu-list')]//li[3]//a");


    public String apiBlock(SelenideElement block) {
        return block.getText();
    }

    public void changeLanguageOnPL() {
        langDropDown.click();
        getPL.click();
    }
}
