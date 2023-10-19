package kaeh.personal.loginjwt.controller;

import jakarta.persistence.EntityNotFoundException;
import kaeh.personal.loginjwt.model.User;
import kaeh.personal.loginjwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<Object> getUserInfo(@PathVariable String username) {
        try {
            User user = userService.loadUserByUsername(username);
            return ResponseEntity.ok(user);
        } catch (EntityNotFoundException | UsernameNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid username or id");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        try {
            Long idLong = Long.parseLong(id);
            userService.deleteUserById(idLong);
            return ResponseEntity.ok("User successfully deleted");
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid id");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}