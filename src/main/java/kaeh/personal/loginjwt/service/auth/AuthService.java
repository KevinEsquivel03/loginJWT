package kaeh.personal.loginjwt.service.auth;

import jakarta.persistence.EntityNotFoundException;
import kaeh.personal.loginjwt.model.auth.AuthResponse;
import kaeh.personal.loginjwt.model.auth.LoginRequest;
import kaeh.personal.loginjwt.model.auth.RegisterRequest;
import kaeh.personal.loginjwt.model.user.Role;
import kaeh.personal.loginjwt.repository.user.UserRepository;
import kaeh.personal.loginjwt.model.user.User;
import kaeh.personal.loginjwt.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticateUser(request.getUsername(), request.getPassword());
        UserDetails user = findUserByUsername(request.getUsername());
        String token = jwtService.generateToken(user);
        return createAuthResponse(token);
    }

    public AuthResponse register(RegisterRequest request) {
        User user = createUserFromRequest(request);
        userRepository.save(user);
        String token = jwtService.generateToken(user);
        return createAuthResponse(token);
    }

    private void authenticateUser(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    private UserDetails findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    private AuthResponse createAuthResponse(String token) {
        return AuthResponse.builder().token(token).build();
    }

    private User createUserFromRequest(RegisterRequest request) {
        return User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .country(request.getCountry())
                .role(Role.USER)
                .build();
    }
}
