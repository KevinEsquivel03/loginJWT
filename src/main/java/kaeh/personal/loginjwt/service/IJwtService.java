package kaeh.personal.loginjwt.service;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * IJwtService interface.
 */
public interface IJwtService {
    String generateToken(UserDetails user);

    String getUsernameFromToken(String token);

    boolean isTokenValid(String token, UserDetails userDetails);
}
