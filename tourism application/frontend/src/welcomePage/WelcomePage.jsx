// frontend/src/welcome/WelcomePage.jsx
import React from 'react';
import { useNavigate } from 'react-router-dom';
import './welcomePage.css'; // Adaugă stiluri pentru pagina de bun venit
import romaniaMap from './romania.png';

const WelcomePage = () => {
  const navigate = useNavigate();

  const handleUserClick = () => {
    navigate('/home'); // Redirecționează către pagina de home pentru utilizatori
  };

  const handleAdminClick = () => {
    navigate('/adminLogin'); // Redirecționează către pagina de autentificare admin
  };

  return (
    <div className="welcome-page">
      <h1>Bine ai venit pe România la pas!</h1>
      <p>Alege cum dorești să accesezi site-ul:</p>
      <div className="button-group">
        <button onClick={handleUserClick} className="btn user-btn">
          Intră ca Utilizator
        </button>
        <button onClick={handleAdminClick} className="btn admin-btn">
          Intră ca Admin
        </button>
      </div>
    </div>
  );
};

export default WelcomePage;