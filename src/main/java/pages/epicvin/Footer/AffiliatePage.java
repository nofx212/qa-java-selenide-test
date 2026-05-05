package pages.epicvin.Footer;

import com.codeborne.selenide.SelenideElement;
import interfaces.Meta;
import interfaces.Register;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.TypeOptions.text;

public class AffiliatePage implements Meta, Register {

    public static SelenideElement h1Title = $x("//div[@class='main-head-text']//h1");
    public static SelenideElement block1 = $x("//section[@id='get-started']//h2");
    public static SelenideElement block2 = $x("//section[@id='get-paid']//h2");
    public static SelenideElement block3 = $x("//section[@id='type-partnership']//h2");
    public static SelenideElement block4 = $x("//section[@id='special-offer']//h2");
    public static SelenideElement block5 = $x("//section[@id='contacts']//h2");
    private final SelenideElement inputEmail = $(By.id("email"));
    private final SelenideElement inputWebsite = $(By.id("link"));
    private final SelenideElement btnJoin = $(By.id("save-aff"));
    public static SelenideElement successRegister = $x("//p[@id='m-txt']");
    private final SelenideElement loginFromModal = $x("//p[@id='m-txt']//a");
    private final SelenideElement loginFromPage = $x("//ul[@id='top-menu-scroll']//li[5]//a");
    private final SelenideElement userName = $x("//input[@name='username']");
    private final SelenideElement userPassword = $x("//input[@name='password']");
    private final SelenideElement btnLogin = $x("//div[@class='Button pap-btn']//span");
    public static SelenideElement loggedUser = $x("//div[@class='logged-user']");
    private final SelenideElement merchantTab = $x("//ul[@class='nav login-tabs']//li[2]//a");


    public String affiliateBlock(SelenideElement block) {
        return block.getText();
    }

    @Override
    public String registration(String website) {
        var email = (String.format("test.affiliate+%s@gmail.com", Register.getUniqueId()));
        inputEmail.setValue(email);
        inputWebsite.setValue(website);
        executeJavaScript("arguments[0].click()", btnJoin);
        sleep(5000);
        return email;
    }

    public void login (String name, String password) {
        loginFromModal.click();
        switchTo().window(1);
        userName.setValue(name);
        userPassword.setValue(password);
        btnLogin.click();
        sleep(5000);
    }

    public void merchantLogin(String name, String password) {
        loginFromPage.click();
        switchTo().window(1);
        merchantTab.click();
        userName.setValue(name);
        userPassword.type(text(password).sensitive());
        btnLogin.click();
        sleep(5000);
    }
}
