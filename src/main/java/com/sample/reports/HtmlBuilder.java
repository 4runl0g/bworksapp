package com.sample.reports;

import com.sample.dataprovider.TestDataFactory;
import org.testng.ITestResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Logger;

public class HtmlBuilder {

    public Logger logger = Logger.getLogger(this.getClass().getSimpleName());
    public void createLiveLogsFiles(File htmlFile) {
        try {
            if (htmlFile.createNewFile()) {
                System.out.println("Runtime Logs file is created!");
            } else {
                System.out.println("Runtime Logs file already exists.");
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
    public void prepareLogReport(File htmlFile) {
        createLiveLogsFiles(htmlFile);
        createRuntimeLogTemplate(htmlFile);
//        try {
//            Desktop.getDesktop().browse(htmlFile.toURI());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void prepareTracker(File htmlFile) {
        createLiveLogsFiles(htmlFile);
        createTrackerTemplate(htmlFile);
//        try {
//            Desktop.getDesktop().browse(htmlFile.toURI());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void createRuntimeLogTemplate(File htmlFile) {
        StringBuilder builder = new StringBuilder();
        builder
                .append("<html><title>Runtime Logs</title><meta http-equiv=\"refresh\" content=\"\" >")
                .append("<link rel=\"icon\" href=\"https://www.betterworks.com/wp-content/uploads/2018/08/Favicon_3-150x150.png\" class=\"next-head\"/>")
                .append("<link rel=\"stylesheet\" type=\"text/css\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">\n")
                .append("<link rel=\"stylesheet\" type=\"text/css\" href=\"https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap.min.css\">\n")
                .append("<script type=\"text/javascript\" src=\"https://code.jquery.com/jquery-3.3.1.js\"></script>\n")
                .append("<script type=\"text/javascript\" src=\"https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js\"></script>\n")
                .append("<script type=\"text/javascript\" src=\"https://cdn.datatables.net/responsive/2.2.3/js/dataTables.responsive.min.js\"></script>\n")
                .append("<link rel=\"stylesheet\" type=\"text/css\" href=\"https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css\">\n")
                .append("<link rel=\"stylesheet\" type=\"text/css\" href=\"https://cdn.datatables.net/responsive/2.2.3/css/responsive.dataTables.min.css\">\n")
                .append("<script>$(document).ready(function() {\n" +
                        "    $('#example').DataTable( {\n" +
                        "        responsive: {\n" +
                        "            details: {\n" +
                        "                type: 'column'\n" +
                        "            }\n" +
                        "        },\n" +
                        "        columnDefs: [ {\n" +
                        "            className: 'control',\n" +
                        "            orderable: false,\n" +
                        "            targets:   0\n" +
                        "        },\n" +
                        "         {\n" +
                        "            className: 'none',\n" +
                        "            targets: -1\n" +
                        "            }\n" +
                        "        ],\n" +
                        "    } );\n" +
                        "} );</script></html>")
                .append("<body><h3 align=\"center\"><b><img src=\"https://www.betterworks.com/wp-content/uploads/2018/08/Favicon_3-150x150.png\" width=\"50\" height=\"50\">Test Cases Results</b></h3>")
                .append("<table id=\"example\" class=\"display responsive\" style=\"width:100%\">")
                .append("<thead><tr><th></th><th style=\"text-align: center\">TestCaseId</th><th style=\"text-align: center\">Test Suite</th><th style=\"text-align: center\">Test Case</th><th style=\"text-align: center\">Test Parameters</th><th style=\"text-align: center\">Status</th>")
                .append("<th style=\"text-align: center\">Error Message</th><th style=\"text-align: center\">Stack Trace Exception</th></tr></thead><tbody>")
                .append("");
        writeToFile(htmlFile, builder.toString(), false);
    }

    private void writeToFile(File htmlFile, String text, boolean appendText) {
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(htmlFile, appendText);
            fileWriter.write(text);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void afterComplete(File htmlFile){
        String completedText = "<table align=\"center\"><tr><th><h4>Execution completed! </h4></th></tr></table>";
        writeToFile(htmlFile, completedText, true);
    }

    public void writeResultsToFile(File htmlFile, File testCaseTrackerHtmlFile, ITestResult result, String timeNow, String status) {
        StringBuilder builder = new StringBuilder();

        String resultStatus;

        switch (status) {
            case "PASS":
                resultStatus = "<td style=\"color: #006400\"><b>PASS</b></td>";
                break;
            case "FAIL":
                resultStatus = "<td style=\"color: #B22222\"><b>FAIL</b></td>";
                break;
            case "SKIP":
                resultStatus = "<td style=\"color: #DAA520\"><b>SKIP</b></td>";
                break;
            default:
                resultStatus = "<td>Unknown</td>";
                break;
        }

        String fullClassName = result.getTestClass().getName();
        String className = fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
        String methodName = result.getMethod().getMethodName();
        String errorMessage = ""; String shortStackTraceErrorMessage = ""; String testParams ="";
        Object[] testParameters = result.getParameters();

        if (testParameters.length > 0 && testParameters != null) {
            testParams =  Arrays.toString(testParameters);
        }

        if (result.getThrowable() != null) {
            shortStackTraceErrorMessage =
                    org.testng.internal.Utils.shortStackTrace(result.getThrowable(),
                            true);
            errorMessage = result.getThrowable().toString();
        }

        builder
                .append("<tr>")
                .append("<td></td>")
                .append(String.format("<td style=\"text-align: left;word-wrap: break-word;white-space: normal\"><font size=\"2\">%s</font></td>",timeNow))
                .append(String.format("<td style=\"text-align: left;word-wrap: break-word;white-space: normal\"><font size=\"2\">%s</font></td>",className))
                .append(String.format("<td style=\"text-align: left;word-wrap: break-word;white-space: normal\"><font size=\"2\">%s</font></td>",methodName))
                .append(String.format("<td style=\"text-align: left;word-wrap: break-word;white-space: normal\"><font size=\"2\">%s</font></td>",testParams))
                .append(resultStatus)
                .append(String.format("<td style=\"text-align: left;word-wrap: break-word;white-space: normal; color: #B22222\"><font size=\"2\"><i>%s</i></font></td>",errorMessage))
                .append(String.format("<td style=\"text-align: left;word-wrap: break-word;white-space: normal; color: #B22222\"><font size=\"2\"><i><a href=%s>Testcase Detailed Log</a><br>%s</i></font></td>",testCaseTrackerHtmlFile,shortStackTraceErrorMessage))
                .append("</tr>");
        writeToFile(htmlFile, builder.toString(), true);
    }

    public void createTrackerTemplate(File htmlFile) {
        StringBuilder builder = new StringBuilder();
        builder
                .append("<html><title>Runtime Logs</title><meta http-equiv=\"refresh\" content=\"\" >")
                .append("</html>")
                .append("<body><h3 align=\"center\"><b><img src=\"https://www.betterworks.com/wp-content/uploads/2018/08/Favicon_3-150x150.png\" width=\"50\" height=\"50\"> Event Tracker</b></h3>")
                .append("<table id=\"example\" class=\"display responsive\" style=\"width:100%\">")
                .append("<thead><tr><th></th><th style=\"text-align: center\">Events</th><th style=\"text-align: center\">API</th><th style=\"text-align: center\">DataFactory</th><th style=\"text-align: center\">Request Params</th><th style=\"text-align: center\">Response Params</th></tr></thead><tbody>")
                .append("");
        writeToFile(htmlFile, builder.toString(), true);
    }
}