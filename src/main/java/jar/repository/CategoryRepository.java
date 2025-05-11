// src/main/java/jar/repository/CategoryRepository.java
package jar.repository;

import jar.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    Category findByName(String name);
    
    boolean existsByName(String name);
}