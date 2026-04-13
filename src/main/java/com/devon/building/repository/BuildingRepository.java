package com.devon.building.repository;

import com.devon.building.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BuildingRepository extends JpaRepository<Building, Long> {

    @Query("""
            SELECT DISTINCT b FROM Building b
            LEFT JOIN b.assignmentBuildings ab
            LEFT JOIN b.rentAreas ra
            WHERE (:name IS NULL OR b.name LIKE %:name%)
              AND (:ward IS NULL OR b.ward LIKE %:ward%)
              AND (:district IS NULL OR b.district LIKE %:district%)
              AND (:street IS NULL OR b.street LIKE %:street%)
              AND (:floorArea IS NULL OR b.floorArea = :floorArea)
              AND (:basement IS NULL OR b.numberOfBasement = :basement)
              AND (:direction IS NULL OR b.direction LIKE %:direction%)
              AND (:level IS NULL OR b.level LIKE %:level%)
              AND (:areaF IS NULL OR ra.value >= :areaF)
              AND (:areaT IS NULL OR ra.value <= :areaT)
              AND (:priceF IS NULL OR b.price >= :priceF)
              AND (:priceT IS NULL OR b.price <= :priceT)
              AND (:managerName IS NULL OR b.managerName LIKE %:managerName%)
              AND (:managerPhone IS NULL OR b.managerPhone LIKE %:managerPhone%)
              AND (:staffId IS NULL OR ab.user.id = :staffId)
              AND (:noTypeFilter = TRUE
                   OR (:type0 IS NOT NULL AND CONCAT(',', REPLACE(b.type, ' ', ''), ',') LIKE CONCAT('%,', :type0, ',%'))
                   OR (:type1 IS NOT NULL AND CONCAT(',', REPLACE(b.type, ' ', ''), ',') LIKE CONCAT('%,', :type1, ',%'))
                   OR (:type2 IS NOT NULL AND CONCAT(',', REPLACE(b.type, ' ', ''), ',') LIKE CONCAT('%,', :type2, ',%')))
            """)
    List<Building> search(
            String name,
            String ward,
            String district,
            String street,
            Integer floorArea,
            Integer basement,
            String direction,
            String level,
            Double areaF,
            Double areaT,
            Double priceF,
            Double priceT,
            String managerName,
            String managerPhone,
            Long staffId,
            boolean noTypeFilter,
            String type0,
            String type1,
            String type2
    );
}
