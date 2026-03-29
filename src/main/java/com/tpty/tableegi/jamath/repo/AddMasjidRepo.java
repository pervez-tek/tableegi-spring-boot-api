package com.tpty.tableegi.jamath.repo;

import com.tpty.tableegi.jamath.entity.AddMasjidDataEntity;
import com.tpty.tableegi.jamath.entity.AdminLoginEntity;
import com.tpty.tableegi.jamath.entity.AdminUsersLoginTrackingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface AddMasjidRepo extends JpaRepository<AddMasjidDataEntity, UUID> {


    @Query("SELECT a FROM AddMasjidDataEntity a WHERE a.locationId = :locationId ")
    public List<AddMasjidDataEntity> findMasjidsByLocationId(@Param("locationId") UUID locationId);
}
