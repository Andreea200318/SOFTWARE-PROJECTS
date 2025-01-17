import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './login.css'; // Stilurile frontend-ului

const Login = ({ onLogin }) => {
  const navigate = useNavigate();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const handleLogin = async (e) => {
    e.preventDefault();
  
    const loginData = { email, password };
  
    try {
      const response = await fetch('http://localhost:8080/clients/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(loginData),
      });
  
      if (response.ok) {
        const data = await response.json();
        localStorage.setItem("authToken", data.authToken); // Salvează token-ul
        localStorage.setItem("email", data.email); // Salvează email-ul
        onLogin(); // Setează autentificarea în App
        navigate('/home');
      } else {
        setError('Autentificare eșuată!');
      }
    } catch {
      setError('A apărut o eroare. Te rugăm să încerci mai târziu.');
    }
  };

  return (
    <div className="login-page">
      <h1>Login</h1>
      {error && <div className="error">{error}</div>}
      <form onSubmit={handleLogin} className="login-form">
        <label htmlFor="email">Email:</label>
        <input
          type="email"
          id="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        />
        <label htmlFor="password">Password:</label>
        <input
          type="password"
          id="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />
        <button type="submit">Login</button>
      </form>
    </div>
  );
};

export default Login;