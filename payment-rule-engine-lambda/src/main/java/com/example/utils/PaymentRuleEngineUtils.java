package com.example.utils;

import com.example.models.DBScanSpec;

import java.util.Map;

public class PaymentRuleEngineUtils {

    public static DBScanSpec getDBScanSpecification(Map<String, Object> input) {
        DBScanSpec dbScanSpec = new DBScanSpec();
        StringBuilder filterBuilder = new StringBuilder();
        var ref = new Object() {
            boolean noneMatched = true;
        };
        if (null != input && !input.isEmpty()) {
            input.keySet().forEach(key -> {
                switch (key) {
                    case "DaysSinceLast3DS" -> {
                        if (!ref.noneMatched)
                            filterBuilder.append(" OR ");
                        filterBuilder.append("attribute_exists(Criteria.Last3DSTransactionWithinDays)");
                        ref.noneMatched = false;
                    }
                    case "TransactionAmount" -> {
                        if (!ref.noneMatched)
                            filterBuilder.append(" OR ");
                        filterBuilder.append("attribute_exists(Criteria.TransactionAmountGreaterThan) OR attribute_exists(Criteria.TransactionAmountLessThan)");
                        ref.noneMatched = false;
                    }
                    default -> {
                        if (!ref.noneMatched)
                            filterBuilder.append(" OR ");
                        filterBuilder.append("Criteria.#").append(key).append(" = :").append(key);
                        dbScanSpec.getAttributeNameMap().put("#" + key, key);
                        dbScanSpec.getAttributeValueMap().put(":" + key, input.get(key));
                        ref.noneMatched = false;
                    }
                }
            });
        }
        dbScanSpec.setFilterCondition(filterBuilder.toString());
        dbScanSpec.setNoneMatched(ref.noneMatched);
        return dbScanSpec;
    }
}
