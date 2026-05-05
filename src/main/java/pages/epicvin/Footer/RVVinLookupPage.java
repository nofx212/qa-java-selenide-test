package pages.epicvin.Footer;

import com.codeborne.selenide.SelenideElement;
import interfaces.Meta;
import interfaces.SearchByVinPlate;

import static com.codeborne.selenide.Selenide.$x;

public class RVVinLookupPage implements SearchByVinPlate, Meta {

  public static SelenideElement title = $x("//h1[contains(@class,'heading')]");
  public static SelenideElement block1 = $x("//section[@id='section1']//h2");
  public static SelenideElement block2 = $x("//section[@id='section2']//h2");
  public static SelenideElement block3 = $x("//section[@id='section3']//h2");
  public static SelenideElement block4 = $x("//section[@id='section4']//h2");
  public static SelenideElement block5 = $x("//section[@id='section5']//h2");


  public String rvLookupBlock(SelenideElement block) {
    return block.getText();
  }
}
