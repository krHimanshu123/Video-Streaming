package jar.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jar.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByVideoId(Long videoId, Pageable pageable);
    
    List<Comment> findByVideoIdOrderByCreatedAtDesc(Long videoId);
}