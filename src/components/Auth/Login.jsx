import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './Auth.css'; // Using the same CSS as Signup

function Login() {
  const [formData, setFormData] = useState({
    username: '',
    password: ''
  });
  const [message, setMessage] = useState({
    text: '',
    type: '' // 'success' or 'error'
  });
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMessage({ text: '', type: '' });

    try {
      const response = await fetch('http://localhost:8080/api/auth/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json', // Change this to 'application/json'
        },
        body: JSON.stringify({
          username: formData.username,
          password: formData.password
        }),
      });

      const data = await response.json(); // Parse JSON response

      if (response.ok) {
        setMessage({
          text: 'Login successful',
          type: 'success'
        });

        // Store user data (for example, username) in localStorage
        localStorage.setItem('user', JSON.stringify({ username: formData.username }));

        // Redirect to home ("/") after short delay
        setTimeout(() => {
          navigate('/');  // 👈 Redirects to Home page
        }, 1500);
      } else {
        setMessage({
          text: data.message || 'Login failed', // Use message from response
          type: 'error'
        });
      }
    } catch (err) {
      console.error('Login error:', err);
      setMessage({
        text: 'Server error',
        type: 'error'
      });
    }
  };

  return (
    <div className="auth-container">
      <div className="auth-box">
        <h2>Login</h2>
        {message.text && (
          <div className={`auth-message ${message.type}`}>
            {message.text}
          </div>
        )}
        <form onSubmit={handleSubmit} className="auth-form">
          <div className="form-group">
            <label htmlFor="username">Username</label>
            <input
              type="text"
              id="username"
              name="username"
              value={formData.username}
              onChange={handleChange}
              required
              placeholder="Enter your username"
            />
          </div>
          <div className="form-group">
            <label htmlFor="password">Password</label>
            <input
              type="password"
              id="password"
              name="password"
              value={formData.password}
              onChange={handleChange}
              required
              placeholder="Enter your password"
            />
          </div>
          <button type="submit" className="auth-button">Login</button>
        </form>
        <p className="auth-redirect">
          Don't have an account? <Link to="/signup">Sign Up</Link>
        </p>
      </div>
    </div>
  );
}

export default Login;
