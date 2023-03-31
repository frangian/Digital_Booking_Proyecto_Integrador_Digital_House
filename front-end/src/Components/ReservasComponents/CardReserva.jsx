import React from 'react'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faLocationDot, faStar } from '@fortawesome/free-solid-svg-icons'
import Skeleton from 'react-loading-skeleton'
import 'react-loading-skeleton/dist/skeleton.css'
import { CircularProgress } from '@mui/material'

const CardReserva = ({ tituloCategoria, tituloProducto, ubicacion, img, checks, sendLoad }) => {
  return (
    <div className='card-reserva-container'>
      <div className="card-reserva">
        <h3>Detalle de la reserva</h3>
        <div className="card-reserva-content">
          <section className="title-img-reserva">
            {img ? <img src={img} alt="Imagen producto" /> : <Skeleton height={"40vh"}/>}
          </section>
          <section className="right-card-reserva">
            <div className="info-card-reserva">
              <p className='titulo-categoria-reserva'>{tituloCategoria || <Skeleton />}</p>
              <h3>{tituloProducto || <Skeleton />}</h3>
              <div className="estrellas-reserva">
                <FontAwesomeIcon icon={faStar} className="icono icono-verde" />
                <FontAwesomeIcon icon={faStar} className="icono icono-verde" />
                <FontAwesomeIcon icon={faStar} className="icono icono-verde" />
                <FontAwesomeIcon icon={faStar} className="icono icono-verde" />
                <FontAwesomeIcon icon={faStar} className="icono icono-verde" />
              </div>
              <p className="ubicacion-reserva">
                <span><FontAwesomeIcon icon={faLocationDot} className="location-icon"/></span>
                {ubicacion.includes("undefined") ? <Skeleton /> : ubicacion}
              </p>
            </div>
            <div className="checkin-checkout">
              <div className="checkin">
                <p>Check in</p>
                <p className="fecha">{checks[0]?.day ? `${checks[0]?.day}/${checks[0]?.month}/${checks[0]?.year}` : "_ _ /_ _ /_ _"}</p>
              </div>
              <div className="checkout">
                <p>Check out</p>
                <p className="fecha">
                  {checks[1]?.day ? `${checks[1]?.day}/${checks[1]?.month}/${checks[1]?.year}` : "_ _ /_ _ /_ _"}
                </p>
              </div>
            </div>
            <button className='small-button' type='submit' form='reserva-form' disabled={sendLoad}>
              {
                sendLoad ? <CircularProgress sx={{ color: "#fff", marginTop: "5px"}} size="1.4rem"/> : "Confirmar reserva"
              }
            </button>
          </section>
        </div>
      </div>
    </div>
  )
}

export default CardReserva