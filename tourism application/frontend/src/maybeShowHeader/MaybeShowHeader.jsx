import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';

const MaybeShowHeader = ({ children }) => {
  const location = useLocation();

  const [showHeader, setShowHeader] = useState(true); // Setăm default să afișăm header-ul

  useEffect(() => {
    console.log('this is the location: ', location);

    // Verificăm dacă URL-ul conține /manage-attractions/edit/ urmat de orice nume de județ
    if (
      location.pathname === '/' ||
      location.pathname === '/adminHome' ||
      location.pathname === '/adminLogin' ||
      location.pathname === '/manage-attractions/add' ||
      location.pathname === '/manage-attractions/edit' ||
      location.pathname === '/manage-attractions/delete' ||
      location.pathname === '/counties-edit' ||
      //location.pathname === '/counties' ||
      //location.pathname === '/county/Alba' ||
      //location.pathname === '/myAccount' ||
      location.pathname.includes('/manage-attractions/edit/')|| // Se ascunde header-ul dacă URL-ul conține /manage-attractions/edit/
      location.pathname.includes('/manage-attractions/delete/') // Se ascunde header-ul dacă URL-ul conține /manage-attractions/edit/
    ) {
      setShowHeader(false); // Nu afișa header-ul pe aceste pagini
    } else {
      setShowHeader(true); // Afișează header-ul pe celelalte pagini
    }
  }, [location]);

  return <div>{showHeader && children}</div>;
};

export default MaybeShowHeader;
