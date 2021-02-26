package com.sample.dataprovider;

import org.json.JSONObject;

public class TestDataFactory {

    private String testCaseId;
    private JSONObject testcaseParameters;
    private String group;
    private String category;
    private JSONObject types;
    private JSONObject options;
    private String type;

    public String getTestCaseId() {
        return testCaseId;
    }

    public void setTestCaseId(String testCaseId) {
        this.testCaseId = testCaseId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public JSONObject getOptions() {
        return options;
    }

    public void setOptions(JSONObject options) {
        this.options = options;
    }

    public JSONObject getTypes() {
        return types;
    }

    public void setTypes(JSONObject types) {
        this.types = types;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public JSONObject getTestcaseParameters() {
        return testcaseParameters;
    }

    public void setTestcaseParameters(JSONObject testcaseParameters) {
        this.testcaseParameters = testcaseParameters;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

}
