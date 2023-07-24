package com.RewardsCalculator.service;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.Collections;

public enum StoreType {
    STORE_1("STORE1"),
    STORE_2("STORE2"),

    NO_MATCH("");

    private static final Map<StoreType, String> storesMap;

    static {
        Map<StoreType, String> map = new EnumMap<>(StoreType.class);
        map.put(STORE_1, "matching promotions for store 1");
        map.put(STORE_2, "matching promotions for store 2");
        storesMap = Collections.unmodifiableMap(map);
    }

    String storeTypeName;

    StoreType(String ruleTypeName) {
        this.storeTypeName = ruleTypeName;
    }

    public static StoreType formString(String text) {
        return Arrays.stream(StoreType.values())
                .filter(ruleType -> ruleType.name().equals(text))
                .findAny()
                .orElse(NO_MATCH);
    }

    public String getRuleDescription() {
        return storesMap.getOrDefault(this,
                "No description available for rule");
    }
}
