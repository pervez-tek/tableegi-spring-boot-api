package com.tpty.tableegi.jamath.controller;

import com.tpty.tableegi.jamath.dto.AdminLoginRequestDto;
import com.tpty.tableegi.jamath.dto.AdminLoginResponseDto;
import com.tpty.tableegi.jamath.entity.AdminLoginEntity;
import com.tpty.tableegi.jamath.entity.AdminUsersLoginTrackingEntity;
import com.tpty.tableegi.jamath.exceptions.InvalidDataException;
import com.tpty.tableegi.jamath.service.AdminLoginService;
import com.tpty.tableegi.jamath.service.AdminLoginTrackingService;
import com.tpty.tableegi.jamath.utils.AdminDtoConverterToEntity;
import com.tpty.tableegi.jamath.utils.AdminRequestDtoConverterToResponse;
import com.tpty.tableegi.jamath.utils.AdminUsersLoginTrackingDtoConverterToEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/tableegi")
@CrossOrigin(origins = "${front.end.react.api.url}") // allow React dev server
@Slf4j
public class AuthenticationController {

    private AdminLoginTrackingService adminLoginTrackingService;

    private AuthenticationController(AdminLoginTrackingService adminLoginTrackingService) {
        this.adminLoginTrackingService = adminLoginTrackingService;
    }

    @PostMapping("/signIn")
    public ResponseEntity<AdminLoginRequestDto> signIn(@RequestBody AdminLoginRequestDto adminLoginDto, HttpServletRequest request) throws InvalidDataException {
        log.info("Admin Sign-in started" + adminLoginDto);
        AdminUsersLoginTrackingEntity adminLoginEntity = adminLoginTrackingService.signIn(adminLoginDto, adminLoginDto.getSessionid(), request);

        String userName = adminLoginDto.getUsername();
        adminLoginDto = AdminUsersLoginTrackingDtoConverterToEntity.toDTO(adminLoginEntity);
        if (Objects.isNull(adminLoginDto)) {
            throw new InvalidDataException("Username & Password is not matching");
        }
        adminLoginDto.setUsername(userName);
        log.info("Admin Sign-in Completed By=" + adminLoginDto);
        return ResponseEntity.ok(adminLoginDto);
    }

    @PostMapping("/signOut")
    public ResponseEntity<AdminLoginRequestDto> signOut(@RequestBody AdminLoginRequestDto adminLoginDto) throws InvalidDataException {
        log.info("Admin Sign-out started" + adminLoginDto);
        if (Objects.isNull(adminLoginDto.getSessionid())) {
            throw new InvalidDataException("Session-Id is required");
        }

        AdminUsersLoginTrackingEntity adminLoginEntity = adminLoginTrackingService.signOut(adminLoginDto);
        adminLoginDto = AdminUsersLoginTrackingDtoConverterToEntity.toDTO(adminLoginEntity);
        log.info("Admin Sign-out Completed By=" + adminLoginDto.getUsername());
        return ResponseEntity.ok(adminLoginDto);
    }
}
