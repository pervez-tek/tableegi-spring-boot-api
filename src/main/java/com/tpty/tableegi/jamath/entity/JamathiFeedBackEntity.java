package com.tpty.tableegi.jamath.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "jamathi_feedback_tbl")   // you can change table name
@EntityListeners(AuditingEntityListener.class) // ✅ enables auditing hooks
@Data                   // Lombok: generates getters, setters, toString, equals, hashCode
@NoArgsConstructor      // Lombok: generates no-args constructor
@AllArgsConstructor     // Lombok: generates all-args constructor
@Builder                // Lombok: enables builder pattern
public class JamathiFeedBackEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // ✅ modern way
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    // ✅ Audit fields
    @CreatedDate
    @Column(name = "create_date", nullable = false, updatable = false)
    private LocalDateTime createDate;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "feedback", length = 300)
    private String feedBack;

    @Column(name = "rating")
    private int rating;


}
