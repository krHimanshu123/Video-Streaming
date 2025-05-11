// --------------------------------
package jar.dto;

import org.springframework.web.multipart.MultipartFile;
import java.util.Objects;

public class VideoUploadRequest {
    private String title;
    private String description;
    private String categoryName;
    private MultipartFile videoFile;
    private String thumbnailUrl;
    private String duration;

    // Getters
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public MultipartFile getVideoFile() {
        return videoFile;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getDuration() {
        return duration;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setVideoFile(MultipartFile videoFile) {
        this.videoFile = videoFile;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VideoUploadRequest that = (VideoUploadRequest) o;
        return Objects.equals(title, that.title) &&
               Objects.equals(description, that.description) &&
               Objects.equals(categoryName, that.categoryName) &&
               Objects.equals(thumbnailUrl, that.thumbnailUrl) &&
               Objects.equals(duration, that.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, categoryName, thumbnailUrl, duration);
    }

    // toString
    @Override
    public String toString() {
        return "VideoUploadRequest{" +
               "title='" + title + '\'' +
               ", description='" + description + '\'' +
               ", categoryName='" + categoryName + '\'' +
               ", videoFile=" + (videoFile != null ? "present" : "null") +
               ", thumbnailUrl='" + thumbnailUrl + '\'' +
               ", duration='" + duration + '\'' +
               '}';
    }
}