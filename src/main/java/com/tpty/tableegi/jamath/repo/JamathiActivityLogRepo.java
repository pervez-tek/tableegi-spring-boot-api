package com.tpty.tableegi.jamath.repo;

import com.tpty.tableegi.jamath.entity.JamathActivityLogSurveyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JamathiActivityLogRepo extends JpaRepository<JamathActivityLogSurveyEntity, UUID> {

    Optional<JamathActivityLogSurveyEntity> findByNamaziIdAndActivityDate(UUID namaziId, LocalDate activityDate);

    @Query("""
                SELECT mas.halkaNo,mas.name, m, a
                FROM AddMasjidDataEntity mas
                JOIN JamathMasterSurveyEntity m
                    ON mas.id = m.masjidId
                JOIN JamathActivityLogSurveyEntity a
                    ON m.id = a.namaziId
                WHERE m.lastUpdateDate >= :fromDate
                  AND m.lastUpdateDate < :toDate
                  AND a.lastUpdateDate >= :fromDate
                  AND a.lastUpdateDate < :toDate
            """)
    List<Object[]> findJamathiDataBetweenDatesRange(@Param("fromDate") LocalDateTime fromDate, @Param("toDate") LocalDateTime toDate);

}
