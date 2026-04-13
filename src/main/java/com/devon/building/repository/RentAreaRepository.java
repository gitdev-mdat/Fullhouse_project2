package com.devon.building.repository;

import com.devon.building.entity.RentArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentAreaRepository extends JpaRepository<RentArea, Long> {
    List<RentArea> findByBuilding_Id(Long id);

    void deleteByBuilding_Id(Long buildingId);
}
