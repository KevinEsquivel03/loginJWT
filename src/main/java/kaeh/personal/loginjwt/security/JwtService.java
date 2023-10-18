package kaeh.personal.loginjwt.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import kaeh.personal.loginjwt.service.IJwtService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * JwtService class.
 */
@Service
public class JwtService implements IJwtService {

    private static final String SECRET_KEY = "586E3272357538782F413F4428472B4B6250655368566B597033733676397924";
    private static final long TOKEN_EXPIRATION_MILLIS = 1000L * 60 * 24;

    /**
     * Generates a token for the given user.
     *
     * @param  user  the user details
     * @return       the generated token
     */
    public String generateToken(UserDetails user) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, user);
    }

    /**
     * Retrieves the username from the provided token.
     *
     * @param  token  the token from which to retrieve the username
     * @return        the username extracted from the token
     */
    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    /**
     * Checks if a token is valid.
     *
     * @param  token         the token to be checked
     * @param  userDetails  the user details
     * @return               true if the token is valid, false otherwise
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Generates a token based on the provided claims and user details.
     *
     * @param  claims the claims to be included in the token
     * @param  user   the user details for whom the token is being generated
     * @return        the generated token as a string
     */
    private String createToken(Map<String, Object> claims, UserDetails user) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + TOKEN_EXPIRATION_MILLIS);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Generates the key for the Java function.
     *
     * @return         	the generated key
     */
    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Retrieves all the claims from the provided token.
     *
     * @param  token  the token from which to retrieve the claims
     * @return        the claims extracted from the token
     */
    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Retrieves a specific claim from the given token using the provided claims resolver function.
     *
     * @param  token          the token from which to retrieve the claim
     * @param  claimsResolver the function used to resolve the claim from the token's claims
     * @param  <T>            the type of the claim to retrieve
     * @return                the resolved claim from the token's claims
     */
    private <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Retrieves the expiration date of a token.
     *
     * @param  token the token for which the expiration date is to be retrieved
     * @return      the expiration date of the token
     */
    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    /**
     * Determines if the given token is expired.
     *
     * @param  token  the token to check for expiration
     * @return        true if the token is expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        Date expirationDate = getExpiration(token);
        Date now = new Date();
        return expirationDate.before(now);
    }
}