package kaeh.personal.loginjwt.filter.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kaeh.personal.loginjwt.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JwtAuthenticationFilter class.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    /**
     * This method is called by the servlet container to perform the actual filtering of the request and response.
     * It extracts the token from the request, processes it, and then continues the filter chain.
     *
     * @param request      the HttpServletRequest object representing the client's request
     * @param response     the HttpServletResponse object representing the response to be sent to the client
     * @param filterChain the FilterChain object that allows the request to be passed to the next filter in the chain
     * @throws ServletException if any servlet-related errors occur
     * @throws IOException      if any I/O-related errors occur
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String token = getTokenFromRequest(request);

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        processToken(token, request);

        filterChain.doFilter(request, response);
    }

    /**
     * Processes the given token and authenticates the user if the token is valid.
     *
     * @param token    the JWT token to process
     * @param request  the HttpServletRequest object
     */
    private void processToken(String token, HttpServletRequest request) {
        String username = jwtService.getUsernameFromToken(token);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtService.isTokenValid(token, userDetails)) {
                authenticateUser(userDetails, request);
            }
        }
    }

    /**
     * Authenticates the user with the provided user details and sets the authentication token in the security context.
     *
     * @param userDetails The user details of the authenticated user.
     * @param request The HTTP servlet request.
     */
    private void authenticateUser(UserDetails userDetails, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    /**
     * Extracts the JWT token from the HttpServletRequest object.
     *
     * @param request The HttpServletRequest object from which to extract the token.
     * @return The JWT token extracted from the request, or null if no token is found.
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}
