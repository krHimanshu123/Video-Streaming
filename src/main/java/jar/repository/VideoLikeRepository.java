// src/main/java/jar/repository/VideoLikeRepository.java
package jar.repository;

import jar.model.User;
import jar.model.Video;
import jar.model.VideoLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoLikeRepository extends JpaRepository<VideoLike, Long> {
    
    List<VideoLike> findByVideo(Video video);
    
    List<VideoLike> findByUser(User user);
    
    Optional<VideoLike> findByVideoAndUser(Video video, User user);
    
    boolean existsByVideoAndUser(Video video, User user);
    
    void deleteByVideoAndUser(Video video, User user);
    
    long countByVideo(Video video);
}