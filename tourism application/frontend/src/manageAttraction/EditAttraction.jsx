import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import './editAttraction.css';

const EditAttractions = () => {
  const { countyName } = useParams(); // Obține numele județului din URL
  const [attractions, setAttractions] = useState([]);
  const [editAttraction, setEditAttraction] = useState(null);

  useEffect(() => {
    // Fetch atracțiile din județ (exemplu API)
    const fetchAttractions = async () => {
      try {
        const response = await fetch(`http://localhost:8080/attractions/${countyName}`);
        const data = await response.json();
        setAttractions(data);
      } catch (error) {
        console.error('Eroare la obținerea atracțiilor:', error);
      }
    };

    fetchAttractions();
  }, [countyName]);

  const handleEditClick = (attraction) => {
    setEditAttraction(attraction);
  };

  const handleSave = async () => {
    try {
      const response = await fetch(`http://localhost:8080/attractions/${editAttraction.id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(editAttraction),
      });

      if (response.ok) {
        alert('Atracție actualizată cu succes!');
        setEditAttraction(null);
        const updatedAttractions = await fetch(
          `http://localhost:8080/attractions/${countyName}`
        ).then((res) => res.json());
        setAttractions(updatedAttractions);
      }
    } catch (error) {
      console.error('Eroare la actualizarea atracției:', error);
    }
  };

  return (
    <div className="edit-attractions-container">
      <h2>Atracții în județul {countyName}</h2>
      {attractions.length === 0 ? (
        <p className="no-attractions-message">
          Nu există atracții disponibile pentru județul {countyName}. Adaugă unele atracții pentru a începe!
        </p>
      ) : !editAttraction ? (
        <ul className="attractions-list">
          {attractions.map((attraction) => (
            <li key={attraction.id} className="attraction-item">
              <h3>{attraction.name}</h3>
              <p>{attraction.description}</p>
              <img src={attraction.imageUrl} alt={attraction.name} />
              <button onClick={() => handleEditClick(attraction)}>Edit</button>
            </li>
          ))}
        </ul>
      ) : (
        <div className="edit-form">
          <h3>Editare Atracție</h3>
          <label>
            Nume:
            <input
              type="text"
              value={editAttraction.name}
              onChange={(e) =>
                setEditAttraction({ ...editAttraction, name: e.target.value })
              }
            />
          </label>
          <label>
            Descriere:
            <textarea
              value={editAttraction.description}
              onChange={(e) =>
                setEditAttraction({ ...editAttraction, description: e.target.value })
              }
            />
          </label>
          <label>
            Imagine URL:
            <input
              type="text"
              value={editAttraction.imageUrl}
              onChange={(e) =>
                setEditAttraction({ ...editAttraction, imageUrl: e.target.value })
              }
            />
          </label>
          <button onClick={handleSave}>Salvează</button>
          <button onClick={() => setEditAttraction(null)}>Anulează</button>
        </div>
      )}
    </div>
  );
  
};

export default EditAttractions;
