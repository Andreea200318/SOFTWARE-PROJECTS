import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import AdminLogin from './adminLogin/AdminLogin';
import AdminHome from './adminHome/AdminHome';

const AppRoutes = () => (
  <Router>
    <Routes>
      <Route path="/" element={<AdminLogin />} />
      <Route path="/adminHome" element={<AdminHome />} />
    </Routes>
  </Router>
);

export default AppRoutes;