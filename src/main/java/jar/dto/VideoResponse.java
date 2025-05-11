package jar.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class VideoResponse {
    private Long id;
    private String title;
    private String description;
    private String videoUrl;
    private String thumbnailUrl;
    private String creatorName;
    private Long creatorId;
    private String categoryName;
    private Long viewCount;
    private LocalDateTime uploadDate;
    private Integer duration;

    // Getters
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Long getViewCount() {
        return viewCount;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public Integer getDuration() {
        return duration;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VideoResponse that = (VideoResponse) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(title, that.title) &&
               Objects.equals(description, that.description) &&
               Objects.equals(videoUrl, that.videoUrl) &&
               Objects.equals(thumbnailUrl, that.thumbnailUrl) &&
               Objects.equals(creatorName, that.creatorName) &&
               Objects.equals(creatorId, that.creatorId) &&
               Objects.equals(categoryName, that.categoryName) &&
               Objects.equals(viewCount, that.viewCount) &&
               Objects.equals(uploadDate, that.uploadDate) &&
               Objects.equals(duration, that.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, videoUrl, thumbnailUrl, creatorName, creatorId, categoryName, viewCount, uploadDate, duration);
    }

    // toString
    @Override
    public String toString() {
        return "VideoResponse{" +
               "id=" + id +
               ", title='" + title + '\'' +
               ", description='" + description + '\'' +
               ", videoUrl='" + videoUrl + '\'' +
               ", thumbnailUrl='" + thumbnailUrl + '\'' +
               ", creatorName='" + creatorName + '\'' +
               ", creatorId=" + creatorId +
               ", categoryName='" + categoryName + '\'' +
               ", viewCount=" + viewCount +
               ", uploadDate=" + uploadDate +
               ", duration=" + duration +
               '}';
    }
}
