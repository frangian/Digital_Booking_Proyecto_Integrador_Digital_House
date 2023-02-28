import * as React from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPersonSwimming, faStar } from "@fortawesome/free-solid-svg-icons";
import { faHeart } from "@fortawesome/free-solid-svg-icons";
import { faWifi } from "@fortawesome/free-solid-svg-icons";
import { faMapMarkerAlt } from "@fortawesome/free-solid-svg-icons";

const CardRecomendaciones = ({
  title,
  imagen,
  category,
  location,
  description,
}) => {
  return (
    <div className="card-recomendaciones">
      <div className="image-container">
        <img src={imagen} alt={title} />
        <FontAwesomeIcon icon={faHeart} className="fa-heart" />
      </div>
      <div className="info-container">
        <div className="info-container-header">
          <div>
            <div className="categoria-container">
              <h3>{category}</h3>
              <FontAwesomeIcon icon={faStar} className="icono icono-verde" />
              <FontAwesomeIcon icon={faStar} className="icono icono-verde" />
              <FontAwesomeIcon icon={faStar} className="icono icono-verde" />
              <FontAwesomeIcon icon={faStar} className="icono icono-verde" />
            </div>
            <h2>{title}</h2>
          </div>
          <div className="puntuacion-container">
            <div className="square8">
              <span id="square8-number">8</span>
            </div>
            <p>Muy bueno</p>
          </div>
        </div>
        <div className="extras-container">
            
            <p>
              <FontAwesomeIcon icon={faMapMarkerAlt} className="icono ubicacion" />
              {location} <span>MOSTRAR EN EL MAPA</span>
            </p>
          <FontAwesomeIcon icon={faWifi} className="icono extra" />
          <FontAwesomeIcon icon={faPersonSwimming} className="icono extra" />
        </div>
        <p>{description}</p>
        <button className="ver-mas-btn">ver más</button>
      </div>
    </div>
  );
};

export default CardRecomendaciones;
