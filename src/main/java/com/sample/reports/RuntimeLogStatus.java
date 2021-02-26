package com.sample.reports;

import org.testng.ITestResult;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RuntimeLogStatus {

    public Logger logger = Logger.getLogger(this.getClass().getSimpleName());
    public enum Status implements Serializable {
        PASS,
        FAIL,
        INFO,
        SKIP;
    }

    public Status logTestResult(ITestResult result){
        Status testResult;
        switch(result.getStatus()){
            case 1:
                testResult = Status.PASS;
                break;
            case 2:
                testResult = Status.FAIL;
                break;
            case 3:
                testResult = Status.SKIP;
                break;
            default:
                testResult = Status.INFO;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Test result: %s%s", testResult, System.lineSeparator()));
        logger.log(Level.INFO,Thread.currentThread().getName() + sb.toString());
        return testResult;
    }
}