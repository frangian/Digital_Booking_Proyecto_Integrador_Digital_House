import React, { useState, useEffect, useContext } from 'react'
import { ContextGlobal } from '../Utils/globalContext'
import { Calendar, DateObject } from "react-multi-date-picker"
import CardReserva from './CardReserva'
import DatosReserva from './DatosReserva'
import Llegada from './Llegada'

const ReservaForm = ({ tituloCategoria, tituloProducto, ubicacion, img }) => {

    const { state, dispatch } = useContext(ContextGlobal);
    const [checks, setChecks] = useState([
        new DateObject().format("YYYY/M/D"),
        new DateObject().format("YYYY/M/D"),
    ])
    const [values, setValues] = useState({
        nombre: state.nombre, 
        apellido: state.apellido, 
        mail: state.mail, 
        ciudad: "",
        horaLlegada: "10:00 AM",
        checkIn: "",
        checkOut: ""
    })
    
    const handleChangeCiudad = (value) => {
        setValues({...values, ciudad: value})
    }

    const handleChangeHour = (value) => {
        setValues({...values, horaLlegada: value})
    }

    return (
        <div className='reserva-form-container'>
            <form id='reserva-form'>
                <DatosReserva values={values} changeCiudad={handleChangeCiudad}/>
                <div className="calendar-container-reserva">
                    <h3 onClick={() => console.log(checks)}>Seleccioná tu fecha de reserva</h3>
                    <Calendar 
                    minDate={new Date()}
                    onChange={setChecks}
                    numberOfMonths={2}
                    disableMonthPicker
                    disableYearPicker
                    range
                    rangeHover
                    className='custom-calendar'                 
                    />
                    <Calendar 
                    minDate={new Date()}
                    numberOfMonths={1}
                    disableMonthPicker
                    disableYearPicker
                    range
                    className='custom-calendar mobile'
                    />
                </div>
                <Llegada values={values} changeHour={handleChangeHour}/>
            </form>
            <CardReserva 
                tituloCategoria={tituloCategoria} 
                tituloProducto={tituloProducto}
                ubicacion={ubicacion}
                img={img}
                checks={checks}
            />
        </div>
    )
}

export default ReservaForm