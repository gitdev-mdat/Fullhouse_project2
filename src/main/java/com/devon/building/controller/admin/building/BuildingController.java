package com.devon.building.controller.admin.building;

import com.devon.building.convertor.BuildingConvertor;
import com.devon.building.entity.Building;
import com.devon.building.enums.District;
import com.devon.building.model.request.BuildingCreateRequestDTO;
import com.devon.building.model.request.BuildingSearchRequest;
import com.devon.building.model.response.AssignmentBuildingResponseDTO;
import com.devon.building.model.response.BuildingSearchResponse;
import com.devon.building.repository.BuildingRepository;
import com.devon.building.service.BuildingService;
import com.devon.building.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequestMapping("/admin/buildings")
@AllArgsConstructor
public class BuildingController {
    @Autowired
    private UserService userService;

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private BuildingConvertor buildingConvertor;

    @InitBinder("buildingSearchRequest")
    public void initBuildingSearchBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
        binder.registerCustomEditor(Long.class, new CustomNumberEditor(Long.class, true));
        binder.registerCustomEditor(Double.class, new CustomNumberEditor(Double.class, true));
    }

    @InitBinder("buildingCreateRequestDTO")
    public void initBuildingCreateBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
    }

    @GetMapping("/list")
    public String getBuildings(@ModelAttribute BuildingSearchRequest buildingSearchRequest, Model model) {
        model.addAttribute("modelSearch", buildingSearchRequest);
        model.addAttribute("staffs", userService.getStaffs());
        model.addAttribute("districts", District.getDistricts());
        List<BuildingSearchResponse> bsr = new ArrayList<>();
        List<Building> buildings = buildingService.search(buildingSearchRequest);
        for (Building b : buildings) {
            bsr.add(buildingConvertor.convertToResponseDTO(b));
        }
        model.addAttribute("result", bsr);
        return "admin/building/buildingList";
    }

    @GetMapping("/edit")
    public String editBuilding(@RequestParam(required = false) Long id, Model model) {
        return buildingEditView(id, model);
    }

    @GetMapping("{id}/update")
    public String updateBuilding(@PathVariable Long id, Model model) {
        return buildingEditView(id, model);
    }

    private String buildingEditView(Long id, Model model) {
        BuildingCreateRequestDTO dto = new BuildingCreateRequestDTO();
        if (id != null) {
            Building building = buildingRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
            dto = buildingConvertor.toCreateRequestDTO(building);
        }
        model.addAttribute("buildingCreateRequestDTO", dto);
        model.addAttribute("districts", District.getDistricts());
        return "admin/building/buildingEdit";
    }
    @GetMapping("/assignment-staff")
    @ResponseBody
    public List<AssignmentBuildingResponseDTO> assignmentStaff(@RequestParam("buildingId") Long buildingId) {
        Map<Long, String> users = userService.getStaffs();
        Set<Long> assignedStaffIds = buildingRepository.findById(buildingId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND))
                .getStaffs().stream()
                .map(user -> user.getId())
                .collect(Collectors.toSet());
        List<AssignmentBuildingResponseDTO> dtos = new ArrayList<>();
        for (Map.Entry<Long, String> e : users.entrySet()) {
            AssignmentBuildingResponseDTO dto = new AssignmentBuildingResponseDTO();
            dto.setId(e.getKey());
            dto.setName(e.getValue());
            dto.setChecked(assignedStaffIds.contains(e.getKey()));
            dtos.add(dto);
        }
        return dtos;
    }
}
