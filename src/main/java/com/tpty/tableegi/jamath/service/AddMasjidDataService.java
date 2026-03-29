package com.tpty.tableegi.jamath.service;

import com.tpty.tableegi.jamath.dto.AddMasjidRequestDto;
import com.tpty.tableegi.jamath.entity.AddMasjidDataEntity;
import com.tpty.tableegi.jamath.exceptions.InvalidDataException;
import com.tpty.tableegi.jamath.exceptions.NoMasjidFoundException;

import java.util.List;
import java.util.UUID;

public interface AddMasjidDataService {

    public AddMasjidDataEntity addMasjid(AddMasjidRequestDto masjidRequestDto,String action);

    public AddMasjidDataEntity findById(UUID uuuid);

     public List<AddMasjidDataEntity> getAllMasjids(String locationId) throws NoMasjidFoundException;

    public void deleteMasjid(AddMasjidRequestDto masjidRequestDto,String action) throws NoMasjidFoundException;;
}
