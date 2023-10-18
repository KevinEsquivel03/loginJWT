package kaeh.personal.loginjwt.config.application;

import kaeh.personal.loginjwt.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * ApplicationConfig class.
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository userRepository;

    /**
     * A description of the entire Java function.
     *
     * @param  config   description of parameter
     * @return          description of return value
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // Create a bean for authentication provider
    @Bean
    public AuthenticationProvider authenticationProvider() {
        // Create a new instance of DaoAuthenticationProvider
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        // Set the UserDetailsService for the authentication provider
        authenticationProvider.setUserDetailsService(userDetailsService());

        // Set the PasswordEncoder for the authentication provider
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        // Return the authentication provider
        return authenticationProvider;
    }

    /**
     * Returns a PasswordEncoder instance using BCrypt algorithm.
     *
     * @return the PasswordEncoder instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Returns a UserDetailsService that retrieves user details from the UserRepository.
     * Throws a UsernameNotFoundException if the user is not found.
     *
     * @return UserDetailsService instance
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}