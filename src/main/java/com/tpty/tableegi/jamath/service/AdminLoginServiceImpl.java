package com.tpty.tableegi.jamath.service;

import com.tpty.tableegi.jamath.dto.AdminLoginRequestDto;
import com.tpty.tableegi.jamath.entity.AdminLoginEntity;
import com.tpty.tableegi.jamath.exceptions.InvalidDataException;
import com.tpty.tableegi.jamath.repo.AdminLoginRepo;
import com.tpty.tableegi.jamath.utils.AdminDtoConverterToEntity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class AdminLoginServiceImpl implements AdminLoginService {

    private AdminLoginRepo adminLoginRepo;
    private PasswordEncoder passwordEncoder;

    public AdminLoginServiceImpl(AdminLoginRepo adminLoginRepo, PasswordEncoder passwordEncoder) {
        this.adminLoginRepo = adminLoginRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AdminLoginEntity addAdmin(AdminLoginRequestDto adminDto) throws InvalidDataException {
        // Convert back
        AdminLoginEntity adminLoginEntity = AdminDtoConverterToEntity.toEntity(adminDto);

        //check already Id is available & user id & user details checking with database
        if (Objects.nonNull(adminLoginEntity.getId())) {
            AdminLoginEntity existEntity = findById(adminLoginEntity.getId());
            if (Objects.nonNull(existEntity) && existEntity.getUsername().equalsIgnoreCase(adminDto.getUsername()) &&
                    existEntity.getPassword().equalsIgnoreCase(adminDto.getPassword())) {
                throw new InvalidDataException("Username and Password are exist");
            }
            throw new InvalidDataException("User Id and User-details are not matching");

        } else {
            AdminLoginEntity existEntity = findByUsername(adminDto.getUsername());
            //No Duplicate Usernames are allowed
            if (Objects.nonNull(existEntity)) {
                throw new InvalidDataException("Username are exist. No Duplicates allowed..");
            }
        }

        // This method saves data only when new user means Id is null

        adminLoginEntity.setPassword(passwordEncoder.encode(adminDto.getPassword()));
        log.debug("addAdmin {}", adminLoginEntity);
        return adminLoginRepo.save(adminLoginEntity);
    }

    @Override
    public AdminLoginEntity findByUsername(String username) {
        return adminLoginRepo.findByUsername(username);
    }

    @Override
    public AdminLoginEntity findById(UUID uuid) {
        return adminLoginRepo.findById(uuid).orElse(null);
    }

    @Override
    public AdminLoginEntity updateAdmin(AdminLoginRequestDto admin) throws InvalidDataException {
        AdminLoginEntity existingEntity = validateAdmin(admin);
        AdminDtoConverterToEntity.updateEntity(existingEntity, admin);
        existingEntity.setPassword(passwordEncoder.encode(admin.getPassword()));
        return adminLoginRepo.save(existingEntity);
    }


    @Override
    public void deleteAdmin(AdminLoginRequestDto admin) throws InvalidDataException {
        AdminLoginEntity existingEntity = validateAdmin(admin);
        AdminDtoConverterToEntity.updateEntity(existingEntity, admin);
        adminLoginRepo.delete(existingEntity);
    }

    @Override
    public List<AdminLoginEntity> getAllAdmins() throws InvalidDataException {
        List<AdminLoginEntity> admins = adminLoginRepo.findAll();
        if (admins.isEmpty()) {
            throw new InvalidDataException("No admin login data available");
        }
        return admins;
    }

    @Override
    public AdminLoginEntity getAdmin(String admin) throws InvalidDataException {
        AdminLoginEntity adminEntity = findByUsername(admin);
        if (Objects.isNull(adminEntity))
            adminEntity = findById(UUID.fromString(admin));

        if (Objects.isNull(adminEntity)) {
            throw new InvalidDataException("Admin data is not exist in database=" + admin);
        }
        return adminEntity;

    }

    private AdminLoginEntity validateAdmin(AdminLoginRequestDto admin) throws InvalidDataException {
        AdminLoginEntity existingEntity = findByUsername(admin.getUsername());
        if (Objects.isNull(existingEntity)) {
            existingEntity = findById(UUID.fromString(admin.getId()));
        }

        if (Objects.isNull(existingEntity)) {
            throw new InvalidDataException("Admin Id is not exist from database=" + admin.getUsername());
        }
        return existingEntity;
    }
}
