package pages.epicvin.Account;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class SavedSearchesPage {

    private final SelenideElement btnDelete =
            $x("//button[@class='btn-reset']");
    private final SelenideElement titleText = $x("//h2[contains(@class,'profile__subtitle')]");

    private void clickDelete() {
        btnDelete.shouldBe(Condition.visible);
        executeJavaScript("arguments[0].click()", btnDelete);
        sleep(2000);
    }

    public void getDeleteAll() {
        var deleteAllSaveSearches = $$x("//button[@class='btn-reset']");
        while (deleteAllSaveSearches.size() > 0) {
            System.out.println(deleteAllSaveSearches.size());
            clickDelete();
            sleep(1000);
            deleteAllSaveSearches = $$x("//button[@class='btn-reset']");
            System.out.println(deleteAllSaveSearches.size());
        }
    }

    public String searchPanelDisplayed() {
        return titleText.getText();
    }
}
