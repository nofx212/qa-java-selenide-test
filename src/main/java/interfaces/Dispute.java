package interfaces;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.sleep;

public interface Dispute {

    SelenideElement disputeModal = $x("//div[@id='dispute-modal']//h2");
    SelenideElement disputeModalText = $x("//div[@class='modal-body']//p");


    default String getModalDispute() {
        sleep(2000);
        return disputeModal.getText();
    }

    default String getModalDisputeText() {
        return disputeModalText.getText();
    }
}
