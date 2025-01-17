import React from 'react';
import { Link, useNavigate } from 'react-router-dom'; // Importă useNavigate
import logo from './headerAssets/logo.png';
import './Header.css';

const Header = ({ isAuthenticated, onLogout }) => {
  const navigate = useNavigate(); // Inițializează navigarea

  const handleLogout = () => {
    // Șterge datele din localStorage
    localStorage.removeItem('client_id');
    localStorage.removeItem('email');
    // Alte date care trebuie șterse...
    
    onLogout(); // Funcția existentă de logout
    navigate('/home'); // Navigare către pagina principală
  };
  return (
    <header>
      <div className="header-content">
        <nav className="left-links">
          <Link to="/home">Acasă</Link>
          <Link to="/counties">Județele</Link>
          <Link to="/map">Hartă</Link>
        </nav>

        <div className="logo">
          <Link to="/">
            <img src={logo} alt="România Noastră" />
          </Link>
        </div>

        <nav className="right-links">
          
          <Link to="/about">Despre</Link>
          {isAuthenticated ? (
            <>
              <Link to="/myAccount">My Account</Link>
              <button onClick={handleLogout}>Logout</button> {/* Folosește handleLogout */}
            </>
          ) : (
            <>
              <Link to="/login">Login</Link>
              <Link to="/register">Register</Link>
            </>
          )}
        </nav>
      </div>
    </header>
  );
};

export default Header;