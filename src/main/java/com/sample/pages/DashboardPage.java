package com.sample.pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

public class DashboardPage {

    private WebDriver driver;

    @FindBy(how = How.XPATH, using = "//*[@id='create-button']")
    List<WebElement> createPostBtn;

    @FindBy(how = How.XPATH, using = "//span[@class='Linkify']")
    List<WebElement> verifyCreatedPostText;

    //Dashboard page identifier
    public DashboardPage(WebDriver driver) {
        this.driver=driver;
        PageFactory.initElements(driver, this);
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfAllElements(createPostBtn)));
    }

    //click CREATE NEW POST btn on dashboard screen
    public void clickCreateNewPostBtn() {
        createPostBtn.get(0).click();
    }

    //returns post title to verify in test case
    public String verifyCreatedPostText() {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfAllElements(verifyCreatedPostText)));
        String postText = verifyCreatedPostText.get(0).getText();
        return postText;
    }

}
