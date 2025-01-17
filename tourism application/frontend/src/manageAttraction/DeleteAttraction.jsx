import React, { useState, useEffect } from 'react';
import './deleteAttraction.css';

const DeleteAttraction = () => {
  const [attractions, setAttractions] = useState([]);

  // Fetch existing attractions
  useEffect(() => {
    const fetchAttractions = async () => {
      try {
        const response = await fetch('http://localhost:8080/attractions');
        const data = await response.json();
        setAttractions(data);
      } catch (error) {
        console.error('Error fetching attractions:', error);
      }
    };

    fetchAttractions();
  }, []);

  // Handle delete
  const handleDelete = async (id) => {
    try {
      const response = await fetch(`http://localhost:8080/attractions/${id}`, {
        method: 'DELETE',
      });
      if (response.ok) {
        alert('Attraction deleted successfully!');
        setAttractions(attractions.filter((attraction) => attraction.id !== id));
      }
    } catch (error) {
      console.error('Error deleting attraction:', error);
    }
  };

  return (
    <div className="delete-attraction-container">
      <h2>Delete Attractions</h2>
      {attractions.length === 0 ? (
        <p>No attractions available.</p>
      ) : (
        <ul className="attractions-list">
          {attractions.map((attraction) => (
            <li key={attraction.id} className="attraction-item">
              <div>
                <h3>{attraction.name}</h3>
                <p>
                  <strong>County:</strong> {attraction.countyName}
                </p>
                <p>{attraction.description}</p>
                {attraction.imageUrl && (
                  <img
                    src={`http://localhost:8080/uploads/${attraction.imageUrl}`}
                    alt={attraction.name}
                    className="attraction-image"
                  />
                )}
                {attraction.coordinates && (
                  <p>
                    <strong>Location:</strong> {attraction.coordinates[0]},{' '}
                    {attraction.coordinates[1]}
                  </p>
                )}
              </div>
              <button
                className="delete-button"
                onClick={() => handleDelete(attraction.id)}
              >
                Delete
              </button>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default DeleteAttraction;
