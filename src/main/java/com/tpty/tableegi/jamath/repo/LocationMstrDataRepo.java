package com.tpty.tableegi.jamath.repo;

import com.tpty.tableegi.jamath.entity.LocationDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LocationMstrDataRepo extends JpaRepository<LocationDataEntity, UUID> {
}
