import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import './editSelectedAttraction.css';

const EditSelectedAttraction = () => {
  
  const { locationName, id } = useParams(); // Preluăm locationName și id din URL
  const navigate = useNavigate();
  const [name, setName] = useState('');
  const [description, setDescription] = useState('');
  const [latitude, setLatitude] = useState('');
  const [longitude, setLongitude] = useState('');
  const [location, setLocation] = useState('');
  const [image, setImage] = useState('');
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  useEffect(() => {
    const fetchAttraction = async () => {
      try {
        const response = await fetch(`http://localhost:8080/attractions/${id}`);
        const data = await response.json();
        if (data) {
          setName(data.name);
          setDescription(data.description);
          setLatitude(data.latitude);
          setLongitude(data.longitude);
          setLocation(data.location); // Setezi locația
          setImage(data.image);
        }
      } catch (error) {
        console.error('Error fetching attraction data:', error);
      }
    };

    fetchAttraction();
  }, [id]);

  const handleEditAttraction = async (e) => {
    e.preventDefault();
  
    const attractionData = {
      name,
      location, // Locația rămâne aceeași
      description,
      latitude: parseFloat(latitude),
      longitude: parseFloat(longitude),
      image,
    };
  
    try {
      const response = await fetch(`http://localhost:8080/attractions/update/${id}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(attractionData),
      });
  
      if (response.ok) {
        setSuccess('Attraction updated successfully!');
        setError('');
        //navigate(`/attractions/${id}`);  // Navighează utilizatorul înapoi la pagina atracției actualizate
      } else {
        const data = await response.json();
        setError(data.message || 'Failed to update attraction.');
        setSuccess('');
      }
    } catch (err) {
      setError('An error occurred. Please try again later.');
      setSuccess('');
    }
  };

  return (
    <div className="edit-attraction-container">
      <h2>Edit Attraction</h2>
      <button 
        className="back-button" 
        onClick={() => navigate(-1)} // Navighează la pagina anterioară
      >
        Înapoi
      </button>
      {error && <div className="error">{error}</div>}
      {success && <div className="success">{success}</div>}

      <form onSubmit={handleEditAttraction} className="attraction-form">
        <label>Nume:</label>
        <input
          type="text"
          placeholder="Enter attraction name"
          value={name}
          onChange={(e) => setName(e.target.value)}
          required
        />
        <label>Descriere:</label>
        <textarea
          placeholder="Enter attraction description"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
          required
        ></textarea>
        <label>Latitude:</label>
        <input
          type="number"
          step="any"
          placeholder="Enter latitude"
          value={latitude}
          onChange={(e) => setLatitude(e.target.value)}
          required
        />
        <label>Longitude:</label>
        <input
          type="number"
          step="any"
          placeholder="Enter longitude"
          value={longitude}
          onChange={(e) => setLongitude(e.target.value)}
          required
        />
        <label>Județ:</label>
        {/* Afișezi locația dar nu o poți modifica */}
        <input 
          type="text" 
          value={location} 
          readOnly 
        />
        <label>Imagine URL:</label>
        <input
          type="text"
          placeholder="Enter image URL"
          value={image}
          onChange={(e) => setImage(e.target.value)}
        />
        <button type="submit">Update atracție</button>
      </form>
    </div>
  );
};

export default EditSelectedAttraction;
