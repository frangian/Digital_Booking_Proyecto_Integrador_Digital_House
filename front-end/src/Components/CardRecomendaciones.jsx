import * as React from "react";
import { useState, useEffect, useContext } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faStar } from "@fortawesome/free-solid-svg-icons";
import { faHeart } from "@fortawesome/free-solid-svg-icons";
import { faMapMarkerAlt } from "@fortawesome/free-solid-svg-icons";
import { useNavigate } from "react-router-dom";
import { elegirServicio } from './Utils/utils'
import { ContextGlobal } from "./Utils/globalContext";
import axios from 'axios';

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
  const { state, dispatch } = useContext(ContextGlobal);

  const [characteristics, setCharacteristics] = useState([]);

  //funcion de boton mas-menos
  const [showFullDescription, setShowFullDescription] = useState(false);

  const shortDescription =
    description.length > MAX_LENGTH
      ? description.substring(0, MAX_LENGTH) + "..."
      : description;

  const handleToggleDescription = () => {
    setShowFullDescription(!showFullDescription);
  };

  useEffect(() => {
    axios.get(`http://localhost:8080/caracteristica/producto/${id}`)
      .then((response) => {
        setCharacteristics(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
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
            <span key={caracteristica.id} className="icono-servicio">
              {elegirServicio(caracteristica.titulo, "#383b58")}
            </span>
          ))}
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
            dispatch({
              type: "register",
              payload: {
                ...state,
                map: false
              }
            })
          }}
        >
          ver más
        </button>
      </div>
    </div>
  );
};

export default CardRecomendaciones;
