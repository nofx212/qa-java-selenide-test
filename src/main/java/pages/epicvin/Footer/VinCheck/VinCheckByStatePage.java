package pages.epicvin.Footer.VinCheck;

import com.codeborne.selenide.SelenideElement;
import interfaces.Meta;

import static com.codeborne.selenide.Selenide.*;

public class VinCheckByStatePage implements Meta {

  public static SelenideElement titleH1 = $x("//h1[contains(@class,'heading')]");
  public static SelenideElement block1 = $x("//div[@class='reg-content__main']//section[1]//h2");
  public static SelenideElement block2 = $x("//div[@class='reg-content__main']//section[2]//h2");
  public static SelenideElement block3 = $x("//div[@class='reg-content__main']//section[3]//h2");
  public static SelenideElement block4 = $x("//div[@class='reg-content__main']//section[4]//h2");
  public static SelenideElement block5 = $x("//div[@class='reg-content__main']//section[5]//h2");
  public static SelenideElement block6 = $x("//h3[contains(text(),'Other Resources')]");
  public static SelenideElement block7 = $x("//div[@class='reg-content__main']//section[6]//h2");

  public String vinCheckByStateBlock(SelenideElement block) {
    return block.getText();
  }
}
