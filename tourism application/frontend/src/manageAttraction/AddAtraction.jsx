import React, { useState, useEffect } from 'react';
import './AddAttraction.css';

const AddAttraction = () => {
  const [name, setName] = useState('');
  const [description, setDescription] = useState('');
  const [latitude, setLatitude] = useState('');
  const [longitude, setLongitude] = useState('');
  const [county, setCounty] = useState('');
  const [image, setImage] = useState(null);
  const [attractions, setAttractions] = useState([]);
  const [isEditing, setIsEditing] = useState(false);
  const [currentAttraction, setCurrentAttraction] = useState(null);

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

  // Handle form submission
  const handleSubmit = async (e) => {
    e.preventDefault();
    const formData = new FormData();
    formData.append('name', name);
    formData.append('description', description);
    formData.append('latitude', latitude);
    formData.append('longitude', longitude);
    formData.append('county', county);
    if (image) formData.append('image', image);

    try {
      const url = isEditing
        ? `http://localhost:8080/attractions/${currentAttraction.id}`
        : 'http://localhost:8080/attractions';
      const method = isEditing ? 'PUT' : 'POST';

      const response = await fetch(url, {
        method,
        body: formData,
      });

      if (response.ok) {
        alert(isEditing ? 'Attraction updated successfully!' : 'Attraction added successfully!');
        const updatedAttractions = await fetch('http://localhost:8080/attractions').then((res) =>
          res.json()
        );
        setAttractions(updatedAttractions);
        setName('');
        setDescription('');
        setLatitude('');
        setLongitude('');
        setCounty('');
        setImage(null);
        setIsEditing(false);
        setCurrentAttraction(null);
      }
    } catch (error) {
      console.error('Error adding/updating attraction:', error);
    }
  };

  // Handle edit
  const handleEdit = (attraction) => {
    setName(attraction.name);
    setDescription(attraction.description);
    setLatitude(attraction.coordinates[0]);
    setLongitude(attraction.coordinates[1]);
    setCounty(attraction.county);
    setIsEditing(true);
    setCurrentAttraction(attraction);
  };

  // Handle delete
  const handleDelete = async (id) => {
    try {
      const response = await fetch(`http://localhost:8080/attractions/${id}`, { method: 'DELETE' });
      if (response.ok) {
        alert('Attraction deleted successfully!');
        setAttractions(attractions.filter((attraction) => attraction.id !== id));
      }
    } catch (error) {
      console.error('Error deleting attraction:', error);
    }
  };

  return (
    <div className="add-attraction-container">
      <h2>{isEditing ? 'Edit Attraction' : 'Add New Attraction'}</h2>
      <form onSubmit={handleSubmit} className="attraction-form">
        <label>Name:</label>
        <input
          type="text"
          placeholder="Enter attraction name"
          value={name}
          onChange={(e) => setName(e.target.value)}
          required
        />
        <label>Description:</label>
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
        <label>County (Județ):</label>
        <input
          type="text"
          placeholder="Enter county name"
          value={county}
          onChange={(e) => setCounty(e.target.value)}
          required
        />
        <label>Upload Image:</label>
        <input
          type="file"
          accept="image/*"
          onChange={(e) => setImage(e.target.files[0])}
        />
        <button type="submit">{isEditing ? 'Update Attraction' : 'Add Attraction'}</button>
      </form>

      <h2>Existing Attractions</h2>
      <ul className="attractions-list">
        {attractions.map((attraction) => (
          <li key={attraction.id}>
            <div>
              <h3>{attraction.name}</h3>
              <p><strong>County (Județ):</strong> {attraction.county}</p>
              <p>{attraction.description}</p>
              {attraction.imageUrl && (
                <img
                  src={`http://localhost:8080/uploads/${attraction.imageUrl}`}
                  alt={attraction.name}
                />
              )}
              <p>
                <strong>Location:</strong> {attraction.coordinates[0]}, {attraction.coordinates[1]}
              </p>
            </div>
            <div className="attraction-actions">
              <button onClick={() => handleEdit(attraction)}>Edit</button>
              <button onClick={() => handleDelete(attraction.id)}>Delete</button>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default AddAttraction;
