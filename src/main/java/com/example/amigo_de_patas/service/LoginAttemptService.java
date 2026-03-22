package com.example.amigo_de_patas.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LoginAttemptService {

    Map<String, Integer> attempts = new ConcurrentHashMap<>();
    Map<String, LocalDateTime> blockedUntil = new ConcurrentHashMap<>();

    public boolean isBlocked(String ip) {
        if (!blockedUntil.containsKey(ip)) {
            return false;
        }

        if (LocalDateTime.now().isAfter(blockedUntil.get(ip))) {
            blockedUntil.remove(ip);
            attempts.remove(ip);
            return false;
        }

        return true;
    }

    public void registerFailedAttempt(String ip) {
        if (!attempts.containsKey(ip)) {
            attempts.put(ip, 1);
        } else {
            attempts.put(ip, attempts.get(ip) + 1);
        }

        if (attempts.get(ip) == 5) {
            blockedUntil.put(ip, LocalDateTime.now().plusMinutes(30));
        }
    }

    public void resetAttempt(String ip) {
        attempts.remove(ip);
        blockedUntil.remove(ip);
    }
}
