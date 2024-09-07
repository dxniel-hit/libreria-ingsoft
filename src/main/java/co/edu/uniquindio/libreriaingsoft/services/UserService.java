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

    public ResponseEntity<Object> registerUser(User user) {
        // Check if the user with the same email or username already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("User with this email already exists.");
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("User with this username already exists.");
        }

        // Save the user; MongoDB will automatically generate the ID
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

    /**
     * Checks if user is registered.
     * @param userId String
     * @return boolean
     */
    public boolean isUserRegistered(String userId) {
        // Check in the database if the user exists
        return userRepository.existsById(userId);
    }

}