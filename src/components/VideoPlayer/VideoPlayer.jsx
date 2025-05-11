// src/components/VideoPlayer/VideoPlayer.jsx
import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { ThumbsUp, ThumbsDown, Share } from 'lucide-react';
import './VideoPlayer.css';

function VideoPlayer() {
  const { id } = useParams(); // Get video ID from URL
  const [comments, setComments] = useState([]);
  const [newComment, setNewComment] = useState('');
  const [videoData, setVideoData] = useState(null);

  // Mock video data (in a real app, this could come from an API)
  const mockVideos = [
    {
      id: 1,
      title: 'Building a Modern Web Application with React',
      youtubeUrl: 'https://www.youtube.com/embed/Ke90Tje7VS0',
      description: 'Learn how to build a modern web application using React.',
      creator: 'Tech Master',
    },
    {
      id: 2,
      title: 'The Most Beautiful Places in India - Travel Vlog',
      youtubeUrl: 'https://youtu.be/cOa97mBh8co?si=__s2YQwoNLLPjKKa',
      description: 'Explore the stunning landscapes of India in this travel vlog.',
      creator: 'Travel With Me',
    },
    {
      id: 3,
      title: 'Learn Piano in 30 Days - Day 1',
      youtubeUrl: 'https://youtu.be/hyJEKmoFQDs?si=8HyutT98t7f01GP8',
      description: 'Start your piano journey with this beginner lesson.',
      creator: 'Music Academy',
    },
    {
      id: 4,
      title: 'Making the Perfect Pizza at Home',
      youtubeUrl: 'https://youtu.be/G-jPoROGHGE?si=usFaSmIZWbAGDtKQ',
      description: 'Master the art of pizza making with this recipe.',
      creator: 'Cooking Master',
    },
    {
      id: 5,
      title: 'Understanding Quantum Computing',
      youtubeUrl: 'https://www.youtube.com/embed/QX3M8Ka9vUA',
      description: 'A beginner-friendly introduction to quantum computing.',
      creator: 'Science Explained',
    },
    {
      id: 6,
      title: 'Daily Workout Routine for Beginners',
      youtubeUrl: 'https://www.youtube.com/embed/ixkQaZw-jks',
      description: 'Get fit with this beginner workout routine.',
      creator: 'Fitness Pro',
    },
    {
      id: 7,
      title: 'Daily Yoga Routine for Beginners',
      youtubeUrl: 'https://www.youtube.com/embed/v7AYKMP6rOE',
      description: 'Start your yoga journey with this beginner routine.',
      creator: 'Fitness Pro',
    },
    {
      id: 8,
      title: 'India vs Pakistan',
      youtubeUrl: 'https://youtu.be/6I2XfZO8l4k?si=a0Jfnw1YUtgcMuYg',
      description: 'Highlights from the thrilling India vs Pakistan match.',
      creator: 'Cricket Match',
    },
    {
      id: 9,
      title: 'BGMI: Top 5 Settings to Improve Your Gameplay',
      youtubeUrl: 'https://youtu.be/fQi83rqXFkg?si=ONHJt8p1xfW7dQzq',
      description: 'Optimize your BGMI gameplay with these settings.',
      creator: 'BGMI Expert',
    },
    {
      id: 10,
      title: 'Free Fire: How to Complete Booyahs Events!',
      youtubeUrl: 'https://youtu.be/HtaSHNSGym4?si=1VmushkxDsrDVRbK',
      description: 'Learn how to excel in Free Fire Booyah events.',
      creator: 'Free Fire Guru',
    },
    {
      id: 11,
      title: 'Top 10 Hindi Movies of 2024',
      youtubeUrl: 'https://youtu.be/Hem-M9GkyuQ?si=qTvpn-ZBeopASitt',
      description: 'Check out the best Hindi movies of 2024.',
      creator: 'Bollywood Buzz',
    },
    {
      id: 12,
      title: 'Best South Indian Movies to Watch',
      youtubeUrl: 'https://youtu.be/W5y69I4Fqao?si=ntCR5efJLoklH963',
      description: 'Discover must-watch South Indian films.',
      creator: 'South Cinema',
    },
    {
      id: 13,
      title: 'Top 5 Anime Series You Must Watch',
      youtubeUrl: 'https://youtu.be/y5F5PGYDBXQ?si=JBIHObxKd7C28Jsl',
      description: 'Explore the best anime series for all fans.',
      creator: 'Anime Fanatic',
    },
  ];

  useEffect(() => {
    // Find the video data based on the ID
    const video = mockVideos.find(v => v.id === parseInt(id));
    setVideoData(video);
  }, [id]);

  const handleAddComment = (e) => {
    e.preventDefault();
    if (newComment.trim()) {
      setComments([
        {
          id: Date.now(),
          text: newComment,
          user: 'Current User',
          timestamp: 'Just now'
        },
        ...comments
      ]);
      setNewComment('');
    }
  };

  return (
    <div className="video-player-page">
      <div className="video-container">
        {videoData ? (
          <iframe
            width="100%"
            height="400"
            src={`${videoData.youtubeUrl}?autoplay=0`} // Autoplay disabled
            title={videoData.title}
            frameBorder="0"
            allow="accelerometer; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
            allowFullScreen
          ></iframe>
        ) : (
          <p>Loading video...</p>
        )}
      </div>
      <div className="video-details">
        <h1>{videoData ? videoData.title : 'Loading...'}</h1>
        <div className="video-actions">
          <div className="likes">
            <button><ThumbsUp /> 1.2K</button>
            <button><ThumbsDown /> 50</button>
          </div>
          <button><Share /> Share</button>
        </div>
        <div className="video-description">
          <p>{videoData ? videoData.description : 'Loading description...'}</p>
          <p>Creator: {videoData ? videoData.creator : 'Loading...'}</p>
        </div>
      </div>
      <div className="comments-section">
        <h3>Comments</h3>
        <form onSubmit={handleAddComment} className="comment-form">
          <input
            type="text"
            placeholder="Add a comment..."
            value={newComment}
            onChange={(e) => setNewComment(e.target.value)}
          />
          <button type="submit">Comment</button>
        </form>
        <div className="comments-list">
          {comments.map(comment => (
            <div key={comment.id} className="comment">
              <div className="comment-header">
                <span className="comment-user">{comment.user}</span>
                <span className="comment-time">{comment.timestamp}</span>
              </div>
              <p>{comment.text}</p>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

export default VideoPlayer;