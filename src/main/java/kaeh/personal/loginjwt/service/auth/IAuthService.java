package kaeh.personal.loginjwt.service.auth;

import kaeh.personal.loginjwt.model.auth.AuthResponse;
import kaeh.personal.loginjwt.model.auth.LoginRequest;
import kaeh.personal.loginjwt.model.auth.RegisterRequest;

/**
 * IAuthService interface.
 */
public interface IAuthService {
    AuthResponse login(LoginRequest loginRequest);
    AuthResponse register(RegisterRequest registerRequest);
}
