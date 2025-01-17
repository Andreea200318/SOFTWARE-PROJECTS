import React from 'react';
import './about.css';

const About = () => {
  return (
    <div className="about-container">
      <h2>Despre noi</h2>
      <p>
        Bine ați venit pe platforma noastră! Suntem două colege de facultate care am dorit să împărtășim iubirea noastră pentru România și frumusețile sale ascunse. 
        Proiectul nostru are scopul de a promova atracțiile turistice din țară și de a le oferi utilizatorilor posibilitatea de a explora locuri fascinante. 
        Vrem să ajutăm vizitatorii să își organizeze călătoriile, să aleagă ce vor să viziteze și să își țină evidența locurilor deja văzute.
      </p>
      
      <h3>Ce oferim?</h3>
      <ul>
        <li>Oferim o listă completă de atracții turistice din România, care include locuri celebre și destinații mai puțin cunoscute.</li>
        <li>Permitem utilizatorilor să își creeze un plan personalizat de călătorie, să aleagă ce locuri vor să viziteze și ce vor să evite.</li>
        <li>Oferim posibilitatea de a marca obiectivele vizitate, astfel încât utilizatorii să își poată urmări progresul în explorarea României.</li>
        <li>Împărtășim informații utile și sugestii pentru fiecare destinație, pentru a face călătoria mai ușoară și mai plăcută.</li>
      </ul>

      <h3>Scopul nostru</h3>
      <p>
        Scopul nostru este de a încuraja turismul intern și de a ajuta oamenii să descopere frumusețile naturale și culturale ale României. 
        Fie că sunteți un călător pasionat sau pur și simplu doriți să descoperiți locuri noi, platforma noastră vă va ajuta să vă planificați următoarea aventură.
      </p>

      <h3>Hai să călătorim împreună!</h3>
      <p>
        Explorați cele mai frumoase destinații din România și împărtășiți-vă experiențele cu noi. Noi credem că fiecare colț al țării are o poveste de spus și suntem aici pentru a vă ghida prin această călătorie.
      </p>
    </div>
  );
};

export default About;
