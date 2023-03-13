import React, { useState } from 'react'
import { horas } from '../Utils/utils';
import Select from 'react-select';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faCheck } from '@fortawesome/free-solid-svg-icons'

const Llegada = ({ values, changeHour }) => {

  const [idValue, setIdValue] = useState(9);

  return (
    <div className='llegada-container'>
      <h3>Tu horario de llegada</h3>
      <div className="llegada-card-container">
        <div className="llegada-card-content">
          <h5>
            <span><FontAwesomeIcon icon={faCheck} className="icon-check"/></span>
            Tu habitación va a estar lista para el check-in entre las {values.horaLlegada} y las {horas[idValue + 1].label}
          </h5>
          <p>Indicá tu horario estimado de llegada </p>
          <Select
            className='basic-single'
            classNamePrefix="select"
            defaultValue={horas[0].value}
            name="horas"
            options={horas}
            onChange={(e) => {
              changeHour(e.value)
              if (e.i === 23) {
                setIdValue(-1)
              } else setIdValue(e.i)
            }}
          />
        </div>
      </div>
    </div>
  )
}

export default Llegada