package co.edu.uniquindio.libreriaingsoft.controller;

import co.edu.uniquindio.libreriaingsoft.model.User;
import co.edu.uniquindio.libreriaingsoft.model.records.UserDTO;
import co.edu.uniquindio.libreriaingsoft.model.records.UserLoginDTO;
import co.edu.uniquindio.libreriaingsoft.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody UserDTO body) {
        User newUser = new User();
        newUser.setUsername(body.getUsername());
        newUser.setPassword(body.getPassword());
        newUser.setEmail(body.getEmail());

        return userService.registerUser(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<Object>loginUser(@RequestBody UserLoginDTO body) {
        return userService.loginUser(body.email(), body.password());
    }
}

