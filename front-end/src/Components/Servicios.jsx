import React from 'react'
import { elegirServicio } from './Utils/utils'



const Servicios = ({ servicios }) => {

    return (
        <div className='servicios'>
            <h3>¿Qué ofrece este lugar?</h3>
            <div className="iconos-servicios-container">
                {
                    servicios.map((servicio) => (
                        <div key={servicio.id} className="servicio">
                            {elegirServicio(servicio.title)}
                            <p>{servicio.title}</p>
                        </div>
                    ))
                }
            </div>
        </div>
    )
}

export default Servicios