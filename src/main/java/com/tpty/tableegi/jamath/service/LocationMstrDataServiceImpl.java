package com.tpty.tableegi.jamath.service;

import com.tpty.tableegi.jamath.entity.AddMasjidDataEntity;
import com.tpty.tableegi.jamath.entity.LocationDataEntity;
import com.tpty.tableegi.jamath.exceptions.NoMasjidFoundException;
import com.tpty.tableegi.jamath.repo.AddMasjidRepo;
import com.tpty.tableegi.jamath.repo.LocationMstrDataRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LocationMstrDataServiceImpl implements LocationMstrDataService {

    public LocationMstrDataRepo locationMstrDataRepo;

    public LocationMstrDataServiceImpl(LocationMstrDataRepo locationMstrDataRepo) {
        this.locationMstrDataRepo = locationMstrDataRepo;
    }

    @Override
    public List<LocationDataEntity> findAllLocations() {

        List<LocationDataEntity> locationDataEntityList = locationMstrDataRepo.findAll();
        if (locationDataEntityList.isEmpty())
            throw new NoMasjidFoundException("No Locations are added");

        return locationDataEntityList.stream().sorted(Comparator.comparing(LocationDataEntity::getSequenceNo)).collect(Collectors.toList());
    }

    @Override
    public LocationDataEntity findByLocationId(String locationId) {

        Optional<LocationDataEntity> byId = locationMstrDataRepo.findById(UUID.fromString(locationId));
        return byId.orElse(null);
    }
}
