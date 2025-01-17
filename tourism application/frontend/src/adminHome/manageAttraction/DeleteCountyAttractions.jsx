import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import './deleteCountyAttractions.css';

const DeleteCountyAttractions = () => {
  const { locationName } = useParams();  // Obținem județul din URL
  const [attractions, setAttractions] = useState([]);
  const navigate = useNavigate();

  // Fetch atracțiile din județul specificat
  useEffect(() => {
    const fetchAttractions = async () => {
      try {
        const response = await fetch(`http://localhost:8080/attractions/county/${locationName}`);
        const data = await response.json();
        setAttractions(data);
      } catch (error) {
        console.error('Error fetching attractions:', error);
      }
    };

    if (locationName) {
      fetchAttractions();
    }
  }, [locationName]);

  // Handle delete
  const handleDelete = async (id) => {
    try {
      const response = await fetch(`http://localhost:8080/attractions/${id}`, {
        method: 'DELETE',
      });
      if (response.ok) {
        alert('Attraction deleted successfully!');
        setAttractions(attractions.filter((attraction) => attraction.idAttraction !== id));
      } else {
        const message = await response.text();
        alert(`Failed to delete: ${message}`);
      }
    } catch (error) {
      console.error('Error deleting attraction:', error);
    }
  };

  return (
    <div className="delete-attraction-container">
      <h2>Șterge atracție din {locationName}</h2>
      <button 
        className="back-button" 
        onClick={() => navigate(-1)} // Navighează la pagina anterioară
      >
        Înapoi
      </button>
      {attractions.length === 0 ? (
        <p>Nu sunt atracții în acest județ.</p>
      ) : (
        <ul className="attractions-list">
          {attractions.map((attraction) => (
            <li key={attraction.idAttraction} className="attraction-item">
              <div>
                <h3>{attraction.name}</h3>
                <p>
                  <strong>Județ:</strong> {attraction.location}
                </p>
                <p>{attraction.description}</p>
                {attraction.image && (
                  <img
                    src={attraction.image}
                    alt={attraction.name}
                    className="attraction-image"
                  />
                )}
              </div>
              <button
                className="delete-button"
                onClick={() => handleDelete(attraction.idAttraction)}
              >
                Șterge
              </button>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default DeleteCountyAttractions;
