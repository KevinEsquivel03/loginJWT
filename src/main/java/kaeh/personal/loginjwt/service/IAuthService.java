package kaeh.personal.loginjwt.service;

import kaeh.personal.loginjwt.dto.AuthResponse;
import kaeh.personal.loginjwt.dto.LoginRequest;
import kaeh.personal.loginjwt.dto.RegisterRequest;

/**
 * IAuthService interface.
 */
public interface IAuthService {
    AuthResponse login(LoginRequest loginRequest);
    AuthResponse register(RegisterRequest registerRequest);
}
