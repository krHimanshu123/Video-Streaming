package jar.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import jar.model.Role;
import jar.repository.RoleRepository;
import jar.repository.UserRepository;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        // Initialize roles if they don't exist
        createRoleIfNotFound("ROLE_USER");
        createRoleIfNotFound("ROLE_ADMIN");
        
        System.out.println("Database initialized with required roles");
    }
    
    private void createRoleIfNotFound(String name) {
        if (!roleRepository.findByName(name).isPresent()) {
            Role role = new Role(name);
            roleRepository.save(role);
            System.out.println("Created role: " + name);
        }
    }
}