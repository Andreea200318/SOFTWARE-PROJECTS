import React from 'react';
import './topAttractions.css';

// Importează imaginile
import castelulPelesImg from './topAttractionsImg/castelulPeles.jpg';
import laculIezerImg from './topAttractionsImg/laculIezer.jpg';
import mocanitaImg from './topAttractionsImg/mocanita.jpg';
import palatulParlamentuluiImg from './topAttractionsImg/palatulParlamentului.jpg';
import sfinxulBucegiImg from './topAttractionsImg/sfinxulBucegi.jpg';
import cimitirulVeselImg from './topAttractionsImg/cimitirulVesel.jpg';
import deltaDunariiImg from './topAttractionsImg/deltaDunarii.jpeg';
import salinaTurdaImg from './topAttractionsImg/salinaTurda.jpg';
import transfagarasanImg from './topAttractionsImg/transfagarasan.jpg';

const TopAttractions = () => {
  const topAttractionsImages = [
    castelulPelesImg,
    laculIezerImg,
    mocanitaImg,
    palatulParlamentuluiImg,
    sfinxulBucegiImg,
    cimitirulVeselImg,
    deltaDunariiImg,
    salinaTurdaImg,
    transfagarasanImg
  ];

  const attractionsNames = [
    'Castelul Peleș',
    'Lacul Iezer',
    'Mocănița',
    'Palatul Parlamentului',
    'Sfinxul Bucegi',
    'Cimitirul Vesel',
    'Delta Dunării',
    'Salina Turda',
    'Transfăgarășan'
  ];

  // Array cu URL-uri pentru fiecare atracție
  const attractionsUrls = [
    'https://link-castelul-peles',
    'https://link-lacul-iezer',
    'https://link-mocanita',
    'https://link-palatul-parlamentului',
    'https://link-sfinxul-bucegi',
    'https://link-cimitirul-vesel',
    'https://link-delta-dunarii',
    'https://link-salina-turda',
    'https://link-transfagarasan'
  ];

  return (
    <div className="topAttractions-container">
      <h2>Atracțiile principale ale României</h2>
      <div className="topAttractions-list">
        {topAttractionsImages.map((img, index) => (
          <div
            key={index}
            className="topAttractions"
            style={{ backgroundImage: `url(${img})` }}
          >
            <a href={attractionsUrls[index]} target="_blank" rel="noopener noreferrer">
              {attractionsNames[index]}
            </a>
          </div>
        ))}
      </div>
    </div>
  );
};

export default TopAttractions;
