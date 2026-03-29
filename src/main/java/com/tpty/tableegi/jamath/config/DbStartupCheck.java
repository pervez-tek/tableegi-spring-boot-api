package com.tpty.tableegi.jamath.config;

import com.tpty.tableegi.jamath.entity.TestUser;
import com.tpty.tableegi.jamath.repo.TestUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DbStartupCheck implements CommandLineRunner {

    private final TestUserRepository repo;

    public DbStartupCheck(TestUserRepository repo) {
        this.repo = repo;
    }

    @Override
    public void run(String... args) {
        TestUser user = new TestUser("Hello PostgreSQL");
        repo.save(user);

        System.out.println("Inserted user ID = " + user.getId());
        System.out.println("Total rows = " + repo.count());
    }
}

