import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import './attractionsByCounty.css';

const AttractionsByCounty = () => {
  const { countyName } = useParams(); // Obține numele județului din URL
  const [attractions, setAttractions] = useState([]);
<<<<<<< HEAD
=======
  const [loading, setLoading] = useState(true); // Stare pentru încărcare
  const [error, setError] = useState(null); // Stare pentru erori
>>>>>>> origin/master
  const navigate = useNavigate();

  useEffect(() => {
    // Fetch atracțiile din județul selectat
    const fetchAttractions = async () => {
      try {
        const response = await fetch(`http://localhost:8080/attractions/${countyName}`);
<<<<<<< HEAD
        const data = await response.json();
        setAttractions(data);
      } catch (error) {
        console.error('Eroare la obținerea atracțiilor:', error);
=======
        if (!response.ok) {
          throw new Error(`Eroare la obținerea atracțiilor: ${response.statusText}`);
        }
        const data = await response.json();
        setAttractions(data);
      } catch (error) {
        setError(error.message);
      } finally {
        setLoading(false);
>>>>>>> origin/master
      }
    };

    fetchAttractions();
  }, [countyName]);

  const handleEditClick = (attractionId) => {
    navigate(`/manage-attractions/edit/${countyName}/${attractionId}`); // Navighează la pagina de editare pentru atracția selectată
  };

<<<<<<< HEAD
  return (
    <div className="attractions-container">
      <h2>Atracții din județul {countyName}</h2>
      {attractions.length === 0 ? (
        <p>Nu există atracții pentru județul {countyName}.</p>
=======
  if (loading) {
    return <div className="loading">Se încarcă atracțiile...</div>;
  }

  if (error) {
    return <div className="error">Eroare: {error}</div>;
  }

  return (
    <div className="attractions-container">
      <h2>Atracțiile din județul {countyName}</h2>
      {attractions.length === 0 ? (
        <p>Nu sunt atracții de editat momentan pentru județul {countyName}!</p>
>>>>>>> origin/master
      ) : (
        <div className="attractions-list">
          {attractions.map((attraction) => (
            <div key={attraction.id} className="attraction-card">
<<<<<<< HEAD
              <img src={attraction.imageUrl} alt={attraction.name} />
              <div className="attraction-details">
                <h3>{attraction.name}</h3>
                <p>{attraction.description}</p>
                <button onClick={() => handleEditClick(attraction.id)}>Editează</button>
=======
              <img src={attraction.imageUrl} alt={attraction.name} className="attraction-image" />
              <div className="attraction-details">
                <h3>{attraction.name}</h3>
                <p>{attraction.description}</p>
                <button
                  className="edit-button"
                  onClick={() => handleEditClick(attraction.id)}
                >
                  Editează
                </button>
>>>>>>> origin/master
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default AttractionsByCounty;
