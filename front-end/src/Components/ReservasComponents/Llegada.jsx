import React, { useState } from 'react'
import { horas } from '../Utils/utils';
import Select from 'react-select';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faCheck } from '@fortawesome/free-solid-svg-icons'

const Llegada = ({ changeHour }) => {

  return (
    <div className='llegada-container'>
      <h3>Tu horario de llegada</h3>
      <div className="llegada-card-container">
        <div className="llegada-card-content">
          <h5>
            <span><FontAwesomeIcon icon={faCheck} className="icon-check"/></span>
            Tu habitación va a estar lista para el check-in entre las 10:00 AM y las 11:00 PM
          </h5>
          <p>Indicá tu horario estimado de llegada</p>
          <Select
            className='basic-single'
            classNamePrefix="select"
            defaultValue={"10:00:00"}
            name="horas"
            options={horas}
            onChange={(e) => {
              changeHour(e.value)
            }}
            menuPlacement="top"
          />
        </div>
      </div>
    </div>
  )
}

export default Llegada