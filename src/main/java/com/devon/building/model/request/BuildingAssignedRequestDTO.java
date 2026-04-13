package com.devon.building.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BuildingAssignedRequestDTO {
    @NotNull
    private Long buildingId;
    private List<Long> staffIds;
}
