package co.edu.uniquindio.libreriaingsoft.controller;

import co.edu.uniquindio.libreriaingsoft.model.User;
import co.edu.uniquindio.libreriaingsoft.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public void registerUser(@RequestParam String email, @RequestParam String password) {
        userService.registerUser(email, password);
    }

    @PostMapping("/login")
    public User loginUser(@RequestParam String email, @RequestParam String password) {
        return userService.loginUser(email, password);
    }
}

