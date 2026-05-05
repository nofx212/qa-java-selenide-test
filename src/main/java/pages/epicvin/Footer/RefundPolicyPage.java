package pages.epicvin.Footer;

import com.codeborne.selenide.SelenideElement;
import interfaces.Meta;
import interfaces.Refund;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class RefundPolicyPage implements Refund, Meta {

    public static SelenideElement block1 = $x("//div[@class='legal__section']//p[1]//b");
    public static SelenideElement block2 = $x("//div[@class='legal__section']//p[3]//b");
    public static SelenideElement block3 = $x("//div[@class='legal__section']//p[5]//b");
    public static SelenideElement block4 = $x("//div[@class='legal__section']//p[6]//b");
    public static SelenideElement block5 = $x("//div[@class='legal__section']//b[contains(text(),'4. Requesting a Refund')]");
    public static SelenideElement block6 = $x("//div[@class='legal__section']//b[contains(text(),'5. Additional Refund Terms')]");
    public static SelenideElement block7 = $x("//div[@class='legal__section']//b[contains(text(),'Final Notes')]");
    public static SelenideElement validationEmail = $x("//form[@id='refund-form']//div[1]//div[1]//p");
    public static SelenideElement validationVin = $x("//form[@id='refund-form']//div[1]//div[2]//p");
    public static SelenideElement validationReason = $x("//form[@id='refund-form']//div[1]//div[3]//p");
    public static SelenideElement validationName = $x("//form[@id='refund-form']//div[1]//div[4]//p");
    public static SelenideElement validationLastName = $x("//form[@id='refund-form']//div[1]//div[5]//p");
    public static SelenideElement validationDate = $x("//form[@id='refund-form']//div[1]//div[6]//p");
    public static SelenideElement validationPayment = $x("//form[@id='refund-form']//div[2]//div[1]//p");
    public static SelenideElement validationTransaction = $x("//form[@id='refund-form']//div[2]//div[2]//p");
    public static SelenideElement validationNote = $x("//form[@id='refund-form']//div[2]//div[3]//p");
    public static SelenideElement langDropDown = $(By.id("langDropdownDesktop"));


    public String refundBlock(SelenideElement block) {
        return block.getText();
    }
}
