package com.tpty.tableegi.jamath.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "jamathi_activity_log_tbl")   // you can change table name
@EntityListeners(AuditingEntityListener.class) // ✅ enables auditing hooks
@Data                   // Lombok: generates getters, setters, toString, equals, hashCode
@NoArgsConstructor      // Lombok: generates no-args constructor
@AllArgsConstructor     // Lombok: generates all-args constructor
@Builder
public class JamathActivityLogSurveyEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // ✅ modern way
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @LastModifiedDate
    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;

    // ✅ Just a foreign key column, no entity reference
    @Column(name = "masjid_id")
    private UUID masjidId;

    // ✅ Just a foreign key column, no entity reference
    @Column(name = "namazi_id")
    private UUID namaziId;

    @Column(name = "comments", length = 300)
    @Size(max = 300, message = "Message cannot exceed 300 characters")
    private String comments;

    @CreatedDate
    @Column(name = "activity_date")
    private LocalDate activityDate;

}
