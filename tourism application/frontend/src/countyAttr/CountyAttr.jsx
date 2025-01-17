import React from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import './countyAttr.css';

const CountyAttr = () => {
    const location = useLocation(); // Access passed state
    const navigate = useNavigate();
    const { selectedCounty } = location.state || {};

    // Sample data for Alba; can be extended for other counties
    const countyAttractions = {
        Alba: {
            visited: ['Cetatea Alba Carolina', 'Salina Turda'],
            toVisit: ['Cascada Vârciorog', 'Râpa Roșie'],
            notInterested: ['Peștera Huda lui Papară']
        },

        Suceava: {
            visited: ['Cetatea de Scaun a Sucevei', 'Mănăstirea Humorului'],
            toVisit: ['Mănăstirea Voroneț', 'Codrii Seculari Slătioara'],
            notInterested: ['Gura Humorului Parc Aventura']
        },
        // Add more counties as needed
    };

    // Handle Logout
    const handleLogout = () => {
        navigate('/');
    };

    // Fallback if no county selected
    if (!selectedCounty || !countyAttractions[selectedCounty]) {
        return (
            <div className="error">
                <h2>No data available for the selected county!</h2>
                <button onClick={() => navigate('/')}>Go Back</button>
            </div>
        );
    }

    const { visited, toVisit, notInterested } = countyAttractions[selectedCounty];

    return (
        <div className="my-account-page">
            <h1>Obiective Turistice - Județul {selectedCounty}</h1>

            <div className="county-lists">
                {/* Visited Attractions */}
                <div className="list visited-list">
                    <h2>Vizitate</h2>
                    <ul>
                        {visited.map((attr, index) => (
                            <li key={index}>{attr}</li>
                        ))}
                    </ul>
                </div>

                {/* To Visit Attractions */}
                <div className="list to-visit-list">
                    <h2>De vizitat</h2>
                    <ul>
                        {toVisit.map((attr, index) => (
                            <li key={index}>{attr}</li>
                        ))}
                    </ul>
                </div>

                {/* Not Interested Attractions */}
                <div className="list not-to-visit-list">
                    <h2>Nu vreau să vizitez</h2>
                    <ul>
                        {notInterested.map((attr, index) => (
                            <li key={index}>{attr}</li>
                        ))}
                    </ul>
                </div>
            </div>

            <button className="logout-button" onClick={handleLogout}>
                Logout
            </button>
        </div>
    );
};

export default CountyAttr;
