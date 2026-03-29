package com.tpty.tableegi.jamath.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.SecondaryRow;

import java.io.Serializable;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users_admins_tbl")   // you can change table name
@EntityListeners(AuditingEntityListener.class) // ✅ enables auditing hooks
@Data                   // Lombok: generates getters, setters, toString, equals, hashCode
@NoArgsConstructor      // Lombok: generates no-args constructor
@AllArgsConstructor     // Lombok: generates all-args constructor
@Builder                // Lombok: enables builder pattern
public class AdminLoginEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // ✅ modern way
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "username", nullable = false, unique = true, length = 100)
    private String username;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "is_super_admin")
    private boolean isSuperAdmin;

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

    // Lifecycle hooks for auto-populating dates
    //@PrePersist
    protected void onCreate() {
        this.createDate = LocalDateTime.now();
        this.lastUpdateDate = LocalDateTime.now();
    }

    // @PreUpdate
    protected void onUpdate() {
        this.lastUpdateDate = LocalDateTime.now();
    }

    // ✅ Just a foreign key column, no entity reference
    @Column(name = "location_id")
    private UUID locationId;

    @OneToMany
    @JoinColumn(name = "usr_admin_id") // foreign key in namazi_mstr_table
    private List<AdminUsersLoginTrackingEntity> adminUsersTracing = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "usr_admin_id") // foreign key in namazi_mstr_table
    private List<AddMasjidDataEntity> adminMasjidId = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "usr_admin_id") // foreign key in namazi_mstr_table
    private List<BroadCastMessageEntity> adminBroadCastMsgId = new ArrayList<>();
}

