import React from 'react'
import AutocompleteCities from './AutocompleteCities'

const DatosReserva = ({ values, changeCiudad }) => {

    return (
        <div className='datos-reserva'>
            <h3>Completá tus datos</h3>
            <div className="datos-reserva-inputs">
                <div className='nombre-reserva-input reserva-input-container'>
                    <label htmlFor="nombre-reserva">Nombre</label>
                    <input type="text" id='nombre-reserva' className='input-reserva' value={values.nombre} disabled/>
                </div>
                <div className='apellido-reserva-input reserva-input-container'>
                    <label htmlFor="apellido-reserva">Apellido</label>
                    <input type="text" id='apellido-reserva' className='input-reserva' value={values.apellido} disabled/>
                </div>
                <div className='mail-reserva-input reserva-input-container'>
                    <label htmlFor="mail-reserva">Correo electronico</label>
                    <input type="text" id='mail-reserva' className='input-reserva' value={values.mail} disabled/>
                </div>
                <div className='ciudad-reserva-input reserva-input-container'>
                    <label htmlFor="ciudad-reserva">Ciudad</label>
                    <AutocompleteCities ciudad={values.ciudad} changeCiudad={changeCiudad}/>
                </div>
            </div>
        </div>
    )
}

export default DatosReserva
