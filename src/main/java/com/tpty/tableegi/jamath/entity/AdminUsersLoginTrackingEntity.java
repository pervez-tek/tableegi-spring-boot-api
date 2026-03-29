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
import java.util.UUID;

@Entity
@Table(name = "users_login_tracking_tbl",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"id", "usr_admin_id", "is_admin_login", "session_id"})})
// you can change table name
@EntityListeners(AuditingEntityListener.class) // ✅ enables auditing hooks
@Data                   // Lombok: generates getters, setters, toString, equals, hashCode
@NoArgsConstructor      // Lombok: generates no-args constructor
@AllArgsConstructor     // Lombok: generates all-args constructor
@Builder                // Lombok: enables builder pattern
public class AdminUsersLoginTrackingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // ✅ modern way
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;


    // ✅ Just a foreign key column, no entity reference
    @Column(name = "usr_admin_id")
    private UUID usrAdminId;

    @Column(name = "session_id")
    private UUID sessionId;

    @Column(name = "is_admin_login")
    private boolean isAdminLogin;

    @CreatedDate
    @Column(name = "create_date", nullable = false, updatable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(name = "last_login_date")
    private LocalDateTime lastLoginDate;

    @Transient
    private boolean isSuperAdmin;

    @Transient
    private String locationId;

}
