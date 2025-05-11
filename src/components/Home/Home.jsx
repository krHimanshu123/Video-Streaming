import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import videoData from "../../data/video.json";

import './Home.css';

function Home() {
  const [videos, setVideos] = useState([]);
  const [filteredVideos, setFilteredVideos] = useState([]);
  const [categories, setCategories] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState('All');
  const navigate = useNavigate();

  useEffect(() => {
    try {
      // Initialize videos from video.json
      if (videoData && Array.isArray(videoData)) {
        setVideos(videoData);
        setFilteredVideos(videoData);
        const uniqueCategories = ['All', ...new Set(videoData.map(video => video.category))];
        setCategories(uniqueCategories);
        console.log('Loaded videos:', videoData);
        console.log('Categories:', uniqueCategories);
      } else {
        console.error('video.json is empty or not an array');
      }
    } catch (error) {
      console.error('Error loading video.json:', error);
    }
  }, []);

  useEffect(() => {
    // Filter videos based on selected category
    if (selectedCategory === 'All') {
      setFilteredVideos(videos);
    } else {
      setFilteredVideos(videos.filter(video => video.category === selectedCategory));
    }
    console.log('Filtered videos:', filteredVideos);
  }, [selectedCategory, videos]);

  // Function to add a new video (called by Upload.jsx)
  const addVideo = (newVideo) => {
    setVideos(prevVideos => {
      const updatedVideos = [...prevVideos, newVideo];
      // Update categories
      setCategories(['All', ...new Set(updatedVideos.map(video => video.category))]);
      setFilteredVideos(updatedVideos);
      console.log('Added new video:', newVideo);
      console.log('Updated videos:', updatedVideos);
      return updatedVideos;
    });
  };

  // Expose addVideo to global scope for Upload.jsx
  window.addVideo = addVideo;

  return (
    <div className="page-wrapper">
      <div className="home-container">
        <div className="categories-wrapper">
          <div className="categories">
            {categories.length > 0 ? (
              categories.map(category => (
                <button
                  key={category}
                  className={`category-btn ${selectedCategory === category ? 'active' : ''}`}
                  onClick={() => setSelectedCategory(category)}
                >
                  {category}
                </button>
              ))
            ) : (
              <p>No categories available</p>
            )}
          </div>
        </div>

        <div className="videos-grid">
          {filteredVideos.length > 0 ? (
            filteredVideos.map(video => (
              <div key={video.id} className="video-card">
                <div
                  className="open-video"
                  onClick={() => navigate(`/video/${video.id}`)}
                >
                  <div className="thumbnail-container">
                    <img
                      src={video.thumbnail}
                      alt={video.title}
                      className="video-thumbnail"
                      onError={(e) => {
                        console.error(`Failed to load thumbnail for video ${video.id}: ${video.thumbnail}`);
                        e.target.src = 'https://via.placeholder.com/320x180?text=Thumbnail+Not+Found';
                      }}
                    />
                    <span className="video-duration">{video.duration}</span>
                  </div>
                  <div className="video-info">
                    <div className="creator-avatar">
                      <img
                        src="https://webneel.com/daily/sites/default/files/images/daily/05-2018/portrait-photography-by-dennis-drozhzhin.jpg"
                        alt={video.creator}
                        onError={(e) => {
                          console.error(`Failed to load avatar for ${video.creator}`);
                          e.target.src = 'https://via.placeholder.com/50?text=Avatar';
                        }}
                      />
                    </div>
                    <div className="video-details">
                      <h3 className="video-title">{video.title}</h3>
                      <p className="creator">{video.creator}</p>
                      <p className="meta">{video.views} views • {new Date(video.timestamp).toLocaleDateString()}</p>
                    </div>
                  </div>
                </div>
              </div>
            ))
          ) : (
            <p className="no-videos">No videos available for this category.</p>
          )}
        </div>
      </div>
    </div>
  );
}

export default Home;