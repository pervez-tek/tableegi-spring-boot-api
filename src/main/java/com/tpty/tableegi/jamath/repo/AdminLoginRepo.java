package com.tpty.tableegi.jamath.repo;

import com.tpty.tableegi.jamath.entity.AdminLoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AdminLoginRepo extends JpaRepository<AdminLoginEntity, UUID> {

    public AdminLoginEntity findByUsername(String username);

//    @Query("SELECT a FROM AdminLoginEntity a WHERE a.sessionId = :sessionId")
//    public AdminLoginEntity findBySessionId(@Param("sessionId") String sessioId);

}
