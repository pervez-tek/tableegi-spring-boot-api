package com.tpty.tableegi.jamath.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tpty.tableegi.jamath.dto.AddMasjidRequestDto;
import com.tpty.tableegi.jamath.entity.AddMasjidDataEntity;
import com.tpty.tableegi.jamath.exceptions.InvalidDataException;
import com.tpty.tableegi.jamath.exceptions.NoMasjidFoundException;
import com.tpty.tableegi.jamath.service.AddMasjidDataService;
import com.tpty.tableegi.jamath.utils.AddMasjidDtoConverterToEntity;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tableegi")
@CrossOrigin(origins = "${front.end.react.api.url}") // allow React dev server
@Slf4j
public class AddMasjidController {

    private AddMasjidDataService addMasjidDataService;

    public AddMasjidController(AddMasjidDataService addMasjidDataService) {
        this.addMasjidDataService = addMasjidDataService;
    }

    @PostMapping("/addMasjid")
    public ResponseEntity<Object> addMasjid(@Valid @RequestBody JsonNode body) throws InvalidDataException {
        log.info("Add Masjid Started");
        ObjectMapper mapper = new ObjectMapper();
        List<AddMasjidRequestDto> masjidDtoList = null;
        if (body.isArray()) {
            // ✅ Request body is a JSON array
            masjidDtoList = mapper.convertValue(body, new TypeReference<>() {
            });
            masjidDtoList.forEach(masjidRequestDto -> {
                log.debug("Add=" + masjidRequestDto);
                try {
                    validateMasjidAdminUserIsAvl(masjidRequestDto);
                } catch (InvalidDataException e) {
                    throw new RuntimeException(e);
                }
                addMasjidDataService.addMasjid(masjidRequestDto, null);
            });
            log.info("Add Masjid Completed:" + masjidDtoList.size() + " items");
            return ResponseEntity.ok("Received list with " + masjidDtoList.size() + " items");
        } else {
            // ✅ Request body is a single JSON object
            AddMasjidRequestDto masjidRequestDto = mapper.convertValue(body, AddMasjidRequestDto.class);
            log.debug("Add=" + masjidRequestDto);
            validateMasjidAdminUserIsAvl(masjidRequestDto);
            AddMasjidDataEntity addMasjidDataEntity = addMasjidDataService.addMasjid(masjidRequestDto, null);
            AddMasjidRequestDto masjidResponseDto = AddMasjidDtoConverterToEntity.toDTO(addMasjidDataEntity);
            masjidResponseDto.setUsername(masjidRequestDto.getUsername());
            masjidResponseDto.setAdminUsrTrackingId(masjidRequestDto.getAdminUsrTrackingId());
            masjidResponseDto.setUsrAdminId(masjidRequestDto.getUsrAdminId());
            masjidResponseDto.setLocationId(masjidRequestDto.getLocationId());
            masjidResponseDto.setSessionid(masjidRequestDto.getSessionid());
            log.info("Add Masjid Completed by =" + addMasjidDataEntity.getUsrAdminId());
            return ResponseEntity.ok(masjidResponseDto);
        }


    }

    private void validateMasjidAdminUserIsAvl(AddMasjidRequestDto masjidRequestDto) throws InvalidDataException {
        if (masjidRequestDto.getUsrAdminId() == null || masjidRequestDto.getUsrAdminId().trim().equalsIgnoreCase("")) {
            throw new InvalidDataException("Admin User only has to add Masjid");
        }
        if (masjidRequestDto.getLocationId() == null || masjidRequestDto.getLocationId().trim().equalsIgnoreCase("")) {
            throw new InvalidDataException("Admin User only has to add Masjid with valid Location");
        }
    }

    @PutMapping("/updateMasjid")
    public ResponseEntity<Object> updateMasjid(@Valid @RequestBody JsonNode body) throws InvalidDataException {
        log.info("Update Masjid Started");
        ObjectMapper mapper = new ObjectMapper();
        List<AddMasjidRequestDto> masjidDtoList = null;
        if (body.isArray()) {
            // ✅ Request body is a JSON array
            masjidDtoList = mapper.convertValue(body, new TypeReference<>() {
            });
            masjidDtoList.forEach(masjidRequestDto -> {
                log.debug("Update=" + masjidRequestDto);
                try {
                    validateMasjidAdminUserIsAvl(masjidRequestDto);
                } catch (InvalidDataException e) {
                    throw new RuntimeException(e);
                }
                addMasjidDataService.addMasjid(masjidRequestDto, "Update");
            });
            log.info("Update Masjid Completed:" + masjidDtoList.size() + " items");
            return ResponseEntity.ok("Received list with " + masjidDtoList.size() + " items");
        } else {
            // ✅ Request body is a single JSON object
            AddMasjidRequestDto masjidRequestDto = mapper.convertValue(body, AddMasjidRequestDto.class);
            log.debug("Update=" + masjidRequestDto);
            validateMasjidAdminUserIsAvl(masjidRequestDto);
            AddMasjidDataEntity addMasjidDataEntity = addMasjidDataService.addMasjid(masjidRequestDto, "Update");
            AddMasjidRequestDto masjidResponseDto = AddMasjidDtoConverterToEntity.toDTO(addMasjidDataEntity);
            masjidResponseDto.setUsername(masjidRequestDto.getUsername());
            masjidResponseDto.setAdminUsrTrackingId(masjidRequestDto.getAdminUsrTrackingId());
            masjidResponseDto.setUsrAdminId(masjidRequestDto.getUsrAdminId());
            masjidResponseDto.setLocationId(masjidRequestDto.getLocationId());
            masjidResponseDto.setSessionid(masjidRequestDto.getSessionid());
            log.info("Update Masjid Completed by =" + addMasjidDataEntity.getUsrAdminId());
            return ResponseEntity.ok(masjidResponseDto);
        }

    }

    @GetMapping("/getAllMasjids")
    public ResponseEntity<List<AddMasjidRequestDto>> getAllMasjids(@RequestHeader(value = "locationId", required = false) String locationId) throws InvalidDataException {
        log.info("Get All Masjid Started ");
        List<AddMasjidDataEntity> masjidDataEntityList = addMasjidDataService.getAllMasjids(locationId);
        if (masjidDataEntityList.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        log.info("Get All Masjid Completed= " + masjidDataEntityList.size());
        return ResponseEntity.ok(AddMasjidDtoConverterToEntity.toDTOList(masjidDataEntityList));

    }

    @GetMapping("/getHalkaWiseAllMasjids")
    public ResponseEntity<Map<Integer, List<AddMasjidRequestDto>>> getHalkaWiseAllMasjids(@RequestHeader(value = "locationId", required = false) String locationId) throws NoMasjidFoundException {
        log.info("Get All Halka Wise Map Masjid Started ");
        List<AddMasjidDataEntity> masjidDataEntityList = addMasjidDataService.getAllMasjids(locationId);

        List<AddMasjidRequestDto> masjidDtoList = AddMasjidDtoConverterToEntity.toDTOList(masjidDataEntityList);
        Map<Integer, List<AddMasjidRequestDto>> mapData = masjidDtoList.stream().collect(Collectors.groupingBy(AddMasjidRequestDto::getHalkaNo, () -> new TreeMap<>((a, b) -> {
            if (a == 0 && b == 0) return 0;
            if (a == 0) return 1;   // push 0 after others
            if (b == 0) return -1;
            return Integer.compare(a, b);
        }), Collectors.toList()));
        if (mapData.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        log.info("Get All Halka Wise Map Masjid Completed= " + mapData.size());
        return ResponseEntity.ok(mapData);
    }

    @DeleteMapping("/deleteMasjid")
    public ResponseEntity<Object> deleteMasjid(@Valid @RequestBody JsonNode body) throws NoMasjidFoundException, InvalidDataException {
        log.info("Delete Masjid Started ");
        ObjectMapper mapper = new ObjectMapper();
        List<AddMasjidRequestDto> masjidDtoList = null;
        if (body.isArray()) {
            // ✅ Request body is a JSON array
            masjidDtoList = mapper.convertValue(body, new TypeReference<>() {
            });
            masjidDtoList.forEach(masjidRequestDto -> {
                log.debug("Delete=" + masjidRequestDto);
                try {
                    validateMasjidAdminUserIsAvl(masjidRequestDto);
                    addMasjidDataService.deleteMasjid(masjidRequestDto, "Delete");
                } catch (NoMasjidFoundException e) {
                    throw new RuntimeException(e);
                } catch (InvalidDataException e) {
                    throw new RuntimeException(e);
                }
            });
            log.info("Delete Masjid Completed = " + masjidDtoList.size() + " items");
            return ResponseEntity.ok("Received list with " + masjidDtoList.size() + " items");
        } else {
            // ✅ Request body is a single JSON object
            AddMasjidRequestDto masjidRequestDto = mapper.convertValue(body, AddMasjidRequestDto.class);
            log.debug("Delete=" + masjidRequestDto);
            validateMasjidAdminUserIsAvl(masjidRequestDto);
            addMasjidDataService.deleteMasjid(masjidRequestDto, "Delete");
            log.info("Delete Masjid Completed by= " + masjidRequestDto.getUsrAdminId());
            return ResponseEntity.ok("Masjid deleted successfully");
        }

    }
}
