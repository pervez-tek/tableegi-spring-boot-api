package com.tpty.tableegi.jamath.repo;

import com.tpty.tableegi.jamath.entity.TestUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestUserRepository extends JpaRepository<TestUser, Long> {
}

