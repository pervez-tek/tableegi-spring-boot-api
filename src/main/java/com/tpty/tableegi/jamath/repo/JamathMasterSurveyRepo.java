package com.tpty.tableegi.jamath.repo;

import com.tpty.tableegi.jamath.entity.JamathMasterSurveyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JamathMasterSurveyRepo extends JpaRepository<JamathMasterSurveyEntity, UUID> {

    Optional<JamathMasterSurveyEntity> findByEmail(String email);

}
