package com.devon.building.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentBuildingResponseDTO {
    private Long id;
    private String name;
    private Boolean checked;
}
