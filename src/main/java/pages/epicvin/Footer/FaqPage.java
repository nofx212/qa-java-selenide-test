package pages.epicvin.Footer;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class FaqPage {

    private final SelenideElement titleH1 = $x("//h1[contains(@class,'faq-main__title')]");

    public String getTitle() {
        return titleH1.getText();
    }
}
