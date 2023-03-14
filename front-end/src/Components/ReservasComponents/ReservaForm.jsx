import React, { useState, useEffect, useContext } from 'react'
import { ContextGlobal } from '../Utils/globalContext'
import { Calendar, DateObject } from "react-multi-date-picker"
import CardReserva from './CardReserva'
import DatosReserva from './DatosReserva'
import Llegada from './Llegada'
import { getDaysArray } from '../Utils/utils'

const ReservaForm = ({ tituloCategoria, tituloProducto, ubicacion, img, reservas, productoId }) => {

    const { state, dispatch } = useContext(ContextGlobal);
    const [disabledDays, setDisabledDays] = useState([]);
    const [checks, setChecks] = useState([
        new DateObject().format("YYYY/M/D"),
        new DateObject().format("YYYY/M/D"),
    ])
    const [values, setValues] = useState({
        nombre: state.nombre, 
        apellido: state.apellido, 
        mail: state.mail, 
        ciudad: "",
        horaLlegada: "10:00:00",
        checkIn: "",
        checkOut: ""
    })
    
    const handleChangeCiudad = (value) => {
        setValues({...values, ciudad: value})
    }

    const handleChangeHour = (value) => {
        setValues({...values, horaLlegada: value})
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        let objPostReserva = {
            horaComienzo: values.horaLlegada,
            fechaInicial: values.checkIn,
            fechaFinal: values.checkOut,
            usuario: 1,
            producto: {
                id: productoId
            }
        }
    }

    useEffect(() => {
        let dayArr = [];
        reservas?.forEach((reserva) => dayArr = dayArr.concat(getDaysArray(reserva.fechaInicial, reserva.fechaFinal)))
        setDisabledDays(dayArr)
    }, [reservas])

    return (
        <div className="reservas-container">
            <div className='reserva-form-container'>
                <form id='reserva-form' onSubmit={(e) => handleSubmit(e)}>
                    <DatosReserva values={values} changeCiudad={handleChangeCiudad}/>
                    <div className="calendar-container-reserva">
                        <h3>Seleccioná tu fecha de reserva</h3>
                        <Calendar 
                        minDate={new Date()}
                        onChange={setChecks}
                        numberOfMonths={2}
                        disableMonthPicker
                        disableYearPicker
                        range
                        rangeHover
                        className='custom-calendar'   
                        mapDays={({ date }) => {
                            let isDisabled = disabledDays.includes(date.format("YYYY/M/D"))
                            if (isDisabled) return {
                                disabled: true,
                                style: { color: "#ccc" }
                            }
                        }}              
                        />
                        <Calendar 
                        minDate={new Date()}
                        onChange={setChecks}
                        numberOfMonths={1}
                        disableMonthPicker
                        disableYearPicker
                        range
                        className='custom-calendar mobile'
                        mapDays={({ date }) => {
                            let isDisabled = disabledDays.includes(date.format("YYYY/M/D"))
                            if (isDisabled) return {
                                disabled: true,
                                style: { color: "#ccc" }
                            }
                        }}   
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
        </div>
    )
}

export default ReservaForm