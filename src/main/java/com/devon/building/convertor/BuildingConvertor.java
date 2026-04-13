package com.devon.building.convertor;

import com.devon.building.entity.Building;
import com.devon.building.entity.RentArea;
import com.devon.building.enums.District;
import com.devon.building.enums.Type;
import com.devon.building.model.request.BuildingCreateRequestDTO;
import com.devon.building.model.response.BuildingSearchResponse;

import com.devon.building.repository.BuildingRepository;
import com.devon.building.repository.RentAreaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class BuildingConvertor {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private RentAreaRepository rentAreaRepository;

    public BuildingSearchResponse convertToResponseDTO (Building item) {
        BuildingSearchResponse dto = modelMapper.map(item, BuildingSearchResponse.class);
        StringBuilder address = new StringBuilder();
        if (item.getStreet() != null) {
            address.append(item.getStreet());
        }
        if (item.getWard() != null) {
            if (!address.isEmpty())
                address.append(",   ");
            address.append(item.getWard());
        }
        String districtCode = item.getDistrict();
        District districtEnum = null;

        if (districtCode != null) {
            try {
                districtEnum = District.valueOf(districtCode);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        if (districtEnum != null) {
            address.append(", ");
            address.append(districtEnum.getDistrictName());
        }
        dto.setAddress(address.toString());

        List<RentArea> rentAreas = item.getRentAreas();
        if (rentAreas == null || rentAreas.isEmpty()) {
            dto.setRentArea("");
        } else {

            dto.setRentArea(
                    rentAreas.stream()
                            .map(RentArea::getValue)
                            .filter(v -> v != null)
                            .map(String::valueOf)
                            .collect(Collectors.joining(", ")));
        }

        if (item.getFloorArea() != null && rentAreas != null && !rentAreas.isEmpty()) {

            int totalRentArea = rentAreas.stream()
                    .map(RentArea::getValue)
                    .filter(v -> v != null)
                    .mapToInt(Integer::intValue)
                    .sum();

            int emptyArea = item.getFloorArea() - totalRentArea;
            dto.setEmptyArea(Math.max(emptyArea, 0));
        }
        return dto;
    }

        public BuildingCreateRequestDTO toCreateRequestDTO(Building entity) {
            BuildingCreateRequestDTO dto = new BuildingCreateRequestDTO();
            if (entity == null) {
                return dto;
            }
            dto = modelMapper.map(entity, BuildingCreateRequestDTO.class);
            if (entity.getRentAreas() != null && !entity.getRentAreas().isEmpty()) {
                dto.setRentAreas(
                        entity.getRentAreas().stream()
                                .map(RentArea::getValue)
                                .filter(Objects::nonNull)
                                .map(String::valueOf)
                                .collect(Collectors.joining(",")));
            }
            if (entity.getType() != null && !entity.getType().isBlank()) {
                dto.setTypeCodes(
                        Arrays.stream(entity.getType().split(","))
                                .map(String::trim)
                                .filter(s -> !s.isEmpty())
                                .collect(Collectors.toCollection(ArrayList::new)));
            } else {
                dto.setTypeCodes(new ArrayList<>());
            }
            return dto;
        }

    public Building convertToEntity(BuildingCreateRequestDTO dto) {
        if (dto.getId() != null) {
            List<Long> rentAreaIds = rentAreaRepository.findByBuilding_Id(dto.getId())
                    .stream()
                    .map(RentArea::getId)
                    .collect(Collectors.toList());
            for (Long i : rentAreaIds) {
                rentAreaRepository.findById(i).ifPresent(rentAreaRepository::delete);
            }
        }
        Building b = modelMapper.map(dto, Building.class);
        List<String> codes = dto.getTypeCodes() != null ? dto.getTypeCodes() : List.of();
        String type = codes.stream()
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(raw -> Type.parse(raw).map(Enum::name).orElse(raw.toUpperCase().replace('-', '_')))
                .distinct()
                .collect(Collectors.joining(","));
        b.setType(type);

        Building saved = buildingRepository.save(b);

        if (dto.getRentAreas() != null && !dto.getRentAreas().isBlank()) {
            for (String part : dto.getRentAreas().split(",")) {
                String t = part.trim();
                if (t.isEmpty()) {
                    continue;
                }
                int v = Integer.parseInt(t);
                RentArea ra = new RentArea();
                ra.setBuilding(saved);
                ra.setValue(v);
                rentAreaRepository.save(ra);
            }
        }
        return b;
    }
}
