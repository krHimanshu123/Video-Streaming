package jar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jar.model.Category;
import jar.model.Video;

public interface VideoRepository extends JpaRepository<Video, Long> {

	List<Video> findByCategory(Category category);

}