import React from 'react'
import { elegirServicio } from './Utils/utils'

const Servicios = ({ servicios }) => {

    return (
        <div className='servicios'>
            <h3>¿Qué ofrece este lugar?</h3>
            <div className="iconos-servicios-container"
            style={{
                gridTemplateRows: `repeat(${servicios.length > 4 ? 2 : 1 }, 1fr)`
            }}
            >
                {
                    servicios.map((servicio) => (
                        <div key={servicio.id} className="servicio">
                            {elegirServicio(servicio.titulo, "#1dbeb4")}
                            <p>{servicio.titulo}</p>
                        </div>
                    ))
                }
            </div>
        </div>
    )
}

export default Servicios