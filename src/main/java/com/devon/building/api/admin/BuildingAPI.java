package com.devon.building.api.admin;

import com.devon.building.model.dto.ResponseDTO;
import com.devon.building.model.request.BuildingAssignedRequestDTO;
import com.devon.building.model.request.BuildingCreateRequestDTO;
import com.devon.building.service.BuildingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/buildings")
@AllArgsConstructor
public class BuildingAPI {
    private final BuildingService buildingService;

    @PostMapping
    public ResponseEntity<Object> addBuilding(@RequestBody @Valid BuildingCreateRequestDTO dto, BindingResult bindingResult) {
        ResponseDTO responseDTO = new ResponseDTO();
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            responseDTO.setMessage("Failed to add Building");
            responseDTO.setDetail(errorMessages);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
        }
        buildingService.create(dto);
        responseDTO.setMessage("Successfully added Building");
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }
    @PutMapping("{id}")
    public ResponseEntity<Object> updateBuilding(@PathVariable Long id, @RequestBody @Valid BuildingCreateRequestDTO dto, BindingResult bindingResult) {
        ResponseDTO responseDTO = new ResponseDTO();
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            responseDTO.setMessage("Failed to update Building");
            responseDTO.setDetail(errorMessages);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
        }
        buildingService.update(id, dto);
        responseDTO.setMessage("Successfully updated Building");
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }
    @DeleteMapping("/{ids}")
    public ResponseEntity<Object> deleteBuilding(@PathVariable String ids) {
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Long::valueOf)
                .toList();
        buildingService.delete(idList);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Successfully deleted Building");
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PostMapping("assign")
    public ResponseEntity<Object> assignBuilding(@RequestBody @Valid BuildingAssignedRequestDTO dto, BindingResult bindingResult) {
        ResponseDTO responseDTO = new ResponseDTO();
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            responseDTO.setMessage("Failed to assign Building");
            responseDTO.setDetail(errorMessages);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
        }
        buildingService.assignBuilding(dto);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}
