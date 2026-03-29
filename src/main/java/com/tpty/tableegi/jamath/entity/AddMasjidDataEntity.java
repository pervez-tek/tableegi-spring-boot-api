package com.tpty.tableegi.jamath.entity;

import jakarta.persistence.*;
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
@Table(name = "masjids_mstr_tbl",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "address","locationId"})})
// you can change table name
@EntityListeners(AuditingEntityListener.class) // ✅ enables auditing hooks
@Data                   // Lombok: generates getters, setters, toString, equals, hashCode
@NoArgsConstructor      // Lombok: generates no-args constructor
@AllArgsConstructor     // Lombok: generates all-args constructor
@Builder                // Lombok: enables builder pattern
public class AddMasjidDataEntity implements Serializable {

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

    @Column(name = "halkaNo")
    private int halkaNo;

    @Column(name = "address")
    private String address;

    // ✅ Just a foreign key column, no entity reference
    @Column(name = "usr_admin_id")
    private UUID usrAdminId;

    // ✅ Just a foreign key column, no entity reference
    @Column(name = "location_id")
    private UUID locationId;


    @OneToMany
    @JoinColumn(name = "masjid_id") // foreign key in namazi_mstr_table
    private List<JamathMasterSurveyEntity> namazisMstr = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "masjid_id") // foreign key in namazi_mstr_table
    private List<JamathActivityLogSurveyEntity> namazisRepo = new ArrayList<>();

}
