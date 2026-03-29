package com.tpty.tableegi.jamath.service;

import com.tpty.tableegi.jamath.dto.AddMasjidRequestDto;
import com.tpty.tableegi.jamath.entity.AddMasjidDataEntity;
import com.tpty.tableegi.jamath.entity.AdminLoginEntity;
import com.tpty.tableegi.jamath.exceptions.InvalidDataException;
import com.tpty.tableegi.jamath.exceptions.NoMasjidFoundException;
import com.tpty.tableegi.jamath.repo.AddMasjidRepo;
import com.tpty.tableegi.jamath.utils.AddMasjidDtoConverterToEntity;
import com.tpty.tableegi.jamath.utils.AdminDtoConverterToEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AddMasjidDataServiceImpl implements AddMasjidDataService {

    public AddMasjidRepo addMasjidRepo;

    public AddMasjidDataServiceImpl(AddMasjidRepo addMasjidRepo) {
        this.addMasjidRepo = addMasjidRepo;
    }

    @Override
    public AddMasjidDataEntity addMasjid(AddMasjidRequestDto masjidRequestDto, String action) {

        // Convert back
        AddMasjidDataEntity addMasjidDataEntity = null;


        if (action != null) {
            addMasjidDataEntity = findById(UUID.fromString(masjidRequestDto.getId()));
            addMasjidDataEntity.setLastUpdateDate(LocalDateTime.now());
            addMasjidDataEntity.setName(masjidRequestDto.getMasjidName());
            addMasjidDataEntity.setAddress(masjidRequestDto.getAddress());
            addMasjidDataEntity.setHalkaNo(masjidRequestDto.getHalkaNo());
            addMasjidDataEntity.setUsrAdminId(UUID.fromString(masjidRequestDto.getUsrAdminId()));
        } else {
            addMasjidDataEntity = AddMasjidDtoConverterToEntity.toEntity(masjidRequestDto);
        }
        log.debug("Data:" + addMasjidDataEntity);
        return addMasjidRepo.save(addMasjidDataEntity);
    }

    @Override
    public AddMasjidDataEntity findById(UUID uuid) {
        return addMasjidRepo.findById(uuid).orElse(null);
    }

    @Override
    public List<AddMasjidDataEntity> getAllMasjids(String locationId) throws NoMasjidFoundException {
        List<AddMasjidDataEntity> addMasjidList = addMasjidRepo.findMasjidsByLocationId(UUID.fromString(locationId));
        if (addMasjidList.isEmpty())
            throw new NoMasjidFoundException("No Masjids are added");

        return addMasjidList.stream().sorted(Comparator.comparing(AddMasjidDataEntity::getHalkaNo)).collect(Collectors.toList());
    }

    @Override
    public void deleteMasjid(AddMasjidRequestDto masjidRequestDto, String action) throws NoMasjidFoundException {
        // Convert back
        AddMasjidDataEntity addMasjidDataEntity = null;

        addMasjidDataEntity = findById(UUID.fromString(masjidRequestDto.getId()));

        if (Objects.nonNull(addMasjidDataEntity) && addMasjidDataEntity.getName().equalsIgnoreCase(masjidRequestDto.getMasjidName())) {
            addMasjidRepo.delete(addMasjidDataEntity);
        } else {
            throw new NoMasjidFoundException("Masjid Id & Masjid name are not matching...");
        }
    }
}
