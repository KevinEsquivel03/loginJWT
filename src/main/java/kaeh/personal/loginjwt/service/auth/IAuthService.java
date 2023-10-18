package kaeh.personal.loginjwt.service.auth;

import kaeh.personal.loginjwt.model.auth.AuthResponse;
import kaeh.personal.loginjwt.model.auth.LoginRequest;
import kaeh.personal.loginjwt.model.auth.RegisterRequest;

public interface IAuthService {
    public AuthResponse login(LoginRequest loginRequest);
    public AuthResponse register(RegisterRequest registerRequest);
}
