package com.tpty.tableegi.jamath.repo;

import com.tpty.tableegi.jamath.entity.AdminLoginEntity;
import com.tpty.tableegi.jamath.entity.BroadCastMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BroadCastRepo  extends JpaRepository<BroadCastMessageEntity, UUID> {
}
