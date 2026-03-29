package com.tpty.tableegi.jamath.service;

import com.tpty.tableegi.jamath.entity.LocationDataEntity;

import java.util.List;

public interface LocationMstrDataService {

    public List<LocationDataEntity> findAllLocations();

    public LocationDataEntity findByLocationId(String locationId);
}


