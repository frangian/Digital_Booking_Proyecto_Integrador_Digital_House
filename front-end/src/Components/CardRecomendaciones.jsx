import * as React from "react";
import { useState, useEffect, useContext } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faStar } from "@fortawesome/free-solid-svg-icons";
import { faHeart } from "@fortawesome/free-solid-svg-icons";
import { faMapMarkerAlt } from "@fortawesome/free-solid-svg-icons";
import { useNavigate } from "react-router-dom";
import { elegirServicio } from "./Utils/utils";
import { ContextGlobal } from "./Utils/globalContext";
import axios from "axios";
import { setFavInStorage, removeFavInStorage } from "./Utils/localStorage";
import Modal from "./Modal";
import { API_URL } from './Utils/api'

const CardRecomendaciones = ({
  id,
  title,
  imagen,
  category,
  location,
  description,
  puntuacion,
  checkIn,
  checkOut,
  reserva,
  llegada
}) => {
  const navigate = useNavigate();
  const MAX_LENGTH = 100;
  const { state, dispatch } = useContext(ContextGlobal);

  const [showModal, setShowModal] = useState(false);
  const [characteristics, setCharacteristics] = useState([]);
  const [favorite, setFavorite] = useState(() => {
    const storedValue = localStorage.getItem(`favorite-${id}`);
    return storedValue ? JSON.parse(storedValue) : false;
  });

  const shortDescription =
    description.length > MAX_LENGTH
      ? description.substring(0, MAX_LENGTH) + "..."
      : description;

  const handleToggleDescription = () => {
    setShowModal(!showModal);
  };

  const addFav = () => {
    const favoritedProduct = setFavInStorage({
      id,
      title,
      imagen,
      category,
      location,
      description,
      puntuacion,
    });
    setFavorite(favoritedProduct);
    localStorage.setItem(`favorite-${id}`, JSON.stringify(true));
  };
  const removeFav = () => {
    removeFavInStorage(id);
    setFavorite(false);
    localStorage.setItem(`favorite-${id}`, JSON.stringify(false));
  };

  useEffect(() => {
    axios
      .get(`${API_URL}/caracteristica/producto/${id}`)
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
        <FontAwesomeIcon
          icon={faHeart}
          className={
            favorite
              ? "fa-heart heart-active grow-heart"
              : "fa-heart grow-heart"
          }
          onClick={favorite ? removeFav : addFav}
        />
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
              <span id="square8-number">{puntuacion}</span>
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
              href={`http://group8-bucket-front.s3-website.us-east-2.amazonaws.com/product/${id}#mapa`}
              className="enlace-mapa"
            >
              MOSTRAR EN EL MAPA
            </a>
          </p>

          {
            !reserva ? characteristics.map((caracteristica) => (
              <span key={caracteristica.id} className="icono-servicio">
                {elegirServicio(caracteristica.titulo, "#383b58")}
              </span>
            )) :
            (<div style={{ marginTop: "20px" }}>
              <p>Check in: {checkIn + " " + llegada}</p>
              <p>Check out: {checkOut}</p>
            </div>)
          }
        </div>
        <p>
          {shortDescription}
          {description.length > MAX_LENGTH && (
            <button
              onClick={handleToggleDescription}
              className="btn-vermas-vermenos"
            >
              más...
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
                map: false,
              },
            });
          }}
        >
          ver más
        </button>
      </div>
      {showModal && (
        <Modal onClose={handleToggleDescription}>
          <h2>{title}</h2>
          <p>{description}</p>
        </Modal>
      )}
    </div>
  );
};

export default CardRecomendaciones;
