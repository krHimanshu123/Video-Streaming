package jar.config;

import jar.model.ERole;
import jar.model.Role;
import jar.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleDataLoader {

    @Bean
    CommandLineRunner loadRoles(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.findByName(ERole.ROLE_USER.name()).isEmpty()) {
                roleRepository.save(new Role(ERole.ROLE_USER.name()));
            }
            if (roleRepository.findByName(ERole.ROLE_MODERATOR.name()).isEmpty()) {
                roleRepository.save(new Role(ERole.ROLE_MODERATOR.name()));
            }
            if (roleRepository.findByName(ERole.ROLE_ADMIN.name()).isEmpty()) {
                roleRepository.save(new Role(ERole.ROLE_ADMIN.name()));
            }
        };
    }
}
