package com.devon.building.model.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BuildingSearchResponse {
    Long id;
    String name;
    String address;
    Integer numberOfBasement;
    String managerName;
    String managerPhone;
    Integer floorArea;
    String rentArea;
    Integer emptyArea;
    Integer price;
    String serviceFee;
    BigDecimal brokerageFee;
}
