package com.devon.building.model.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BuildingSearchRequest {
    String buildingName; // entity Building
    String ward ; // ____
    String district;
    String street;
    Integer floorArea;
    Integer numberOfBasement;
    String direction;
    String level;
    Integer areaF; // entity rentarea
    Integer areaT; // ______
    Double rentPriceF; // entity Building
    Double rentPriceT; // ___
    String managerName; // entity User
    String managerPhone; // ____
    Long staffId; // entity User
    List<String> typeCodes;
}
