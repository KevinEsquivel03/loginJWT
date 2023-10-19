package kaeh.personal.loginjwt.controller;

import jakarta.persistence.EntityNotFoundException;
import kaeh.personal.loginjwt.model.User;
import kaeh.personal.loginjwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserInfo(@PathVariable String username) {
        try {
            User user = userService.loadUserByUsername(username);
            return ResponseEntity.ok(user);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        try {
            Long idLong = Long.parseLong(id);
            userService.deleteUserById(idLong);
            return ResponseEntity.ok("User successfully deleted");
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("invalid id");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
