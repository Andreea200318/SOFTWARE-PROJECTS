import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './deleteAttraction.css';

const countiesData = [
  { name: 'Alba' },
  { name: 'Arad' },
  { name: 'Argeș' },
  { name: 'Bacău' },
  { name: 'Bihor' },
  { name: 'Bistrița-Năsăud' },
  { name: 'Botoșani' },
  { name: 'Brăila' },
  { name: 'Brașov' },
  { name: 'București' },
  { name: 'Buzău' },
  { name: 'Călărași' },
  { name: 'Caraș-Severin' },
  { name: 'Cluj' },
  { name: 'Constanța' },
  { name: 'Covasna' },
  { name: 'Dâmbovița' },
  { name: 'Dolj' },
  { name: 'Galați' },
  { name: 'Giurgiu' },
  { name: 'Gorj' },
  { name: 'Harghita' },
  { name: 'Hunedoara' },
  { name: 'Ialomița' },
  { name: 'Iași' },
  { name: 'Ilfov' },
  { name: 'Maramureș' },
  { name: 'Mehedinți' },
  { name: 'Mureș' },
  { name: 'Neamț' },
  { name: 'Olt' },
  { name: 'Prahova' },
  { name: 'Sălaj' },
  { name: 'Satu Mare' },
  { name: 'Sibiu' },
  { name: 'Suceava' },
  { name: 'Teleorman' },
  { name: 'Timiș' },
  { name: 'Tulcea' },
  { name: 'Vâlcea' },
  { name: 'Vaslui' },
  { name: 'Vrancea' }
];

const DeleteAttraction = () => {
  const navigate = useNavigate();
  return (
    <div className="delete-counties-container">
      <h2>Lista județelor</h2>
      <button 
        className="back-button" 
        onClick={() => navigate(-1)} // Navighează la pagina anterioară
      >
        Înapoi
      </button>
      {countiesData.length === 0 ? (
        <p>No counties available.</p>
      ) : (
        <ul className="counties-list">
          {countiesData.map((county, index) => (
            <li key={index} className="county-item">
              <Link to={`/manage-attractions/delete/${county.name}`} className="county-link">
                {county.name}
              </Link>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default DeleteAttraction;
