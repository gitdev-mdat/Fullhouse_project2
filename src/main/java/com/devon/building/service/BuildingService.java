package com.devon.building.service;

import com.devon.building.entity.Building;
import com.devon.building.model.request.BuildingAssignedRequestDTO;
import com.devon.building.model.request.BuildingCreateRequestDTO;
import com.devon.building.model.request.BuildingSearchRequest;

import java.util.List;

public interface BuildingService {
    List<Building> search(BuildingSearchRequest buildingSearchRequest);

    void create(BuildingCreateRequestDTO dto);

    Object update(Long id, BuildingCreateRequestDTO dto);

    public void delete(List<Long> ids);

    void assignBuilding(BuildingAssignedRequestDTO dto);
}
