import React, { createContext, useState, useEffect } from 'react';

export const AuthContext = createContext();

const AuthProvider = ({ children }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  useEffect(() => {
    // Verificăm dacă există un token în localStorage la fiecare încărcare a aplicației
    const token = localStorage.getItem('authToken');
    setIsAuthenticated(!!token); // Dacă există token, setăm ca autentificat
  }, []);

  const logout = () => {
    localStorage.removeItem('authToken'); // Șterge token-ul
    localStorage.removeItem('email'); // Șterge email-ul
    setIsAuthenticated(false); // Setează starea de autentificare ca false
  };
  
  return (
    <AuthContext.Provider value={{ isAuthenticated, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export default AuthProvider;
