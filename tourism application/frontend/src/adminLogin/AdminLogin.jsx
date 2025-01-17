import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './adminLogin.css'; // Stiluri CSS pentru login

const AdminLoginPage = () => {
  const navigate = useNavigate();
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    // setError(''); // Resetare erori
    const loginData = {
      username: username,
      password: password,
  };

  try {
      const response = await fetch('http://localhost:8080/admins/login', {
          method: 'POST',
          headers: {
              'Content-Type': 'application/json',
          },
          body: JSON.stringify(loginData),
      });
      //console.log(response.status);

      if (response.ok) {
        navigate('/adminHome'); // Redirecționează către pagina admin
      } 
      else {
        setError(data.message || 'Autentificare eșuată!');
      }
    } catch (err) {
      setError('A apărut o eroare. Te rugăm să încerci mai târziu.');
    }
  };

  return (
    <div className="login-admin-page">
      <h1>Autentificare Admin</h1>
      {error && <div className="error">{error}</div>}
      <form onSubmit={handleSubmit} className="login-admin-form">
        <label htmlFor="username">Username:</label>
        <input
          type="text"
          id="username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
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
        <button type="submit">Login Admin</button>
      </form>
    </div>
  );
};

export default AdminLoginPage;