package com.devon.building.repository;

import com.devon.building.entity.AssignmentBuilding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentBuildingRepository extends JpaRepository<AssignmentBuilding, Long> {
    void deleteByBuilding_Id(Long buildingId);
    List<AssignmentBuilding> findByBuilding_Id(Long buildingId);
}
