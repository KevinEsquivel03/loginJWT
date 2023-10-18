package kaeh.personal.loginjwt.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import kaeh.personal.loginjwt.security.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * User class.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue
    Long id;

    @Column(nullable = false)
    String username;

    String lastName;

    String firstName;

    String country;

    String password;

    Role role;

    /**
     * Returns a collection of granted authorities for the user.
     *
     * @return a collection of granted authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    /**
     * Returns a boolean value indicating whether the user account is non-expired.
     *
     * @return {@code true} if the user account is non-expired, {@code false} otherwise.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Returns a boolean value indicating whether the user account is locked or not.
     *
     * @return {@code true} if the user account is not locked, {@code false} otherwise.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Returns a boolean value indicating whether the user's credentials are non-expired.
     *
     * @return {@code true} if the user's credentials are non-expired, {@code false} otherwise.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Returns a boolean value indicating whether the user is enabled.
     *
     * @return {@code true} if the user is enabled, {@code false} otherwise.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
