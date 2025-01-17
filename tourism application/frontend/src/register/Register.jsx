import React, { useState } from 'react';
import './register.css'; // Stilurile frontend-ului

const Register = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [emailUsed, setEmailUsed] = useState(false); // State pentru a ști dacă email-ul este deja folosit

  const handleRegister = async (e) => {
    e.preventDefault();

    const registerData = {
      email: email,
      password: password, // Asigură-te că cheia este "password"
    };

    try {
      const response = await fetch('http://localhost:8080/clients/register', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(registerData),
      });

      if (response.ok) {
        const data = await response.json();
        setSuccess('Registration successful! You can now log in.');
        setError('');
        setEmail('');
        setPassword('');
        setEmailUsed(false); // Resetează starea email-ului folosit
      } else if (response.status === 409) {
        setError('Email already in use');
        setSuccess('');
        setEmailUsed(true); // Email-ul este deja folosit
      } else {
        const data = await response.json();
        setError(data.message || 'Registration failed. Please try again.');
        setSuccess('');
        setEmailUsed(false); // Resetarea în cazul altor erori
      }
    } catch (err) {
      setError('An error occurred. Please try again later.');
      setSuccess('');
      setEmailUsed(false); // Resetarea în caz de eroare
    }
  };

  // Funcție pentru a redirecționa utilizatorul la login
  const navigateToLogin = () => {
    // Poți folosi React Router pentru a naviga către login (presupunând că ai instalat react-router)
    window.location.href = '/login'; // Sau poți folosi un Link din react-router
  };

  return (
    <div className="register-page">
      <h1>Register</h1>
      {error && <div className="error">{error}</div>}
      {success && <div className="success">{success}</div>}
      <form onSubmit={handleRegister} className="register-form">
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
        <button type="submit">Register</button>
      </form>

      {/* Afișează butonul doar dacă email-ul este deja utilizat */}
      {emailUsed && (
        <button onClick={navigateToLogin} className="go-to-login-btn">
          Ai deja un cont? Conectează-te aici!
        </button>
      )}
    </div>
  );
};

export default Register;
