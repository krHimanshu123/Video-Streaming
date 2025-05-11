import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Bell, Search, Upload, User, Menu, Home, Compass, Clock, ThumbsUp, PlaySquare, History, Clapperboard, Gamepad, Music2, Trophy, Newspaper } from 'lucide-react';
import './Navbar.css';

function Navbar() {
  const [searchQuery, setSearchQuery] = useState('');
  const [isSearchFocused, setIsSearchFocused] = useState(false);
  const [isSidebarOpen, setIsSidebarOpen] = useState(false);
  const [isLoggedIn, setIsLoggedIn] = useState(false); // Add authentication state
  const navigate = useNavigate();

  const handleSearch = (e) => {
    e.preventDefault();
    if (searchQuery.trim()) {
      navigate(`/search?q=${encodeURIComponent(searchQuery)}`);
    }
  };

  const toggleSidebar = () => {
    setIsSidebarOpen(!isSidebarOpen);
    document.body.style.overflow = isSidebarOpen ? 'auto' : 'hidden';
  };

  const handleAuthClick = () => {
    if (!isLoggedIn) {
      navigate('/login');
    }
  };

  return (
    <>
      <nav className="navbar">
        {/* Left and Center sections remain the same */}
        <div className="nav-left">
          <button className="menu-button" onClick={toggleSidebar}>
            <Menu size={24} />
          </button>
          <Link to="/" className="logo">
            <span className="logo-text">FlixaraX</span>
          </Link>
        </div>

        <div className="nav-center">
          <form 
            onSubmit={handleSearch} 
            className={`search-form ${isSearchFocused ? 'focused' : ''}`}
          >
            <input
              type="text"
              placeholder="Search videos..."
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
              onFocus={() => setIsSearchFocused(true)}
              onBlur={() => setIsSearchFocused(false)}
            />
            <button type="submit" className="search-button">
              <Search size={20} />
            </button>
          </form>
        </div>

        <div className="nav-right">
          <Link to="/upload" className="nav-item">
            <Upload size={20} />
            <span className="nav-item-label">Upload</span>
          </Link>
          <div className="nav-item notification">
                <Bell size={20} />
                <span className="notification-badge">3</span>
                <span className="nav-item-label">Notifications</span>
              </div>
          {isLoggedIn ? (
            <>
              
              <Link to="/profile" className="nav-item profile">
                <div className="profile-avatar">
                  <User size={20} />
                </div>
                <span className="nav-item-label">Profile</span>
              </Link>
            </>
          ) : (
            <button onClick={handleAuthClick} className="login-button">
              <User size={20} />
              <span>Login</span>
            </button>
          )}
        </div>
      </nav>

      <div 
        className={`sidebar-overlay ${isSidebarOpen ? 'active' : ''}`}
        onClick={toggleSidebar}
      ></div>

<div className={`sidebar ${isSidebarOpen ? 'open' : ''}`}>
        <div className="sidebar-section">
          <Link to="/" className="sidebar-item">
            <Home size={20} />
            <span>Home</span>
          </Link>
          <Link to="/explore" className="sidebar-item">
            <Compass size={20} />
            <span>Explore</span>
          </Link>
          <Link to="/subscriptions" className="sidebar-item">
            <PlaySquare size={20} />
            <span>Subscriptions</span>
          </Link>
        </div>

        <div className="sidebar-divider"></div>

        <div className="sidebar-section">
          <Link to="/library" className="sidebar-item">
            <PlaySquare size={20} />
            <span>Library</span>
          </Link>
          <Link to="/history" className="sidebar-item">
            <History size={20} />
            <span>History</span>
          </Link>
          <Link to="/your-videos" className="sidebar-item">
            <PlaySquare size={20} />
            <span>Your Videos</span>
          </Link>
          <Link to="/watch-later" className="sidebar-item">
            <Clock size={20} />
            <span>Watch Later</span>
          </Link>
          <Link to="/liked-videos" className="sidebar-item">
            <ThumbsUp size={20} />
            <span>Liked Videos</span>
          </Link>
        </div>

        <div className="sidebar-divider"></div>

        <div className="sidebar-section">
          <h3 className="sidebar-heading">Explore</h3>
          <Link to="/trending" className="sidebar-item">
            <Clapperboard size={20} />
            <span>Trending</span>
          </Link>
          <Link to="/music" className="sidebar-item">
            <Music2 size={20} />
            <span>Music</span>
          </Link>
          <Link to="/gaming" className="sidebar-item">
            <Gamepad size={20} />
            <span>Gaming</span>
          </Link>
          <Link to="/sports" className="sidebar-item">
            <Trophy size={20} />
            <span>Sports</span>
          </Link>
          <Link to="/news" className="sidebar-item">
            <Newspaper size={20} />
            <span>News</span>
          </Link>
        </div>
      </div>
    </>
  );
}

export default Navbar;