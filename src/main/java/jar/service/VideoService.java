package jar.service;

import jar.model.Category;
import jar.model.User;
import jar.model.Video;
import jar.model.VideoLike;
import jar.repository.CategoryRepository;
import jar.repository.UserRepository;
import jar.repository.VideoLikeRepository;
import jar.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VideoLikeRepository videoLikeRepository;

    @Value("${app.upload.dir:/tmp/uploads}")
    private String uploadDir;

    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }

    public Optional<Video> getVideoById(Long id) {
        return videoRepository.findById(id);
    }

    public List<Video> getVideosByCategory(String categoryName) {
        if (categoryName == null || categoryName.trim().isEmpty()) {
            return Collections.emptyList();
        }
        Category category = categoryRepository.findByName(categoryName);
        if (category == null) {
            return Collections.emptyList();
        }
        return videoRepository.findByCategory(category);
    }

    public Video saveVideo(
            MultipartFile videoFile,
            MultipartFile thumbnail,
            String title,
            String description,
            String categoryName,
            String visibility,
            List<String> tags,
            String username) throws IOException {

        if (videoFile == null || videoFile.isEmpty() || title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Video file and title are required");
        }

        File uploadsDir = new File(uploadDir);
        if (!uploadsDir.exists()) {
            uploadsDir.mkdirs();
        }

        String videoFileName = UUID.randomUUID().toString() + getFileExtension(videoFile.getOriginalFilename());
        String videoPath = uploadDir + File.separator + videoFileName;
        Path videoFilePath = Paths.get(videoPath);
        Files.write(videoFilePath, videoFile.getBytes());

        String thumbnailPath = null;
        if (thumbnail != null && !thumbnail.isEmpty()) {
            String thumbnailFileName = UUID.randomUUID().toString() + getFileExtension(thumbnail.getOriginalFilename());
            thumbnailPath = uploadDir + File.separator + thumbnailFileName;
            Path thumbnailFilePath = Paths.get(thumbnailPath);
            Files.write(thumbnailFilePath, thumbnail.getBytes());
        }

        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username is required");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));

        if (categoryName == null || categoryName.trim().isEmpty()) {
            throw new IllegalArgumentException("Category name is required");
        }

        Category category = categoryRepository.findByName(categoryName);
        if (category == null) {
            category = new Category();
            category.setName(categoryName);
            categoryRepository.save(category);
        }

        Video video = new Video();
        video.setTitle(title);
        video.setDescription(description != null ? description : "");
        video.setUser(user);
        video.setCategory(category);
        video.setVisibility(visibility != null ? visibility : "public");
        video.setUploadDate(new Date());
        video.setVideoUrl("/uploads/videos/" + videoFileName);
        video.setThumbnailUrl(thumbnailPath != null ? "/uploads/videos/" + new File(thumbnailPath).getName() : null);
        video.setStatus("processing");

        if (tags != null && !tags.isEmpty()) {
            video.setTags(tags);
        }

        Video savedVideo = videoRepository.save(video);
        processVideoAsync(savedVideo);
        return savedVideo;
    }

    private void processVideoAsync(Video video) {
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(10000); // Simulate video processing
                video.setStatus("ready");
                videoRepository.save(video);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                video.setStatus("failed");
                videoRepository.save(video);
            }
        });
    }

    public Optional<String> getVideoProcessingStatus(Long videoId) {
        return videoRepository.findById(videoId)
                .map(Video::getStatus);
    }

    public void likeVideo(Long videoId, String username) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new IllegalArgumentException("Video not found with ID: " + videoId));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));

        boolean alreadyLiked = videoLikeRepository.findByVideoAndUser(video, user).isPresent();

        if (!alreadyLiked) {
            VideoLike like = new VideoLike();
            like.setVideo(video);
            like.setUser(user);
            like.setLikeDate(new Date());
            videoLikeRepository.save(like);
        }
    }

    public void unlikeVideo(Long videoId, String username) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new IllegalArgumentException("Video not found with ID: " + videoId));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->new IllegalArgumentException("User not found: " + username));

        videoLikeRepository.findByVideoAndUser(video, user)
                .ifPresent(videoLikeRepository::delete);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    private String getFileExtension(String filename) {
        if (filename == null) return "";
        int lastDotIndex = filename.lastIndexOf('.');
        return (lastDotIndex > 0) ? filename.substring(lastDotIndex) : "";
    }
}
