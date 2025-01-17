import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './AddAttraction.css';

const AddAttraction = () => {
  const navigate = useNavigate();

  const [name, setName] = useState('');
  const [description, setDescription] = useState('');
  const [latitude, setLatitude] = useState('');
  const [longitude, setLongitude] = useState('');
  const [location, setLocation] = useState('');
  const [image, setImage] = useState(''); // Înlocuim cu URL-ul imaginii
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  const counties = [
    'București',
    'Alba',
    'Arad',
    'Argeș',
    'Bacău',
    'Bihor',
    'Bistrița-Năsăud',
    'Botoșani',
    'Brașov',
    'Brăila',
    'Buzău',
    'Caraș-Severin',
    'Călărași',
    'Cluj',
    'Constanța',
    'Covasna',
    'Dâmbovița',
    'Dolj',
    'Galați',
    'Giurgiu',
    'Gorj',
    'Harghita',
    'Hunedoara',
    'Ialomița',
    'Iași',
    'Ilfov',
    'Maramureș',
    'Mehedinți',
    'Mureș',
    'Neamț',
    'Olt',
    'Prahova',
    'Satu Mare',
    'Sălaj',
    'Sibiu',
    'Suceava',
    'Teleorman',
    'Timiș',
    'Tulcea',
    'Vaslui',
    'Vâlcea',
    'Vrancea',
  ];

  const handleAddAttraction = async (e) => {
    e.preventDefault();

    const attractionData = {
      name,
      location,
      description,
      latitude: parseFloat(latitude),
      longitude: parseFloat(longitude),
      image, // Trimitem URL-ul imaginii
    };

    try {
      const response = await fetch('http://localhost:8080/attractions/store', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(attractionData),
      });

      if (response.ok) {
        setSuccess('Atractie adaugata cu succes!');
        setError('');
        setName('');
        setLocation('');
        setDescription('');
        setLatitude('');
        setLongitude('');
        setImage(''); // Resetăm URL-ul
      } else if (response.status === 409) {
        setError('Atractia deja exista!');
        setSuccess('');
      } else {
        const data = await response.json();
        setError(data.message || 'Failed to add attraction.');
        setSuccess('');
      }
    } catch (err) {
      setError('An error occurred. Please try again later.');
      setSuccess('');
    }
  };

  return (
    <div className="add-attraction-container">
      <h2>Add New Attraction</h2>
      <button 
        className="back-button" 
        onClick={() => navigate(-1)} // Navighează la pagina anterioară
      >
        Înapoi
      </button>

      {error && <div className="error">{error}</div>}
      {success && <div className="success">{success}</div>}

      <form onSubmit={handleAddAttraction} className="attraction-form">
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
        <select
          value={location}
          onChange={(e) => setLocation(e.target.value)}
          required
        >
          <option value="" disabled>
            Select a county
          </option>
          {counties.map((locationName, index) => (
            <option key={index} value={locationName}>
              {locationName}
            </option>
          ))}
        </select>
        <label>Imagine URL:</label>
        <input
          type="text"
          placeholder="Enter image URL"
          value={image}
          onChange={(e) => setImage(e.target.value)}
        />
        <button type="submit">Adauga Atractie</button>
      </form>
    </div>
  );
};

export default AddAttraction;
