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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "jamathi_survey_master_tbl")   // you can change table name
@EntityListeners(AuditingEntityListener.class) // ✅ enables auditing hooks
@Data                   // Lombok: generates getters, setters, toString, equals, hashCode
@NoArgsConstructor      // Lombok: generates no-args constructor
@AllArgsConstructor     // Lombok: generates all-args constructor
@Builder                // Lombok: enables builder pattern
public class JamathMasterSurveyEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // ✅ modern way
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    // ✅ Audit fields
    @CreatedDate
    @Column(name = "create_date", nullable = false, updatable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;

    // ✅ Optimistic locking
    @Version
    @Column(name = "version")
    private Long version;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "service")
    private String service;

    @Column(name = "phone")
    private String phone;

    @Column(name = "gender", length = 1)
    private char gender;

    @Column(name = "age")
    private int age;


    @Column(name = "jamath")
    private String jamath;

    @Column(name = "_5aamal")
    private String _5aamal;

    @Column(name = "comment", length = 300)
    @Size(max = 300, message = "Message cannot exceed 300 characters")
    private String comment;

    @Column(name = "image")
    private String image;

    @Column(name = "agreeTerms")
    private boolean agreeTerms;

    // ✅ Just a foreign key column, no entity reference
    @Column(name = "masjid_id")
    private UUID masjidId;

    @OneToMany
    @JoinColumn(name = "namazi_id") // foreign key in namazi_mstr_table
    private List<JamathActivityLogSurveyEntity> namazisActivityLog = new ArrayList<>();

    // ✅ Just a foreign key column, no entity reference
    @Column(name = "location_id")
    private UUID locationId;

}
