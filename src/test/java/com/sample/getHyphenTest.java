package com.sample;

import com.sample.dataprovider.TestDataFactory;
import com.sample.driverfactory.ChromeDriverConfig;
import com.sample.pages.CreateNewPostPage;
import com.sample.pages.DashboardPage;
import com.sample.reports.LiveLogsListener;
import com.sample.reports.RuntimeLogStatus;
import com.sample.utils.JsonUtil;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.*;
import com.sample.pages.LoginPage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Iterator;

public class getHyphenTest {

    private WebDriverManager driverManager;
    private String webURL = "https://app.gethyphen.com/";
    private String emailId = "hyphen_admin@acmetest.com";
    private String code = "34067";

    String LOG_FILE = this.getClass().getSimpleName().toLowerCase()+"_testresult.html";
    String TEST_CASE_TRACKER = this.getClass().getSimpleName().toLowerCase()+"_fullstack_trace.html";

    public static final String GetHyphenTestcase_JSON_FILE_PATH = System.getProperty("user.dir")
            + "/src/test/resources/GetHyphenTestcase.json";

    //Dataprovider to read testcase parameters and iterate for test suite
    @DataProvider(name = "createPostData", parallel = true)
    public Iterator<Object[]> getTestcaseData(Method m) throws IOException {
        System.out.println(m.getName());
        return new JsonUtil().setTestData(GetHyphenTestcase_JSON_FILE_PATH, m.getName());
    }

    @BeforeSuite(alwaysRun = true)
    public void preRequisiteTestData() {
        new LiveLogsListener().prepareLogReport(new File(LOG_FILE));
    }

    @BeforeTest
    public void launch() {
        driverManager.chromedriver().setup();
    }

    //Create new post by passing type and category via testcase
    @Test(dataProvider = "createPostData")
    public void createPost(TestDataFactory dataFactory) {
//        String webURL = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("web_url");
//        String emailId = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("email_id");
//        String code = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("code");

        WebDriver driver = new ChromeDriverConfig().getChromeDriver();
        driver.get(webURL);

        LoginPage loginPage = new LoginPage(driver);
        loginPage.appLogin(emailId,code);

        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.clickCreateNewPostBtn();

        CreateNewPostPage newPostPopup = new CreateNewPostPage(driver);
        newPostPopup.writePost(dataFactory);

        Assert.assertEquals(dashboardPage.verifyCreatedPostText().toLowerCase(),dataFactory.getTestCaseId().toLowerCase());
        driver.close();
    }

    @AfterMethod
    public void getResult(ITestResult result) {
        RuntimeLogStatus.Status status = new RuntimeLogStatus().logTestResult(result);
        new LiveLogsListener().writeResultsToFile(new File(LOG_FILE), result, new Date().toString(), status.toString());
    }

}