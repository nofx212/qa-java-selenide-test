package pages.epicvin.Cars;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import pages.epicvin.Precheck.PrecheckPage;

import static com.codeborne.selenide.Selenide.*;

public class LotPage {
    private final SelenideElement btnSeeFullReport = $x("//button[@name='vin']");
    private final SelenideElement btnSendMessage = $x("//button[contains(@class,'btn contact-dealer')]");
    private final SelenideElement successDealerContact = $x("//div[@class='popup__message']");
    private final SelenideElement successDealerLead = $x("//div[@id='cars-modal']//div[@class='modal-content']//div[2]");
    private final SelenideElement checkbox1 = $(By.id("carAvailable"));
    private final SelenideElement checkbox2 = $(By.id("carWilling"));
    private final SelenideElement amount = $x("//input[@name='amount']");
    private final SelenideElement checkbox3 = $(By.id("carWarranty"));
    private final SelenideElement checkbox4 = $(By.id("carCredit"));
    private final SelenideElement checkbox5 = $(By.id("carTrading"));
    private final SelenideElement btnSendToDealer = $(By.id("lead-btn"));


    public PrecheckPage clickSeeFullReportButton() {
        executeJavaScript("arguments[0].click()", btnSeeFullReport);
        return new PrecheckPage();
    }

    public void sendContactDealerMessage() {
        btnSendMessage.click();
        sleep(3000);
    }

    public void sendDealerLeadMessage(String price) {
        btnSendMessage.click();
        sleep(2000);
        if (successDealerContactMessage().contains("Message sent to the seller")) {
            executeJavaScript("arguments[0].click()", checkbox1);
            executeJavaScript("arguments[0].click()", checkbox2);
            amount.setValue(price);
            executeJavaScript("arguments[0].click()", checkbox3);
            executeJavaScript("arguments[0].click()", checkbox4);
            executeJavaScript("arguments[0].click()", checkbox5);
            sleep(1000);
            btnSendToDealer.click();
            sleep(2000);
        }
    }

    public String successDealerContactMessage() {
        return successDealerContact.getText();
    }

    public String successDealerLeadMessage() {
        return successDealerLead.getText();
    }
}
