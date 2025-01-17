import React from 'react';
import { useNavigate } from 'react-router-dom';
import './adminHome.css'; // Stiluri pentru pagina de admin home

const AdminHome = () => {
  const navigate = useNavigate();

  const handleLogoutClick = () => {
    navigate('/'); // Redirecționează înapoi la pagina principală
  };

  const handleAddClick = () => {
    navigate('/manage-attractions/add');
  };

  const handleEditClick = () => {
    navigate('/manage-attractions/edit'); // Redirecționează la pagina de editare județe
  };

  const handleDeleteClick = () => {
    navigate('/manage-attractions/delete');
  };

  return (
    <div className="admin-home">
      <h1>Bine ai venit, Admin!</h1>
      <p>Aici poți administra atracțiile turistice.</p>
      <div className="admin-actions">
        <button onClick={handleAddClick} className="btn action-btn">
          Add Attraction
        </button>
        <button onClick={handleEditClick} className="btn action-btn">
          Edit Attraction
        </button>
        <button onClick={handleDeleteClick} className="btn action-btn">
          Delete Attraction
        </button>
      </div>
      <button onClick={handleLogoutClick} className="btn logout-btn">
        Logout
      </button>
    </div>
  );
};

export default AdminHome;