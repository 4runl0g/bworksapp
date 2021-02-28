package com.sample.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

public class LoginPage {

    private WebDriver driver;

    @FindBy(how = How.XPATH, using = "//input[@name='email']")
    List<WebElement> emailField;

    @FindBy(how = How.XPATH, using = "//button[@class='ladda-button btn btn-primary hyphenButton loginButton']")
    List<WebElement> clickSendVerifyCodeBtn;

    @FindBy(how = How.XPATH, using = "//input[@name='verificationCode']")
    List<WebElement> enterVerificationCode;

    @FindBy(how = How.XPATH, using = "//button[@type='submit']")
    List<WebElement> submitBtn;


    //login page identifier
    public LoginPage(WebDriver driver) {
        this.driver=driver;
        PageFactory.initElements(driver, this);
    }

    //enter emailId
    public void enterEmailId(String emailId) {
        emailField.get(0).sendKeys(emailId);
    }

    //click send verification code btn
    public void clickSendVerifyCodeBtn() {
        clickSendVerifyCodeBtn.get(0).click();
    }

    //enter verification code
    public void enterVerificationCode(String code) {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(By.name("verificationCode"))));
        enterVerificationCode.get(0).sendKeys(code);
    }

    //click submit btn to login
    public void clickSubmitBtn() {
        submitBtn.get(0).click();
    }

    /*  1. Enter emil Id
        2. Click send verification code btn
        3. Click Submit btn to login
     */
    public void appLogin(String emailId, String code) {
        enterEmailId(emailId);
        clickSendVerifyCodeBtn();
        enterVerificationCode(code);
        clickSubmitBtn();
    }

}
