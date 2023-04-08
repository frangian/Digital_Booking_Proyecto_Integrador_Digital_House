import React, { useState, useEffect, useContext } from "react";
import { useNavigate } from "react-router-dom";
import { elegirServicio } from "./Utils/utils";
import { ContextGlobal } from "./Utils/globalContext";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faStar } from "@fortawesome/free-solid-svg-icons";
import { faHeart } from "@fortawesome/free-solid-svg-icons";
import { faMapMarkerAlt } from "@fortawesome/free-solid-svg-icons";
import Modal from "./Modal";
import axios from "axios";
import { API_URL } from "./Utils/api";

import useToggleState from "./customHooks/useToggleState";

const CardReco = ({
  id,
  imagen,
  title,
  favorite,
  removeFav,
  addFav,
  category,
  puntuacion,
  location,
  reserva,
  checkIn,
  checkOut,
  llegada,
  shortDescription,
  description,
  characteristics,
  cardPrueba,
}) => {
  const { state, dispatch } = useContext(ContextGlobal);
  const navigate = useNavigate();

  const [showModal, toggleState] = useToggleState(false);

  const MAX_LENGTH = 100;

  console.log(location);

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
              onClick={(event) => {
                if (cardPrueba) {
                  event.preventDefault();
                }
              }}
            >
              MOSTRAR EN EL MAPA
            </a>
          </p>

          {!reserva ? (
            characteristics.map((caracteristica) => (
              <span key={caracteristica.id} className="icono-servicio">
                {elegirServicio(caracteristica.titulo, "#383b58")}
              </span>
            ))
          ) : (
            <div style={{ marginTop: "20px" }}>
              <p>Check in: {checkIn + " " + llegada}</p>
              <p>Check out: {checkOut}</p>
            </div>
          )}
        </div>
        <p>
          {shortDescription}
          {description?.length > MAX_LENGTH && (
            <button onClick={toggleState} className="btn-vermas-vermenos">
              más...
            </button>
          )}
        </p>

        <button
          className="ver-mas-btn"
          onClick={() => {
            if (!cardPrueba) {
              navigate(`/product/${id}`);
              dispatch({
                type: "register",
                payload: {
                  ...state,
                  map: false,
                },
              });
            }
          }}
        >
          ver más
        </button>
      </div>
      {showModal && (
        <Modal onClose={toggleState}>
          <h2>{title}</h2>
          <p>{description}</p>
        </Modal>
      )}
    </div>
  );
};

export default CardReco;

