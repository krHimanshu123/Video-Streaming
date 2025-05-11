package jar.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jar.model.Video;
import jar.repository.VideoRepository;
import jar.service.VideoService;

@RestController
@RequestMapping("/api/videos")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @PostMapping
    public ResponseEntity<?> uploadVideo(
            @RequestParam("videoFile") MultipartFile videoFile,
            @RequestParam(value = "thumbnail", required = false) MultipartFile thumbnail,
            @RequestParam("title") String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam("category") String category,
            @RequestParam(value = "visibility", required = false) String visibility,
            @RequestParam(value = "tags", required = false) List<String> tags,
            @RequestParam("creator") String creator
    ) {
        try {
            // Save the video using the service
            Video savedVideo = videoService.saveVideo(
                    videoFile,
                    thumbnail,
                    title,
                    description,
                    category,
                    visibility,
                    tags,
                    creator
            );

            // Return JSON response
            return ResponseEntity.ok().body("{\"id\": " + savedVideo.getId() + ", \"videoUrl\": \"" + savedVideo.getVideoUrl() + "\"}");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("{\"message\": \"" + e.getMessage() + "\"}");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"Failed to save video file: " + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"Failed to upload video: " + e.getMessage() + "\"}");
        }
    }
}