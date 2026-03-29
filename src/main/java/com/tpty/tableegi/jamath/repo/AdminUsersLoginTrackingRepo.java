package com.tpty.tableegi.jamath.repo;

import com.tpty.tableegi.jamath.entity.AdminUsersLoginTrackingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface AdminUsersLoginTrackingRepo extends JpaRepository<AdminUsersLoginTrackingEntity, UUID> {

    @Query("SELECT a FROM AdminUsersLoginTrackingEntity a WHERE a.usrAdminId = :usrAdminId and a.isAdminLogin = :isAdminLogin")
    public AdminUsersLoginTrackingEntity findByAdminId(@Param("usrAdminId") UUID usrAdminId, boolean isAdminLogin);

    @Query("SELECT a FROM AdminUsersLoginTrackingEntity a WHERE a.lastLoginDate < :expiry and a.isAdminLogin = true")
    public List<AdminUsersLoginTrackingEntity> findExpiredAdmins(@Param("expiry") LocalDateTime expiry);

    @Query("SELECT a FROM AdminUsersLoginTrackingEntity a WHERE a.createDate < :expiry  and a.id=:id and a.sessionId=:sessionId and a.usrAdminId=:usrAdminId ")
    public AdminUsersLoginTrackingEntity findExpiredAdmin(@Param("expiry") LocalDateTime expiry, @Param("id") UUID id,
                                                          @Param("sessionId") UUID sessionId, @Param("usrAdminId") UUID usrAdminId);
}
