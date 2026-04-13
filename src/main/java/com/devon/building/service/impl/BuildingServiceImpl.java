package com.devon.building.service.impl;

import com.devon.building.convertor.BuildingConvertor;
import com.devon.building.entity.AssignmentBuilding;
import com.devon.building.entity.Building;
import com.devon.building.entity.RentArea;
import com.devon.building.entity.User;
import com.devon.building.enums.Type;
import com.devon.building.model.dto.ResponseDTO;
import com.devon.building.model.request.BuildingAssignedRequestDTO;
import com.devon.building.model.request.BuildingCreateRequestDTO;
import com.devon.building.model.request.BuildingSearchRequest;
import com.devon.building.repository.AssignmentBuildingRepository;
import com.devon.building.repository.BuildingRepository;
import com.devon.building.repository.RentAreaRepository;

import com.devon.building.repository.UserRepository;
import com.devon.building.service.BuildingService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BuildingServiceImpl implements BuildingService {
    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private RentAreaRepository rentAreaRepository;

    @Autowired
    private AssignmentBuildingRepository assignmentBuildingRepository;

    @Autowired
    private BuildingConvertor buildingConvertor;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Building> search(BuildingSearchRequest req) {
        List<String> typeCodes = req.getTypeCodes();
        boolean noTypeFilter;
        String type0 = null;
        String type1 = null;
        String type2 = null;
        if (typeCodes == null || typeCodes.isEmpty()) {
            noTypeFilter = true;
        } else {
            LinkedHashSet<String> uniq = new LinkedHashSet<>(3);
            for (String raw : typeCodes) {
                Type.parse(raw).map(Enum::name).ifPresent(uniq::add);
            }
            List<String> codes = new ArrayList<>(uniq);
            if (codes.isEmpty()) {
                noTypeFilter = true;
            } else {
                noTypeFilter = false;
                type0 = codes.get(0);
                type1 = codes.size() > 1 ? codes.get(1) : null;
                type2 = codes.size() > 2 ? codes.get(2) : null;
            }
        }
        return buildingRepository.search(
                req.getBuildingName(),
                req.getWard(),
                req.getDistrict(),
                req.getStreet(),
                req.getFloorArea(),
                req.getNumberOfBasement(),
                req.getDirection(),
                req.getLevel(),
                req.getAreaF() != null ? req.getAreaF().doubleValue() : null,
                req.getAreaT() != null ? req.getAreaT().doubleValue() : null,
                req.getRentPriceF(),
                req.getRentPriceT(),
                req.getManagerName(),
                req.getManagerPhone(),
                req.getStaffId(),
                noTypeFilter,
                type0,
                type1,
                type2
        );
    }

    @Override
    @Transactional
    public void create(BuildingCreateRequestDTO dto) {
        buildingConvertor.convertToEntity(dto);
    }

    @Override
    @Transactional
    public Object update(Long id, BuildingCreateRequestDTO dto) {
        buildingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Building not found"));
        dto.setId(id);
        Building b = buildingConvertor.convertToEntity(dto);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(b);
        return responseDTO;
    }

    @Override
    @Transactional
    public void delete(List<Long> ids) {
        for (Long id : ids) {
            rentAreaRepository.deleteByBuilding_Id(id);
            assignmentBuildingRepository.deleteByBuilding_Id(id);
        }
        buildingRepository.deleteAllById(ids);
    }

    @Override
    @Transactional
    public void assignBuilding(BuildingAssignedRequestDTO dto) {
        Building building = buildingRepository.findById(dto.getBuildingId()).orElseThrow(() -> new EntityNotFoundException("Building not found"));
        assignmentBuildingRepository.deleteByBuilding_Id(dto.getBuildingId());
        for (Long i : dto.getStaffIds()) {
            User user = userRepository.findById(i).orElseThrow(() -> new EntityNotFoundException("User not found"));
            if (user != null) {
                AssignmentBuilding assignmentBuilding = new AssignmentBuilding();
                assignmentBuilding.setBuilding(building);
                assignmentBuilding.setUser(user);
                assignmentBuildingRepository.save(assignmentBuilding);
            }
        }

    }
}
    