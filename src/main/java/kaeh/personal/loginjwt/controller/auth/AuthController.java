package kaeh.personal.loginjwt.controller.auth;

import kaeh.personal.loginjwt.model.auth.AuthResponse;
import kaeh.personal.loginjwt.model.auth.LoginRequest;
import kaeh.personal.loginjwt.model.auth.RegisterRequest;
import kaeh.personal.loginjwt.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AuthController class.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * A description of the entire Java function.
     *
     * @param  request   description of parameter
     *                   (replace `paramName` with the actual name of the parameter)
     * @return           description of return value
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Registers a user.
     *
     * @param  request   the registration request
     * @return           the authentication response
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }
}