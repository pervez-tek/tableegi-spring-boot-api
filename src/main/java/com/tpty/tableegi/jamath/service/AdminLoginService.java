package com.tpty.tableegi.jamath.service;

import com.tpty.tableegi.jamath.dto.AdminLoginRequestDto;
import com.tpty.tableegi.jamath.entity.AdminLoginEntity;
import com.tpty.tableegi.jamath.exceptions.InvalidDataException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.UUID;


public interface AdminLoginService {

    AdminLoginEntity addAdmin(AdminLoginRequestDto admin) throws InvalidDataException;

    AdminLoginEntity findByUsername(String username);

    AdminLoginEntity findById(UUID uuid);

    AdminLoginEntity updateAdmin(AdminLoginRequestDto admin) throws InvalidDataException;

    void deleteAdmin(AdminLoginRequestDto admin) throws InvalidDataException;

    List<AdminLoginEntity> getAllAdmins() throws InvalidDataException;

    AdminLoginEntity getAdmin(String admin) throws InvalidDataException;

}
