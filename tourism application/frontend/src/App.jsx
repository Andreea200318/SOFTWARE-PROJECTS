import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from './home/Home';
import Counties from './counties/Counties';
import TopAttractions from './topAttractions/TopAttractions';
import About from './about/About';
import Map from './map/Map';
import Login from './login/Login';
import CountyDetail from './counties/CountyDetail';
import WelcomePage from './welcomePage/WelcomePage';
import AdminLogin from './adminLogin/AdminLogin';
import AdminHome from './adminHome/AdminHome';
import MyAccount from './myAccount/MyAccount';
import MyAccountvis from './myAccount/MyAccountvis';
import Register from './register/Register';
import Header from './header/Header';
import Footer from './footer/Footer';
import MaybeShowHeader from './maybeShowHeader/MaybeShowHeader';
import CountiesLog from './countiesLog/CountiesLog';
import CountyAttr from './countyAttr/CountyAttr';

import AddAtraction from './adminHome/manageAttraction/AddAtraction';
import DeleteAttraction from './adminHome/manageAttraction/DeleteAttraction';
import EditAttraction from './adminHome/manageAttraction/EditAttraction';
import EditCountyAttractions from './adminHome/manageAttraction/EditCountyAttractions';
import EditSelectedAttraction from './adminHome/manageAttraction/EditSelectedAttraction';
import DeleteCountyAttractions from './adminHome/manageAttraction/DeleteCountyAttractions';

const App = () => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem('authToken');
    if (token) {
      setIsAuthenticated(true); // Dacă există token, utilizatorul este autentificat
    } else {
      localStorage.removeItem('email'); // Șterge email-ul dacă nu există token
    }
  }, []); // Execută doar la montarea componentei

  const handleLogin = () => {
    setIsAuthenticated(true);
  };

  const handleLogout = () => {
    localStorage.removeItem('authToken'); // Șterge token-ul
    localStorage.removeItem('email'); // Șterge email-ul
    setIsAuthenticated(false); // Setează starea de autentificare ca false
  };

  return (
    <Router>
      <div>
        <MaybeShowHeader>
          <Header isAuthenticated={isAuthenticated} onLogout={handleLogout} />
        </MaybeShowHeader>

        <Routes>
          <Route path="/" element={<WelcomePage />} />
          <Route path="/home" element={<Home />} />
          <Route path="/counties" element={<Counties />} />
          <Route
            path="/county/:locationName"
            element={<CountyDetail
              isAuthenticated={isAuthenticated}
              onLogin={handleLogin}
              onLogout={handleLogout}
            />}
          />
          <Route path="/topAttractions" element={<TopAttractions />} />
          <Route path="/about" element={<About />} />
          <Route path="/map" element={<Map />} />
          
          <Route path="/login" element={<Login onLogin={handleLogin} />} />
          <Route path="/register" element={<Register />} />
          <Route path="/adminLogin" element={<AdminLogin />} />
          <Route path="/adminHome" element={<AdminHome />} />
          <Route path="/countiesLog" element={<CountiesLog />} />
          <Route path="/myAccount" element={<MyAccount />} />
          <Route path="/myAccountvis/:countyName" element={<MyAccountvis />} />
          <Route path="/county-attractions" element={<CountyAttr />} />

          <Route path="/manage-attractions/add" element={<AddAtraction />} />
          <Route path="/manage-attractions/delete" element={<DeleteAttraction />} />
          <Route path="/manage-attractions/delete/:locationName" element={<DeleteCountyAttractions />} />

          {/* Rutele pentru editare județe și atracții */}
          <Route path="/manage-attractions/edit" element={<EditAttraction />} />
          <Route path="/manage-attractions/edit/:locationName" element={<EditCountyAttractions />} />
          <Route path="/manage-attractions/edit/:locationName/:id" element={<EditSelectedAttraction />} />
        </Routes>

        <MaybeShowHeader>
          <Footer />
        </MaybeShowHeader>
      </div>
    </Router>
  );
};

export default App;