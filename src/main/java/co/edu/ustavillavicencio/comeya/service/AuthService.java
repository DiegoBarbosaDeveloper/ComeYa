package co.edu.ustavillavicencio.comeya.service;

import co.edu.ustavillavicencio.comeya.dto.login.LoginRequest;
import co.edu.ustavillavicencio.comeya.dto.login.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);
}