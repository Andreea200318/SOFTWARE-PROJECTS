import React, { useEffect, useState } from 'react';
import './map.css';
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet';
import 'leaflet/dist/leaflet.css';
import axios from 'axios';

const Harta = () => {
  const [locations, setLocations] = useState([]);
  const [error, setError] = useState(null);
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  // Hardcoded locations for non-logged in users
  const defaultLocations = [
    {
      name: 'Castelul Peleș',
      coordinates: [45.3996, 25.5403],
      description: 'Un castel renumit din Munții Carpați.',
      url: 'https://link-castelul-peles'
    },
    {
      name: 'Delta Dunării',
      coordinates: [45.1679, 29.6581],
      description: 'Un loc unic, perfect pentru iubitorii de natură.',
      url: 'https://link-delta-dunarii'
    },
    {
      name: 'Palatul Parlamentului',
      coordinates: [44.4268, 26.1025],
      description: 'Un simbol al capitalei României, București.',
      url: 'https://link-palatul-parlamentului'
    },
    {
      name: 'Lacul Iezer',
      coordinates: [47.59806, 24.64620],
      description: 'Un lac glaciar pitoresc situat în Munții Rodnei, aproape de vârful Pietrosul Rodnei.',
      url: 'https://link-lacul-iezer'
    },
    {
      name: 'Mocănița din Maramureș',
      coordinates: [47.7097, 24.4318],
      description: 'Un tren cu aburi care oferă o călătorie pitorească prin peisaje tradiționale.',
      url: 'https://link-mocanita'
    },
    {
      name: 'Sfinxul din Bucegi',
      coordinates: [45.3963, 25.5168],
      description: 'O formațiune stâncoasă misterioasă cu aspect antropomorf, situată în Munții Bucegi.',
      url: 'https://link-sfinxul-bucegi'
    },
    {
      name: 'Cimitirul Vesel',
      coordinates: [47.9739, 23.6920],
      description: 'Un cimitir unic, renumit pentru crucile colorate și inscripțiile umoristice.',
      url: 'https://link-cimitirul-vesel'
    },
    {
      name: 'Salina Turda',
      coordinates: [46.5865, 23.7845],
      description: 'O mină de sare transformată într-o atracție turistică impresionantă.',
      url: 'https://link-salina-turda'
    },
    {
      name: 'Transfăgărășan',
      coordinates: [45.5982, 24.6165],
      description: 'Un drum spectaculos care traversează Munții Făgăraș, oferind peisaje de vis.',
      url: 'https://link-transfagarasan'
    }
  ];

  useEffect(() => {
    // Verifică starea de autentificare din localStorage
    const clientEmail = localStorage.getItem('email');
    setIsAuthenticated(!!clientEmail); // Set authentication status

    const fetchLocations = async () => {
      if (!clientEmail) {
        // If not authenticated, set default locations
        setLocations(defaultLocations);
        return;
      }

      try {
        const response = await axios.get(
          `http://localhost:8080/attraction-user-status/get-all-user-visited-locations/${clientEmail}`
        );
        // Formatting the fetched locations from the server
        const formattedLocations = response.data.map(location => ({
          name: location.name,
          coordinates: [location.latitude, location.longitude],
          description: location.locationName,
          url: location.url // Ensure url exists for each location if applicable
        }));
        setLocations(formattedLocations); // Set fetched locations
      } catch (err) {
        setError('A apărut o eroare la încărcarea locațiilor.');
        console.error(err);
        // În caz de eroare, afișăm locațiile default
        setLocations(defaultLocations);
      }
    };

    fetchLocations();

    // Adaugă un event listener pentru storage changes
    const handleStorageChange = () => {
      const newClientEmail = localStorage.getItem('email');
      setIsAuthenticated(!!newClientEmail ); // Update authentication state
      if (!newClientEmail ) {
        setLocations(defaultLocations); // Reset locations if logged out
      }
    };

    window.addEventListener('storage', handleStorageChange);

    // Cleanup on component unmount
    return () => {
      window.removeEventListener('storage', handleStorageChange);
    };
  }, []); // Empty dependency array means this runs once on component mount

  if (error) {
    return <div className="error">{error}</div>;
  }

  return (
    <div className="harta-container">
      <h2>Harta Turistică a României</h2>
      <p>
        {isAuthenticated 
          ? 'Locațiile tale vizitate:' 
          : 'Descoperă atracții turistice populare din România:'}
      </p>
      <div className="map">
        <MapContainer center={[45.0, 25.0]} zoom={6} style={{ width: '100%', height: '500px' }}>
          <TileLayer url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png" />
          {locations.map((location, index) => (
            <Marker key={index} position={location.coordinates}>
              <Popup>
                {location.url ? (
                  <a
                    href={location.url}
                    target="_blank"
                    rel="noopener noreferrer"
                    style={{ color: '#007bff', textDecoration: 'none', fontWeight: 'bold' }}
                  >
                    {location.name}
                  </a>
                ) : (
                  <strong>{location.name}</strong>
                )}
                <br />
                {location.description}
              </Popup>
            </Marker>
          ))}
        </MapContainer>
      </div>
    </div>
  );
};

export default Harta;
