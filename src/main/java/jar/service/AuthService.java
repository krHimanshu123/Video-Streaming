package jar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jar.model.ERole;
import jar.model.Role;
import jar.model.User;
import jar.repository.RoleRepository;
import jar.repository.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Register a new user in the system.
     * 
     * @param username the username for the new user
     * @param email the email for the new user
     * @param password the password for the new user (will be encoded)
     * @return a success message or error message
     */
    public String signUp(String username, String email, String password) {
        // Check if username already exists
        if (userRepository.findByUsername(username).isPresent()) {
            return "Username already exists";
        }

        // Check if email already exists
        if (userRepository.findByEmail(email).isPresent()) {
            return "Email already in use";
        }

        // Create new user
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password)); // Encode password for security
        
        // Assign ROLE_USER by default
        Role userRole = roleRepository.findByName(ERole.ROLE_USER.name())
                .orElseThrow(() -> new RuntimeException("Error: Role not found."));
        
        Set<Role> roles = new HashSet<>();
        roles.add(userRole); // Assign ROLE_USER to the user
        user.setRoles(roles);

        // Save user to database
        userRepository.save(user);
        
        return "User registered successfully";
    }

    /**
     * Authenticate a user by username and password
     * 
     * @param username the username to authenticate with
     * @param password the raw password to authenticate with
     * @return a success message or error message
     */
    public String login(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            
            // Check if password matches
            if (passwordEncoder.matches(password, user.getPassword())) {
                return "Login successful";
            }
        }
        
        return "Invalid username or password";
    }
}
