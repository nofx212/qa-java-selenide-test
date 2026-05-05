package pages.epicvin.Footer;

import com.codeborne.selenide.SelenideElement;
import interfaces.Meta;

import static com.codeborne.selenide.Selenide.$x;

public class FloodDamageCheckPage implements Meta {

  public static SelenideElement titleH1 = $x("//h1[@class='heading heading--l']");
  public static SelenideElement block1 = $x("//section[@id='section1']//h2");
  public static SelenideElement block2 = $x("//section[@id='section2']//h2");
  public static SelenideElement block3 = $x("//section[@id='section3']//h2");
  public static SelenideElement block4 = $x("//section[@id='section4']//h2");


  public String floodDamageBlock(SelenideElement block) {
    return block.getText();
  }
}
