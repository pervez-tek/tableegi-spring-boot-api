package com.tpty.tableegi.jamath.controller;

import com.tpty.tableegi.jamath.dto.AddMasjidRequestDto;
import com.tpty.tableegi.jamath.dto.LocationsMstrRequestDto;
import com.tpty.tableegi.jamath.entity.AddMasjidDataEntity;
import com.tpty.tableegi.jamath.entity.LocationDataEntity;
import com.tpty.tableegi.jamath.exceptions.InvalidDataException;
import com.tpty.tableegi.jamath.service.AddMasjidDataService;
import com.tpty.tableegi.jamath.service.LocationMstrDataService;
import com.tpty.tableegi.jamath.utils.AddMasjidDtoConverterToEntity;
import com.tpty.tableegi.jamath.utils.LocationsMstrDtoConverterToEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tableegi")
@CrossOrigin(origins = "${front.end.react.api.url}") // allow React dev server
@Slf4j
public class LocationsController {

    private LocationMstrDataService locationMstrDataService;

    public LocationsController(LocationMstrDataService locationMstrDataService) {
        this.locationMstrDataService = locationMstrDataService;
    }

    @GetMapping("/getAllLocations")
    public ResponseEntity<List<LocationsMstrRequestDto>> getAllLocations() throws InvalidDataException {
        log.info("Get All Locations Started ");
        List<LocationDataEntity> masjidDataEntityList = locationMstrDataService.findAllLocations();
        if (masjidDataEntityList.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        log.info("Get All Locations Completed= " + masjidDataEntityList.size());
        return ResponseEntity.ok(LocationsMstrDtoConverterToEntity.toDTOList(masjidDataEntityList));

    }
}
