package kaeh.personal.loginjwt.service;

import jakarta.persistence.EntityNotFoundException;
import kaeh.personal.loginjwt.dto.AuthResponse;
import kaeh.personal.loginjwt.dto.LoginRequest;
import kaeh.personal.loginjwt.dto.RegisterRequest;
import kaeh.personal.loginjwt.security.Role;
import kaeh.personal.loginjwt.repository.UserRepository;
import kaeh.personal.loginjwt.model.User;
import kaeh.personal.loginjwt.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * AuthService class.
 */
@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Authenticates a user's login request and generates an authentication response.
     *
     * @param  request  the login request containing the user's username and password
     * @return          the authentication response containing the generated token
     */
    public AuthResponse login(LoginRequest request) {
        authenticateUser(request.getUsername(), request.getPassword());
        UserDetails user = findUserByUsername(request.getUsername());
        String token = jwtService.generateToken(user);
        return createAuthResponse(token);
    }

    /**
     * Register a new user and return an authentication response.
     *
     * @param  request  the register request containing user details
     * @return          the authentication response with a token
     */
    public AuthResponse register(RegisterRequest request) {
        User user = createUserFromRequest(request);
        userRepository.save(user);
        String token = jwtService.generateToken(user);
        return createAuthResponse(token);
    }

    /**
     * Authenticates a user with the given username and password.
     *
     * @param  username  the username of the user
     * @param  password  the password of the user
     */
    private void authenticateUser(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    /**
     * Finds a user by their username.
     *
     * @param  username  the username of the user
     * @return           the UserDetails object representing the user
     */
    private UserDetails findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    /**
     * Creates an authentication response object.
     *
     * @param  token  The authentication token.
     * @return        The authentication response object.
     */
    private AuthResponse createAuthResponse(String token) {
        return AuthResponse.builder().token(token).build();
    }

    /**
     * Creates a new User object from the provided RegisterRequest object.
     *
     * @param  request  the RegisterRequest object containing the user information
     * @return          the newly created User object
     */
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
