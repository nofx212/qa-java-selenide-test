package pages.epicvin.Footer;

import com.codeborne.selenide.SelenideElement;
import interfaces.Meta;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.currentFrameUrl;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class BlogPage implements Meta {

    public static SelenideElement title = $x("//h1[contains(@class,'blog__title')]");
    public static SelenideElement subTitle = $x("//p[@class='blog__desc']");
    public static SelenideElement popularArticles = $x("//div[@class='blog__aside']//div[contains(@class,'popular-articles__title')]");
    public static SelenideElement findVin = $x("//div[@class='blog__aside']//section[@class='side-find']//div[1]");
    private final SelenideElement page2 = $x("//ul[@class='pagination__list']//li[2]//a");
    public static SelenideElement btnPrev = $x("//a[contains(@class,'pagination__btn--back')]");
    private final SelenideElement subscribeBlock = $x("//section[@class='blog-widget']//div");
    private final SelenideElement inputEmail = $(By.id("blogSubEmail"));
    private final SelenideElement btnSubscribe = $x("//button[contains(@class,'sub-form__button')]");
    public static SelenideElement successSubText = $x("//p[contains(@class,'success-msg show')]");
    private final SelenideElement firstArticle = $x("//article[@class='blog__main-article article']//div//h2");
    public static SelenideElement moreArticlesBlock = $x("//div[contains(@class,'blog-more__title')]");
    public static SelenideElement authorsName = $x("//div[contains(@class,'blog__authors-name')]//a");
    public static SelenideElement authorsPageTitle = $x("//div[@class='blog__head-author']//h1");


    public String getBlogBlocks(SelenideElement blog) {
        return blog.getText();
    }

    public void clickPage2() {
        sleep(1000);
        executeJavaScript("arguments[0].click()", page2);
    }

    public String getUrl() {
        getWebDriver().getCurrentUrl();
        return currentFrameUrl();
    }

    public void subscribe(String email) {
        executeJavaScript(("arguments[0].scrollIntoView();"), subscribeBlock);
        if (subscribeBlock.getText().contains("Subscribe to our newsletter")) {
            inputEmail.setValue(email);
            executeJavaScript("arguments[0].click()", btnSubscribe);
        }
    }

    public void clickFirstArticle() {
        firstArticle.click();
    }

    public void clickAuthorsPage() {
        executeJavaScript("arguments[0].click()", authorsName);
    }
}
