import React from 'react';
import { Link } from 'react-router-dom'
import './counties.css';

import albaImg from './countiesImg/Alba/alba.jpeg';
import aradImg from './countiesImg/Arad/arad.jpg';
import argesImg from './countiesImg/Arges/arges.jpg';
import bacauImg from './countiesImg/Bacau/bacau.jpg';
import bihorImg from './countiesImg/Bihor/bihor.jpg';
import bistritaNasaudImg from './countiesImg/BistritaNasaud/bistritaNasaud.jpg';
import botosaniImg from './countiesImg/Botosani/botosani.jpg';
import brailaImg from './countiesImg/Braila/braila.jpg';
import brasovImg from './countiesImg/Brasov/brasov.jpg';
import bucurestiImg from './countiesImg/Bucuresti/bucuresti.jpg';
import buzauImg from './countiesImg/Buzau/buzau.jpg';
import calarasi1Img from './countiesImg/Calarasi/calarasi.jpg';
import carasSeverinImg from './countiesImg/CarasSeverin/carasSeverin.jpg';
import clujImg from './countiesImg/Cluj/cluj.jpg';
import constantaImg from './countiesImg/Constanta/constanta.jpg';
import covasnaImg from './countiesImg/Covasna/covasna.jpeg';
import dambovitoaImg from './countiesImg/Dambovita/dambovita.png';
import doljImg from './countiesImg/Dolj/dolj.jpeg';
import galatiImg from './countiesImg/Galati/galati.jpg';
import giurgiuImg from './countiesImg/Giurgiu/giurgiu.jpg';
import gorjImg from './countiesImg/Gorj/gorj.jpg';
import harghitaImg from './countiesImg/Harghita/harghita.jpg';
import hunedoaraImg from './countiesImg/Hunedoara/hunedoara.jpg';
import ialomitaImg from './countiesImg/Ialomita/ialomita.jpg';
import iasiImg from './countiesImg/Iasi/iasi.jpeg';
import ilsovImg from './countiesImg/Ilfov/ilfov.jpg';
import maramuresImg from './countiesImg/Maramures/maramures.jpg';
import mehedinti1Img from './countiesImg/Mehedinti/mehedinti.jpg';
import muresImg from './countiesImg/Mures/mures.jpeg';
import neamtImg from './countiesImg/Neamt/neamt.jpeg';
import oltImg from './countiesImg/Olt/olt.jpg';
import prahovaImg from './countiesImg/Prahova/prahova.jpg';
import salajImg from './countiesImg/Salaj/salaj.jpg';
import satuMareImg from './countiesImg/SatuMare/satuMare.png';
import sibiuImg from './countiesImg/Sibiu/sibiu.jpg';
import suceavaImg from './countiesImg/Suceava/suceava.jpg';
import teleormanImg from './countiesImg/Teleorman/teleorman.jpg';
import timisImg from './countiesImg/Timis/timis.jpg';
import tulceaImg from './countiesImg/Tulcea/tulcea.jpg';
import valsaeImg from './countiesImg/Valcea/valcea.jpg';
import vasluiImg from './countiesImg/Vaslui/vaslui.jpg';
import vranceaImg from './countiesImg/Vrancea/vrancea.jpg';

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


const Counties = () => {
  return (
    <div className="counties-container">
      <h2>Județele României</h2>
      <div className="counties-list">
        {countiesData.map((county, index) => (
          <div key={index} className="county" style={{ backgroundImage: `url(${county.image})` }}>
            {/* Navighează către pagina de detalii a județului */}
            <Link to={`/county/${county.name}`} className="county-link">
              Județul {county.name}
            </Link>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Counties;



