import React from 'react'

const DatosReserva = ({ values, changeCiudad }) => {

    return (
        <div className='datos-reserva'>
            <h3>Completá tus datos</h3>
            <div className="datos-reserva-inputs">
                <div className='nombre-reserva-input'>
                    <label htmlFor="nombre-reserva">Nombre</label>
                    <input type="text" id='nombre-reserva' className='input-reserva' value={values.nombre} disabled/>
                </div>
                <div className='apellido-reserva-input'>
                    <label htmlFor="apellido-reserva">Apellido</label>
                    <input type="text" id='apellido-reserva' className='input-reserva' value={values.apellido} disabled/>
                </div>
                <div className='mail-reserva-input'>
                    <label htmlFor="mail-reserva">Correo electronico</label>
                    <input type="text" id='mail-reserva' className='input-reserva' value={values.mail} disabled/>
                </div>
                <div className='ciudad-reserva-input'>
                    <label htmlFor="ciudad-reserva">Ciudad</label>
                    <input type="text" id="ciudad-reserva" value={values.ciudad} onChange={(e) => {changeCiudad(e.target.value)}}/>
                </div>
            </div>
        </div>
    )
}

export default DatosReserva
