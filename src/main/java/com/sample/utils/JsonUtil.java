package com.sample.utils;

import com.sample.dataprovider.TestDataFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;

public class JsonUtil {

    //JSON file reader to read and iterate testcase json using dataprovider
    public Iterator<Object[]> setTestData(String jsonFilePath, String testName) throws IOException {
        Collection<Object[]> provider = new ArrayList<Object[]>();
        File file = new File(jsonFilePath);
        String content = FileUtils.readFileToString(new File(file.getCanonicalPath()), "utf-8");
        JSONObject object = new JSONObject(content);
        JSONArray testSuite = new JSONArray(object.get("TestCases").toString());
        for (int i = 0; i < testSuite.length(); i++) {
            JSONObject testCase = (JSONObject) testSuite.get(i);
            try {
                if (testCase.getString("testCaseName").equalsIgnoreCase(testName) && testCase.getBoolean("execute")) {
                    TestDataFactory dataFactory = new TestDataFactory();
                    dataFactory.setTestCaseId(testCase.getString("testCaseId"));
                    dataFactory.setTestcaseParameters(testCase.getJSONObject("testCaseParameters"));
                    if (dataFactory.getTestcaseParameters().has("category")) {
                        dataFactory.setCategory(dataFactory.getTestcaseParameters().getString("category"));
                        dataFactory.setGroup(dataFactory.getTestcaseParameters().getString("group"));
                        JSONObject data = dataFactory.getTestcaseParameters();
                        JSONObject typesObj = data.optJSONObject("types");
                        dataFactory.setTypes(typesObj);
                        String type = typesObj.getString("typeName");
                        dataFactory.setType(type);
                        if (type.equalsIgnoreCase("Multiple Choice")) {
                            JSONObject optionsObj = typesObj.getJSONObject("options");
                            dataFactory.setOptions(optionsObj);
                        }
                    }
                    provider.add(new Object[]{dataFactory});
                }
            } catch (Exception e) {
                System.out.print(e);
            }
        }
        return provider.iterator();
    }
}
