import * as React from "react";
import { useState, useEffect } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPersonSwimming, faStar } from "@fortawesome/free-solid-svg-icons";
import { faHeart } from "@fortawesome/free-solid-svg-icons";
import { faWifi } from "@fortawesome/free-solid-svg-icons";
import { faMapMarkerAlt } from "@fortawesome/free-solid-svg-icons";
import { useNavigate } from "react-router-dom";
import { elegirServicio } from './Utils/utils'

const CardRecomendaciones = ({
  id,
  title,
  imagen,
  category,
  location,
  description,
}) => {
  const navigate = useNavigate();
  const MAX_LENGTH = 200;

  const [showFullDescription, setShowFullDescription] = useState(false);

  const [characteristics, setCharacteristics] = useState([]);

  const shortDescription =
    description.length > MAX_LENGTH
      ? description.substring(0, MAX_LENGTH) + "..."
      : description;

  const handleToggleDescription = () => {
    setShowFullDescription(!showFullDescription);
  };

  useEffect(() => {
    // Hacer una solicitud GET para obtener las características del producto
    fetch(`http://localhost:8080/caracteristica/producto/${id}`)
      .then((response) => response.json())
      .then((data) => setCharacteristics(data));
  }, [id]);

  return (
    <div className="card-recomendaciones">
      <div className="image-container">
        <img src={imagen} alt={title} />
        <FontAwesomeIcon icon={faHeart} className="fa-heart" />
      </div>
      <div className="info-container">
        <div className="info-container-header">
          <div>
            <div className="info-categoria-container">
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
            <FontAwesomeIcon
              icon={faMapMarkerAlt}
              className="icono ubicacion"
            />
            {location}{" "}
            <a
              href={`http://localhost:3000/product/${id}#mapa`}
              className="enlace-mapa"
            >
              MOSTRAR EN EL MAPA
            </a>
          </p>

          
            {characteristics.map((caracteristica) => (
              <span key={caracteristica.id} className="icono-servicio">{elegirServicio(caracteristica.titulo, "#383b58")}</span>
            ))}
          
          {/* <FontAwesomeIcon icon={faWifi} className="icono extra" />
          <FontAwesomeIcon icon={faPersonSwimming} className="icono extra" /> */}
        </div>
        <p>
          {showFullDescription ? description : shortDescription}
          {description.length > MAX_LENGTH && (
            <button
              onClick={handleToggleDescription}
              className="btn-vermas-vermenos"
            >
              {showFullDescription ? "menos..." : "más..."}
            </button>
          )}
        </p>
        <button
          className="ver-mas-btn"
          onClick={() => {
            navigate(`/product/${id}`);
          }}
        >
          ver más
        </button>
      </div>
    </div>
  );
};

export default CardRecomendaciones;
