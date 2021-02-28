package com.sample.pageObjects;

import com.sample.dataprovider.TestDataFactory;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CreateNewPostPage {

    private WebDriver driver;

    @FindBy(how = How.XPATH, using = "//*[@id='create-button']")
    List<WebElement> createPostBtn;

    @FindBy(how = How.XPATH, using = "//button[contains(string(), 'OPEN')]")
    List<WebElement> openTypeBtn;

    @FindBy(how = How.XPATH, using = "//span[@class='Select-arrow']")
    List<WebElement> dropDownArrow;

    @FindBy(how = How.XPATH, using = "//div[@class='Select-option-group']//div")
    List<WebElement> selectOption;

    @FindBy(how = How.XPATH, using = "//button[@type='submit' and contains(string(), 'Select')]")
    List<WebElement> selectBtn;

    @FindBy(how = How.XPATH, using = "//textArea[@name='question']")
    List<WebElement> writePostTextField;

    @FindBy(how = How.XPATH, using = "//button[contains(string(), 'Publish post')]")
    List<WebElement> publishPostBtn;

    @FindBy(how = How.XPATH, using = "//div[@class='ant-radio-group']//label")
    List<WebElement> selectCategory;

    @FindBy(how = How.XPATH, using = "//button[contains(string(), 'MULTIPLE CHOICE')]")
    List<WebElement> multipleChoiceTypeBtn;

    @FindBy(how = How.XPATH, using = "//input[@id='inputGroupField']")
    List<WebElement> multiChoiceOptionsText;

    //Create acct page identifier
    public CreateNewPostPage(WebDriver driver) {
        this.driver=driver;
        PageFactory.initElements(driver, this);
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfAllElements(createPostBtn)));
    }

    //select category (Anonymous/Official/Named)
    public void selectCategory(String category) {
        for (int i=0;i<selectCategory.size();i++) {
            System.out.println(selectCategory.get(i).getText());
            if (selectCategory.get(i).getText().equalsIgnoreCase(category)) {
                selectCategory.get(i).click();
                break;
            }
        }
    }

    //select type (Open / Multiple Choice)
    public void selectType(String type) {
        switch (type) {
            case "Open": default:
                openTypeBtn.get(0).click();
                break;
            case "Multiple Choice":
                multipleChoiceTypeBtn.get(0).click();
                break;
        }
    }

    //select group from drop down list
    public void selectGroup(String group) {
        dropDownArrow.get(0).click();
        System.out.println(selectOption.size());
        for (int i=0;i<selectOption.size();i++) {
            System.out.println(selectOption.get(i).getText());
            if (selectOption.get(i).getText().equalsIgnoreCase(group)) {
                selectOption.get(i).click();
                break;
            }
        }
    }

    //click SELECT btn after selecting group
    public void clickSelectBtn() {
        selectBtn.get(0).click();
    }

    //enter content and options based on type to write post
    public void enterTextToWritePost(String content, String type, JSONObject optionsObj) {
        if (type.equalsIgnoreCase("Open")) {
            writePostTextField.get(0).sendKeys(content);
        } else {
            writePostTextField.get(0).sendKeys(content);
            for (int i=0;i<optionsObj.length();i++) {
                String optionText = optionsObj.getString("option"+(i+1));
                multiChoiceOptionsText.get(i).sendKeys(optionText);
            }
        }
    }

    //click PUBLISH POST btn
    public void clickPublishPostBtn() {
        publishPostBtn.get(0).click();
    }

    /*
        1. Select category (Anonymous/Official/Named) using dataprovider
        2. Click type (Open / Multiple Choice) using dataprovider
        3. Enter content (for both Open & Multiple Choice) and options (Only for Multiple Choice) text based on type selected
        4. Click PUBLISH POST btn to create a post
     */
    public void writePost(TestDataFactory dataFactory) {
        selectCategory(dataFactory.getCategory());
        selectType(dataFactory.getType());
        selectGroup(dataFactory.getGroup());
        clickSelectBtn();
        enterTextToWritePost(dataFactory.getTestCaseId(), dataFactory.getType(), dataFactory.getOptions());
        clickPublishPostBtn();
    }

}
