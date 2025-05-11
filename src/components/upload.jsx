import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './upload.css';

function Upload() {
  const navigate = useNavigate();
  const [videoDetails, setVideoDetails] = useState({
    title: '',
    description: '',
    category: 'Entertainment',
    videoFile: null,
    thumbnail: '',
    creator: 'Current User',
    duration: '',
    views: '0'
  });
  const [error, setError] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const [uploadStage, setUploadStage] = useState('details');
  const [uploadedVideoId, setUploadedVideoId] = useState(null);

  const categories = [
    'Entertainment', 'Education', 'Music', 'Sports', 'Gaming', 'News', 'Other'
  ]; // static or from backend

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setVideoDetails({ ...videoDetails, [name]: value });
  };

  const handleFileChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      const validTypes = ['video/mp4', 'video/webm', 'video/ogg'];
      if (!validTypes.includes(file.type)) {
        setError('Please upload a valid video file (MP4, WebM, or OGG).');
        return;
      }
      const maxSize = 100 * 1024 * 1024; // 100MB
      if (file.size > maxSize) {
        setError('Video file is too large. Maximum size is 100MB.');
        return;
      }
      setVideoDetails({ ...videoDetails, videoFile: file });
      setError('');
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setIsLoading(true);

    if (!videoDetails.title || !videoDetails.videoFile || !videoDetails.thumbnail || !videoDetails.duration) {
      setError('Please fill in all required fields.');
      setIsLoading(false);
      return;
    }

    try {
      setUploadStage('processing');

      const formData = new FormData();
      formData.append('title', videoDetails.title);
      formData.append('description', videoDetails.description);
      formData.append('category', videoDetails.category);
      formData.append('thumbnail', videoDetails.thumbnail);
      formData.append('creator', videoDetails.creator);
      formData.append('duration', videoDetails.duration);
      formData.append('views', videoDetails.views);
      formData.append('videoFile', videoDetails.videoFile);

      // 🔗 Update this URL with your Spring Boot backend endpoint
      const response = await fetch('http://localhost:8080/api/videos/upload', {
        method: 'POST',
        body: formData,
      });

      if (!response.ok) {
        const contentType = response.headers.get('content-type');
        let errorMessage = 'Failed to upload video';
        if (contentType && contentType.includes('application/json')) {
          const errorData = await response.json();
          errorMessage = errorData.message || errorMessage;
        } else {
          const text = await response.text();
          if (text) errorMessage = text;
        }
        throw new Error(errorMessage);
      }

      const savedVideo = await response.json();
      setUploadedVideoId(savedVideo.id); // assuming backend returns `id`
      setUploadStage('complete');
    } catch (err) {
      console.error(err);
      setError(`Upload failed: ${err.message}`);
      setUploadStage('details');
    } finally {
      setIsLoading(false);
    }
  };

  const resetUpload = () => {
    setVideoDetails({
      title: '',
      description: '',
      category: 'Entertainment',
      videoFile: null,
      thumbnail: '',
      creator: 'Current User',
      duration: '',
      views: '0'
    });
    setError('');
    setUploadStage('details');
    setUploadedVideoId(null);
  };

  // --- UI Sections Below (Same as before) ---

  if (uploadStage === 'details') {
    return (
      <div className="upload-container">
        <h1>Upload Video</h1>
        <form className="video-details-form" onSubmit={handleSubmit}>
          <div className="form-group">
            <label>Title (required)</label>
            <input
              type="text"
              name="title"
              value={videoDetails.title}
              onChange={handleInputChange}
              required
              maxLength="100"
              placeholder="Add a title"
            />
            <span>{videoDetails.title.length}/100</span>
          </div>

          <div className="form-group">
            <label>Description</label>
            <textarea
              name="description"
              value={videoDetails.description}
              onChange={handleInputChange}
              rows="5"
              maxLength="5000"
              placeholder="Tell viewers about your video"
            ></textarea>
            <span>{videoDetails.description.length}/5000</span>
          </div>

          <div className="form-group">
            <label>Video File (required)</label>
            <input
              type="file"
              name="videoFile"
              accept="video/mp4,video/webm,video/ogg"
              onChange={handleFileChange}
              required
            />
            <small>Supported: MP4, WebM, OGG (Max 100MB)</small>
          </div>

          <div className="form-group">
            <label>Thumbnail URL (required)</label>
            <input
              type="url"
              name="thumbnail"
              value={videoDetails.thumbnail}
              onChange={handleInputChange}
              required
              placeholder="e.g., https://example.com/thumb.jpg"
            />
          </div>

          <div className="form-group">
            <label>Duration (e.g., 03:45)</label>
            <input
              type="text"
              name="duration"
              value={videoDetails.duration}
              onChange={handleInputChange}
              required
            />
          </div>

          <div className="form-group">
            <label>Category</label>
            <select
              name="category"
              value={videoDetails.category}
              onChange={handleInputChange}
            >
              {categories.map((category) => (
                <option key={category} value={category}>{category}</option>
              ))}
            </select>
          </div>

          {error && <div className="upload-error">{error}</div>}

          <div className="form-actions">
            <button type="button" onClick={resetUpload}>Cancel</button>
            <button type="submit" disabled={isLoading}>
              {isLoading ? 'Uploading...' : 'Upload'}
            </button>
          </div>
        </form>
      </div>
    );
  }

  if (uploadStage === 'processing') {
    return (
      <div className="upload-container">
        <h1>Processing your video...</h1>
        <div className="spinner" />
        <p>Please wait while we process your video.</p>
      </div>
    );
  }

  if (uploadStage === 'complete') {
    return (
      <div className="upload-container">
        <h1>Upload Successful!</h1>
        <h2>{videoDetails.title}</h2>
        <p>Your video is now available.</p>
        <div className="success-actions">
          <button onClick={() => navigate(`/video/${uploadedVideoId}`)}>View Video</button>
          <button onClick={() => navigate('/')}>Go Home</button>
        </div>
      </div>
    );
  }

  return null;
}

export default Upload;
