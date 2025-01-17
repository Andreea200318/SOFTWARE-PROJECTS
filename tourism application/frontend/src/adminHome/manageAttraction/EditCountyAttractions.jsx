import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import './editCountyAttractions.css';

const EditCountyAttractions = () => {
  const { locationName } = useParams();  // Obține județul din URL
  const navigate = useNavigate();
  const [attractions, setAttractions] = useState([]);

  useEffect(() => {
    const fetchAttractions = async () => {
      try {
        // Modificăm cererea pentru a include județul în URL
        const response = await fetch(`http://localhost:8080/attractions/county/${locationName}`);
        const data = await response.json();
        setAttractions(data);  // Stocăm atracțiile pentru județul respectiv
      } catch (error) {
        console.error('Error fetching attractions:', error);
      }
    };

    // Se apelează fetchAttractions doar când județul este definit
    if (locationName) {
      fetchAttractions();
    }
  }, [locationName]);  // Reexecutăm când `locationName` se schimbă

  const handleEdit = (id, locationName) => {
    navigate(`/manage-attractions/edit/${locationName}/${id}`);
  };

  return (
    <div className="edit-attraction-container">
      <h2>Atracții turistice din județul {locationName}</h2>
     
      <button 
        className="back-button" 
        onClick={() => navigate(-1)} // Navighează la pagina anterioară
      >
        Înapoi
      </button>
      {attractions.length === 0 ? (
        <p>Nu sunt atracții disponibile pentru acest județ.</p>
      ) : (
        <ul className="attractions-list">
          {attractions.map((attraction) => (
            <li key={attraction.idAttraction} className="attraction-item">
              <div>
                <h3>{attraction.name}</h3>
                <p>
                  <strong>County:</strong> {attraction.location}
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
                className="edit-button"
                onClick={() => handleEdit(attraction.idAttraction, attraction.location)}
              >
                Edit
              </button>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default EditCountyAttractions;
