package com.devon.building.enums;

import java.util.LinkedHashMap;
import java.util.Map;

public enum District {
    QUAN_1("Quận 1"),
    QUAN_2("Quận 2"),
    QUAN_3("Quận 3"),
    QUAN_10("Quận 10"),
    QUAN_4("Quận 4"),
    QUAN_5("Quận 5"),
    QUAN_6("Quận 6"),
    QUAN_7("Quận 7"),
    QUAN_8("Quận 8"),
    QUAN_9("Quận 9"),
    QUAN_TB("Quận Tân Bình"),
    QUAN_PN("Quận Phú Nhuận"),
    QUAN_QB("Quận Bình Thạnh"),
    QUAN_QTB("Quận Thủ Đức"),
    QUAN_12("Quận 12"),
    QUAN_11("Quận 11");

    private final String districtName;

    District(String districtName) {
        this.districtName = districtName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public static Map<String, String> getDistricts() {
        Map<String, String> map = new LinkedHashMap<>();
        for (District d : District.values()) {
            map.put(d.name(), d.getDistrictName());
        }
        return map;
    }
}