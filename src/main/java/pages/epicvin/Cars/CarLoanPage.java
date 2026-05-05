package pages.epicvin.Cars;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class CarLoanPage {

  public static SelenideElement title = $x("//h1[contains(@class,'heading')]");
  public static SelenideElement block1 = $(By.id("nav0"));
  public static SelenideElement block2 = $(By.id("nav1"));
  public static SelenideElement block3 = $(By.id("nav2"));


  public String carLoanBlock(SelenideElement block) {
    return block.getText();
  }
}
