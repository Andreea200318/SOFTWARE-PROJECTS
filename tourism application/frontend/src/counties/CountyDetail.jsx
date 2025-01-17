import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import './countyDetail.css';

const CountyDetail = ({ isAuthenticated }) => {
  const navigate = useNavigate();
  const { locationName } = useParams();
  const [attractions, setAttractions] = useState([]);
  const [loading, setLoading] = useState(true);
  const [selections, setSelections] = useState({});

  useEffect(() => {
    if (locationName) {
      axios
        .get(`http://localhost:8080/attractions/county/${locationName}`)
        .then((response) => {
          console.log('Datele atracțiilor:', response.data);
          setAttractions(response.data);
          setLoading(false);

          const initialSelections = {};
          response.data.forEach((attraction) => {
            initialSelections[attraction.name] = null; // Folosim numele atracției
          });
          setSelections(initialSelections);
        })
        .catch((error) => {
          console.error('Eroare la încărcarea atracțiilor turistice', error);
          setLoading(false);
        });
    } else {
      console.error('Parametrul locationName este undefined!');
      setLoading(false);
    }
  }, [locationName]);

  const handleSelectionChange = (attractionName, value) => {
    setSelections((prevSelections) => ({
      ...prevSelections,
      [attractionName]: value,
    }));
  };

  const handleSubmit = async () => {
    const clientEmail = localStorage.getItem('email');
    if (!clientEmail) {
        alert('Nu sunteți autentificat! Conectați-vă pentru a salva selecțiile.');
        return;
    }

    const payload = Object.entries(selections)
        .filter(([_, status]) => status !== null)
        .map(([attractionName, status]) => {
            const attraction = attractions.find((a) => a.name === attractionName);
            return {
                email: clientEmail,
                attractionName,
                locationName, // Trimitem locationName
                status: status === 'visited' ? 2 : status === 'wantToVisit' ? 1 : 3,
                latitude: attraction.latitude, // Trimitem latitudinea
                longitude: attraction.longitude, // Trimitem longitudinea
            };
        });

    console.log('Payload trimis către server:', payload);

    try {
        const response = await axios.post(
            'http://localhost:8080/attraction-user-status/add-visited',
            payload
        );

        if (response.status === 201) {
            alert('Toate selecțiile au fost salvate cu succes!');
        } else {
            alert('Eroare la salvarea selecțiilor. Verificați consola.');
        }
    } catch (error) {
        console.error('Eroare la trimiterea datelor:', error);
        alert('Au existat erori la salvarea selecțiilor. Verificați consola.');
    }
};

  if (loading) {
    return <div>Încărcare...</div>;
  }

  if (attractions.length === 0) {
    return <div>Nu au fost găsite atracții turistice pentru județul respectiv.</div>;
  }

  return (
    <div className="county-detail-container">
      <button className="back-button" onClick={() => navigate(-1)}>
        Înapoi
      </button>
      <h2>Atracții turistice din județul {locationName}</h2>

      {attractions.map((attraction, index) => (
        <div key={index} className="attraction-card">
          <div className="attraction-image-container">
            <h3>{attraction.name}</h3>
            <img src={attraction.image} alt={attraction.name} className="attraction-image" />
          </div>
          <div className="attraction-description">
            <p>{attraction.description}</p>
          </div>

          {isAuthenticated && (
            <div className="radio-options">
              <fieldset>
                <legend>Opțiuni:</legend>
                <label>
                  <input
                    type="radio"
                    name={`visit-${attraction.name}`}
                    value="visited"
                    checked={selections[attraction.name] === 'visited'}
                    onChange={() => handleSelectionChange(attraction.name, 'visited')}
                  />
                  Am vizitat
                </label>
                <label>
                  <input
                    type="radio"
                    name={`visit-${attraction.name}`}
                    value="wantToVisit"
                    checked={selections[attraction.name] === 'wantToVisit'}
                    onChange={() => handleSelectionChange(attraction.name, 'wantToVisit')}
                  />
                  Vreau să vizitez
                </label>
                <label>
                  <input
                    type="radio"
                    name={`visit-${attraction.name}`}
                    value="notInterested"
                    checked={selections[attraction.name] === 'notInterested'}
                    onChange={() => handleSelectionChange(attraction.name, 'notInterested')}
                  />
                  Nu vreau să vizitez
                </label>
              </fieldset>
            </div>
          )}
        </div>
      ))}

      {isAuthenticated && (
        <button className="submit-button" onClick={handleSubmit}>
          Salvează selecțiile
        </button>
      )}
    </div>
  );
};

export default CountyDetail;