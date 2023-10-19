package kaeh.personal.loginjwt.controller;

import kaeh.personal.loginjwt.model.User;
import kaeh.personal.loginjwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping("/{username}")
    public User getUserInfo(@PathVariable String username) {
        return userService.loadUserByUsername(username);
    }

}
