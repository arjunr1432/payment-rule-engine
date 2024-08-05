package com.example.models;

import java.util.HashMap;
import java.util.Map;

public class DBScanSpec {

    private String filterCondition;

    private boolean noneMatched;

    private Map<String, String> attributeNameMap = new HashMap<>();

    private Map<String, Object> attributeValueMap = new HashMap<>();

    public String getFilterCondition() {
        return filterCondition;
    }

    public void setFilterCondition(String filterCondition) {
        this.filterCondition = filterCondition;
    }

    public Map<String, String> getAttributeNameMap() {
        return attributeNameMap;
    }

    public Map<String, Object> getAttributeValueMap() {
        return attributeValueMap;
    }

    public boolean isNoneMatched() {
        return noneMatched;
    }

    public void setNoneMatched(boolean noneMatched) {
        this.noneMatched = noneMatched;
    }
}
