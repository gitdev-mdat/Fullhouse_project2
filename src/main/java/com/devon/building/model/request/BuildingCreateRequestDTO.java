package com.devon.building.model.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BuildingCreateRequestDTO {
    private Long id;
    @NotBlank(message = "Name Building is missing")
    private String name;
    @NotBlank(message = "Ward is missing")
    private String ward;
    @NotBlank(message = "District is missing")
    private String district;
    private String street;
    private String structure;
    private Integer floorArea;
    @Min(value = 0, message = "Number Of Basement must be greater or equal 0")
    private Integer numberOfBasement;
    private String direction;
    private String level;
    private String rentAreas;
    @Positive(message = "Rent price must be greater than 0")
    private Integer price;
    private String rentPriceDescription;
    private String serviceFee;
    private String carFee;
    private String overTimeFee;
    private String managerName;
    @Pattern(regexp = "\\d{10}", message = "Number must to be at least 10 digits")
    private String managerPhone;
    @NotEmpty(message = "Typecode is required")
    private List<String> typeCodes;
    private BigDecimal brokerageFee;
    private String note;
}
