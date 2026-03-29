package com.tpty.tableegi.jamath.components;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ActiveUsersStore {
    private final Map<String, UUID> activeUsers = new ConcurrentHashMap<>();

    public boolean isUserLoggedIn(String username) {
        return activeUsers.containsKey(username);
    }

    public void login(String username, UUID tokenId) {
        activeUsers.put(username, tokenId);
    }

    public void logout(String username) {
        UUID session = activeUsers.get(username);
        activeUsers.remove(username);
    }
}
