import React from 'react'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faLocationDot, faStar } from '@fortawesome/free-solid-svg-icons'

const CardReserva = ({ tituloCategoria, tituloProducto, ubicacion, img, checks }) => {
  return (
    <div className='card-reserva-container'>
      <div className="card-reserva">
        <h3>Detalle de la reserva</h3>
        <div className="card-reserva-content">
          <section className="title-img-reserva">
            <img src={img} alt="Imagen producto" />
          </section>
          <section className="right-card-reserva">
            <div className="info-card-reserva">
              <p className='titulo-categoria-reserva'>{tituloCategoria}</p>
              <h3>{tituloProducto}</h3>
              <div className="estrellas-reserva">
                <FontAwesomeIcon icon={faStar} className="icono icono-verde" />
                <FontAwesomeIcon icon={faStar} className="icono icono-verde" />
                <FontAwesomeIcon icon={faStar} className="icono icono-verde" />
                <FontAwesomeIcon icon={faStar} className="icono icono-verde" />
                <FontAwesomeIcon icon={faStar} className="icono icono-verde" />
              </div>
              <p className="ubicacion-reserva">
                <span><FontAwesomeIcon icon={faLocationDot} className="location-icon"/></span>
                {ubicacion}
              </p>
            </div>
            <div className="checkin-checkout">
              <div className="checkin">
                <p>Check in</p>
                <p className="fecha">{checks[0]?.day ? `${checks[0]?.day}/${checks[0]?.month}/${checks[0]?.year}` : ""}</p>
              </div>
              <div className="checkout">
                <p>Check out</p>
                <p className="fecha">
                  {checks[1]?.day ? `${checks[1]?.day}/${checks[1]?.month}/${checks[1]?.year}` : ""}
                </p>
              </div>
            </div>
            <button className='small-button' type='submit' form='reserva-form'>
              Confirmar reserva
            </button>
          </section>
        </div>
      </div>
    </div>
  )
}

export default CardReserva