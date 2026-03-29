package com.tpty.tableegi.jamath.service;

import com.tpty.tableegi.jamath.dto.AdminLoginRequestDto;
import com.tpty.tableegi.jamath.entity.AdminUsersLoginTrackingEntity;
import com.tpty.tableegi.jamath.exceptions.InvalidDataException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AdminLoginTrackingService {

    public AdminUsersLoginTrackingEntity signIn(AdminLoginRequestDto adminDto,
                                                UUID sessionId, HttpServletRequest request) throws InvalidDataException;

    public AdminUsersLoginTrackingEntity signOut(AdminLoginRequestDto adminDto) throws InvalidDataException;

    public List<AdminUsersLoginTrackingEntity> findExpiredSessions(LocalDateTime expiry) throws InvalidDataException;

    public AdminUsersLoginTrackingEntity findExpiredAdmin(LocalDateTime expiry, UUID id,
                                                          UUID sessionId, UUID usrAdminId);
}
