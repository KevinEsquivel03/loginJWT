package kaeh.personal.loginjwt.service.jwt;

import org.springframework.security.core.userdetails.UserDetails;

public interface IJwtService {
    public String getToken(UserDetails user);

    String getUsernameFromToken(String token);

    boolean isTokenValid(String token, UserDetails userDetails);
}
