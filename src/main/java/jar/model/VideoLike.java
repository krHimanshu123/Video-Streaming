package jar.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "video_likes", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"video_id", "user_id"})
})
public class VideoLike {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "video_id", nullable = false)
    private Video video;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // ✅ FIXED: no Optional
    
    @Column(nullable = false)
    private Date likeDate;

    // Getters and Setters
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getLikeDate() {
        return likeDate;
    }

    public void setLikeDate(Date likeDate) {
        this.likeDate = likeDate;
    }
}
