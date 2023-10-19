package kaeh.personal.loginjwt.service;

import jakarta.persistence.EntityNotFoundException;
import kaeh.personal.loginjwt.model.User;
import kaeh.personal.loginjwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
}
