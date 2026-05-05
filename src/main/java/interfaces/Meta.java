package interfaces;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public interface Meta {

    SelenideElement description = $x("//meta[@name='description']");

    default String getDescription() {
        return description.getAttribute("content");
    }
}
