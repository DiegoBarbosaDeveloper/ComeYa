package co.edu.ustavillavicencio.comeya.service;

public interface EmailService {
    void sendPasswordResetEmail(String to, String token);
}

