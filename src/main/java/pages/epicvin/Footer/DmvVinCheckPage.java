package pages.epicvin.Footer;

import com.codeborne.selenide.SelenideElement;
import interfaces.Meta;
import interfaces.SearchByVinPlate;

import static com.codeborne.selenide.Selenide.$x;

public class DmvVinCheckPage implements SearchByVinPlate, Meta {

  public static SelenideElement title = $x("//h1[contains(text(),'DMV VIN Check')]");
  public static SelenideElement block1 = $x("//section[@id='section1']//h2");
  public static SelenideElement block2 = $x("//section[@id='section2']//h2");
  public static SelenideElement block3 = $x("//section[@id='section3']//h2");
  public static SelenideElement block4 = $x("//section[@id='section4']//h2");

  public String dmvVinCheckBlock(SelenideElement block) {
    return block.getText();
  }
}
