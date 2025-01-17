import React from 'react';
import { useNavigate } from 'react-router-dom'
import './countiesLog.css';

import albaImg from '../counties/countiesImg/Alba/alba.jpeg';
import aradImg from '../counties/countiesImg/Arad/arad.jpg';
import argesImg from '../counties/countiesImg/Arges/arges.jpg';
import bacauImg from '../counties/countiesImg/Bacau/bacau.jpg';
import bihorImg from '../counties/countiesImg/Bihor/bihor.jpg';
import bistritaNasaudImg from '../counties/countiesImg/BistritaNasaud/bistritaNasaud.jpg';
import botosaniImg from '../counties/countiesImg/Botosani/botosani.jpg';
import brailaImg from '../counties/countiesImg/Braila/braila.jpg';
import brasovImg from '../counties/countiesImg/Brasov/brasov.jpg';
import bucurestiImg from '../counties/countiesImg/Bucuresti/bucuresti.jpg';
import buzauImg from '../counties/countiesImg/Buzau/buzau.jpg';
import calarasi1Img from '../counties/countiesImg/Calarasi/calarasi.jpg';
import carasSeverinImg from '../counties/countiesImg/CarasSeverin/carasSeverin.jpg';
import clujImg from '../counties/countiesImg/Cluj/cluj.jpg';
import constantaImg from '../counties/countiesImg/Constanta/constanta.jpg';
import covasnaImg from '../counties/countiesImg/Covasna/covasna.jpeg';
import dambovitoaImg from '../counties/countiesImg/Dambovita/dambovita.png';
import doljImg from '../counties/countiesImg/Dolj/dolj.jpeg';
import galatiImg from '../counties/countiesImg/Galati/galati.jpg';
import giurgiuImg from '../counties/countiesImg/Giurgiu/giurgiu.jpg';
import gorjImg from '../counties/countiesImg/Gorj/gorj.jpg';
import harghitaImg from '../counties/countiesImg/Harghita/harghita.jpg';
import hunedoaraImg from '../counties/countiesImg/Hunedoara/hunedoara.jpg';
import ialomitaImg from '../counties/countiesImg/Ialomita/ialomita.jpg';
import iasiImg from '../counties/countiesImg/Iasi/iasi.jpeg';
import ilsovImg from '../counties/countiesImg/Ilfov/ilfov.jpg';
import maramuresImg from '../counties/countiesImg/Maramures/maramures.jpg';
import mehedinti1Img from '../counties/countiesImg/Mehedinti/mehedinti.jpg';
import muresImg from '../counties/countiesImg/Mures/mures.jpeg';
import neamtImg from '../counties/countiesImg/Neamt/neamt.jpeg';
import oltImg from '../counties/countiesImg/Olt/olt.jpg';
import prahovaImg from '../counties/countiesImg/Prahova/prahova.jpg';
import salajImg from '../counties/countiesImg/Salaj/salaj.jpg';
import satuMareImg from '../counties/countiesImg/SatuMare/satuMare.png';
import sibiuImg from '../counties/countiesImg/Sibiu/sibiu.jpg';
import suceavaImg from '../counties/countiesImg/Suceava/suceava.jpg';
import teleormanImg from '../counties/countiesImg/Teleorman/teleorman.jpg';
import timisImg from '../counties/countiesImg/Timis/timis.jpg';
import tulceaImg from '../counties/countiesImg/Tulcea/tulcea.jpg';
import valsaeImg from '../counties/countiesImg/Valcea/valcea.jpg';
import vasluiImg from '../counties/countiesImg/Vaslui/vaslui.jpg';
import vranceaImg from '../counties/countiesImg/Vrancea/vrancea.jpg';

const countiesData = [
  { name: 'Alba', image: albaImg },
  { name: 'Arad', image: aradImg },
  { name: 'Argeș', image: argesImg },
  { name: 'Bacău', image: bacauImg },
  { name: 'Bihor', image: bihorImg },
  { name: 'Bistrița-Năsăud', image: bistritaNasaudImg },
  { name: 'Botoșani', image: botosaniImg },
  { name: 'Brăila', image: brailaImg },
  { name: 'Brașov', image: brasovImg },
  { name: 'București', image: bucurestiImg },
  { name: 'Buzău', image: buzauImg },
  { name: 'Călărași', image: calarasi1Img },
  { name: 'Caraș-Severin', image: carasSeverinImg },
  { name: 'Cluj', image: clujImg },
  { name: 'Constanța', image: constantaImg },
  { name: 'Covasna', image: covasnaImg },
  { name: 'Dâmbovița', image: dambovitoaImg },
  { name: 'Dolj', image: doljImg },
  { name: 'Galați', image: galatiImg },
  { name: 'Giurgiu', image: giurgiuImg },
  { name: 'Gorj', image: gorjImg },
  { name: 'Harghita', image: harghitaImg },
  { name: 'Hunedoara', image: hunedoaraImg },
  { name: 'Ialomița', image: ialomitaImg },
  { name: 'Iași', image: iasiImg },
  { name: 'Ilfov', image: ilsovImg },
  { name: 'Maramureș', image: maramuresImg },
  { name: 'Mehedinți', image: mehedinti1Img },
  { name: 'Mureș', image: muresImg },
  { name: 'Neamț', image: neamtImg },
  { name: 'Olt', image: oltImg },
  { name: 'Prahova', image: prahovaImg },
  { name: 'Sălaj', image: salajImg },
  { name: 'Satu Mare', image: satuMareImg },
  { name: 'Sibiu', image: sibiuImg },
  { name: 'Suceava', image: suceavaImg },
  { name: 'Teleorman', image: teleormanImg },
  { name: 'Timiș', image: timisImg },
  { name: 'Tulcea', image: tulceaImg },
  { name: 'Vâlcea', image: valsaeImg },
  { name: 'Vaslui', image: vasluiImg },
  { name: 'Vrancea', image: vranceaImg }
];


const CountiesLog = () => {
    const navigate = useNavigate(); // Hook-ul pentru navigare
  
    // Funcție pentru navigare către pagina MyAccount
    const handleCountyClick = (countyName) => {
        navigate('/county-attractions', { state: { selectedCounty: countyName } });
    };
    
  
    return (
      <div className="counties-container">
        <h2>Județele României</h2>
        <div className="counties-list">
          {countiesData.map((county, index) => (
            <div
              key={index}
              className="county"
              style={{ backgroundImage: `url(${county.image})` }}
              onClick={() => handleCountyClick(county.name)} // Navighează la click
            >
              <div className="county-overlay">
                <span>Județul {county.name}</span>
              </div>
            </div>
          ))}
        </div>
      </div>
    );
  };
  
  export default CountiesLog;



