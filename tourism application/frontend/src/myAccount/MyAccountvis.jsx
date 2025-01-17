import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';
import './MyAccountVis.css';

const MyAccountVis = () => {
  const [visitedAttractions, setVisitedAttractions] = useState([]);
  const [toVisitAttractions, setToVisitAttractions] = useState([]);
  const [notInterestedAttractions, setNotInterestedAttractions] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const { locationName } = useParams();
  const navigate = useNavigate();
  const clientEmail = localStorage.getItem('email');
  const location = locationName || localStorage.getItem('locationName');

  const fetchData = async () => {
    if (!clientEmail) {
      setError('Nu sunteți autentificat. Conectați-vă pentru a vedea datele.');
      setLoading(false);
      return;
    }

    try {
      const response = await axios.get(
        `http://localhost:8080/attraction-user-status/get-all-user-visited-attractions/${clientEmail}/${location}`
      );

      setVisitedAttractions(response.data.visited || []);
      setToVisitAttractions(response.data.toVisit || []);
      setNotInterestedAttractions(response.data.notInterested || []);
      setLoading(false);
    } catch (err) {
      setError('A apărut o eroare la încărcarea datelor.');
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
  }, [clientEmail, location]);

  const handleDelete = async (attractionName, status) => {
    try {
        await axios.delete(
            `http://localhost:8080/attraction-user-status/delete/${clientEmail}/${location}/${attractionName}`
        );

        // Update the local state based on which list the attraction was in
        switch (status) {
            case 'visited':
                setVisitedAttractions(prev => prev.filter(attr => attr !== attractionName));
                break;
            case 'toVisit':
                setToVisitAttractions(prev => prev.filter(attr => attr !== attractionName));
                break;
            case 'notInterested':
                setNotInterestedAttractions(prev => prev.filter(attr => attr !== attractionName));
                break;
            default:
                break;
        }
    } catch (err) {
        setError('A apărut o eroare la ștergerea atracției.');
        console.error('Eroare la ștergere:', err);
    }
};

  const handleLocationClick = () => {
    navigate(`/county/${location}`);
  };
  
  if (loading) {
    return <div className="loading-container">Încărcare...</div>;
  }

  if (error) {
    return <div className="error-container">{error}</div>;
  }

  return (
    <div className="my-account-page">
      <h2 onClick={handleLocationClick} style={{ cursor: 'pointer' }}>
        My Account - {location}
      </h2>

      <div className="county-lists">
        <div className="list visited-list">
          <h2>Atracții vizitate</h2>
          {visitedAttractions.length > 0 ? (
            <ul>
              {visitedAttractions.map((attraction, index) => (
                <li key={index} className="attraction-item-static">
                  <span className="attraction-name">{attraction}</span>
                  <button 
                    className="delete-button"
                    onClick={() => handleDelete(attraction, 'visited')}
                  >
                    ×
                  </button>
                </li>
              ))}
            </ul>
          ) : (
            <p className="no-items">Nicio atracție vizitată.</p>
          )}
        </div>

        <div className="list to-visit-list">
          <h2>Atracții de vizitat</h2>
          {toVisitAttractions.length > 0 ? (
            <ul>
              {toVisitAttractions.map((attraction, index) => (
                <li key={index} className="attraction-item-static">
                  <span className="attraction-name">{attraction}</span>
                  <button 
                    className="delete-button"
                    onClick={() => handleDelete(attraction, 'toVisit')}
                  >
                    ×
                  </button>
                </li>
              ))}
            </ul>
          ) : (
            <p className="no-items">Nicio atracție de vizitat.</p>
          )}
        </div>

        <div className="list not-to-visit-list">
          <h2>Atracții care nu te interesează</h2>
          {notInterestedAttractions.length > 0 ? (
            <ul>
              {notInterestedAttractions.map((attraction, index) => (
                <li key={index} className="attraction-item-static">
                  <span className="attraction-name">{attraction}</span>
                  <button 
                    className="delete-button"
                    onClick={() => handleDelete(attraction, 'notInterested')}
                  >
                    ×
                  </button>
                </li>
              ))}
            </ul>
          ) : (
            <p className="no-items">Nicio atracție marcată ca neinteresantă.</p>
          )}
        </div>
      </div>
    </div>
  );
};

export default MyAccountVis;