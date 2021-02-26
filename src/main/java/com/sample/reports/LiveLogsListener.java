package com.sample.reports;

import org.testng.IReporter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import java.io.File;
import java.util.logging.Logger;

public class LiveLogsListener extends HtmlBuilder implements IReporter, ITestListener {

    public Logger logger = Logger.getLogger(this.getClass().getSimpleName());
    String logPath = System.getProperty("user.dir") + File.separator + "LiveLogs.html";
    File htmlFile = new File(logPath);

    public void onFinish(ITestContext context) {
        afterComplete(htmlFile);
    }
}