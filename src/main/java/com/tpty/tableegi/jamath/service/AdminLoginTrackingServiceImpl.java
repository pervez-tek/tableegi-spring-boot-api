package com.tpty.tableegi.jamath.service;

import com.tpty.tableegi.jamath.components.ActiveUsersStore;
import com.tpty.tableegi.jamath.dto.AdminLoginRequestDto;
import com.tpty.tableegi.jamath.entity.AdminLoginEntity;
import com.tpty.tableegi.jamath.entity.AdminUsersLoginTrackingEntity;
import com.tpty.tableegi.jamath.exceptions.InvalidDataException;
import com.tpty.tableegi.jamath.repo.AdminLoginRepo;
import com.tpty.tableegi.jamath.repo.AdminUsersLoginTrackingRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class AdminLoginTrackingServiceImpl implements AdminLoginTrackingService {

    private AdminLoginRepo adminLoginRepo;
    private PasswordEncoder passwordEncoder;
    private AdminUsersLoginTrackingRepo adminUsersLoginTrackingRepo;
    private ActiveUsersStore activeUsersStore;

    public AdminLoginTrackingServiceImpl(AdminLoginRepo adminLoginRepo, PasswordEncoder passwordEncoder, AdminUsersLoginTrackingRepo adminUsersLoginTrackingRepo, ActiveUsersStore activeUsersStore) {
        this.adminLoginRepo = adminLoginRepo;
        this.passwordEncoder = passwordEncoder;
        this.adminUsersLoginTrackingRepo = adminUsersLoginTrackingRepo;
        this.activeUsersStore = activeUsersStore;
    }

    @Override
    public AdminUsersLoginTrackingEntity signIn(AdminLoginRequestDto adminDto, UUID sessionId, HttpServletRequest request) throws InvalidDataException {

        AdminLoginEntity existEntity = adminLoginRepo.findByUsername(adminDto.getUsername());
        if (Objects.nonNull(existEntity) && passwordEncoder.matches(adminDto.getPassword(), existEntity.getPassword())) {
            AdminUsersLoginTrackingEntity trackerEntity = adminUsersLoginTrackingRepo.findByAdminId(existEntity.getId(), true);
            if (!activeUsersStore.isUserLoggedIn(adminDto.getUsername())) {
                sessionId = UUID.randomUUID();
            }
            if (Objects.nonNull(trackerEntity)) {
                throw new InvalidDataException("User already signed in...");
            }
            if (sessionId == null) {
                throw new InvalidDataException("Session id is missing...");
            }
            trackerEntity = AdminUsersLoginTrackingEntity.builder().
                    usrAdminId(existEntity.getId() != null ? existEntity.getId() : null).
                    isSuperAdmin(existEntity.isSuperAdmin()).
                    //.usrAdminId(dto.getUsrAdminId() != null && !dto.getUsrAdminId().isEmpty() ? UUID.fromString(dto.getUsrAdminId()) : null)
                    locationId(existEntity.getLocationId().toString()).
                    sessionId(sessionId).isAdminLogin(true).build();
            activeUsersStore.login(adminDto.getUsername(), sessionId);
            return adminUsersLoginTrackingRepo.save(trackerEntity);
        }
        return null;
    }

    @Override
    public AdminUsersLoginTrackingEntity signOut(AdminLoginRequestDto adminDto) throws InvalidDataException {
        AdminUsersLoginTrackingEntity trackerEntity = null;
        if (activeUsersStore.isUserLoggedIn(adminDto.getUsername())) {

            trackerEntity = adminUsersLoginTrackingRepo.findById(UUID.fromString(adminDto.getId())).
                    orElseThrow(() -> new InvalidDataException("Tracker entity not found for id: " + adminDto.getId()));
            trackerEntity.setAdminLogin(false);
            trackerEntity.setLastLoginDate(LocalDateTime.now());
            activeUsersStore.logout(adminDto.getUsername());
            return adminUsersLoginTrackingRepo.save(trackerEntity);
        } else if (Objects.nonNull(adminDto.getId())) {
            trackerEntity = adminUsersLoginTrackingRepo.findById(UUID.fromString(adminDto.getId())).
                    orElseThrow(() -> new InvalidDataException("Tracker entity not found for id: " + adminDto.getId()));
            trackerEntity.setAdminLogin(false);
            trackerEntity.setLastLoginDate(LocalDateTime.now());
            return adminUsersLoginTrackingRepo.save(trackerEntity);
        }

        return null;
    }

    @Override
    public List<AdminUsersLoginTrackingEntity> findExpiredSessions(LocalDateTime expiry) throws InvalidDataException {

        return adminUsersLoginTrackingRepo.findExpiredAdmins(expiry);
    }

    @Override
    public AdminUsersLoginTrackingEntity findExpiredAdmin(LocalDateTime expiry, UUID id, UUID sessionId, UUID usrAdminId) {
        return adminUsersLoginTrackingRepo.findExpiredAdmin(expiry, id, sessionId, usrAdminId);
    }
}
