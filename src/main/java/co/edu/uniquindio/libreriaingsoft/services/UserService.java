package co.edu.uniquindio.libreriaingsoft.services;

import co.edu.uniquindio.libreriaingsoft.model.User;
import co.edu.uniquindio.libreriaingsoft.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public ResponseEntity<Object> registerUser(String email, String username, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"message\": \"User with this email already exists\"}");
        }

        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("{\"userId\": \"" + user.getId() + "\"}");
    }

    public ResponseEntity<Object> loginUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"message\": \"Invalid email or password\"}");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"message\": \"Invalid email or password\"}");
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body("{\"userId\": \"" + user.getId() + "\"}");
    }
}