package com.tpty.tableegi.jamath.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "locations_mstr_tbl",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "state", "country"})})
// you can change table name
@EntityListeners(AuditingEntityListener.class) // ✅ enables auditing hooks
@Data                   // Lombok: generates getters, setters, toString, equals, hashCode
@NoArgsConstructor      // Lombok: generates no-args constructor
@AllArgsConstructor     // Lombok: generates all-args constructor
@Builder                // Lombok: enables builder pattern
public class LocationDataEntity {

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

    @Column(nullable = false)
    private String name;

    private String state;

    private String country;

    @Column(nullable = false)
    private Double lat;

    @Column(nullable = false)
    private Double lon;

    @Column(name = "short_name")
    private String shortName;

    private Integer halka;

    @Column(name = "sno")
    private Integer sequenceNo;

}
