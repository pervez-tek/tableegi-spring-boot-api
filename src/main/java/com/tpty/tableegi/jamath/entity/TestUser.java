package com.tpty.tableegi.jamath.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "test_user")
@Data
public class TestUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public TestUser() {}

    public TestUser(String name) {
        this.name = name;
    }

    // getters & setters
}

