import React, { useState, useEffect, useContext } from 'react'
import { useNavigate } from 'react-router-dom'
import { Calendar } from "react-multi-date-picker"
import { getDaysArray } from '../Utils/utils';
import { ContextGlobal } from '../Utils/globalContext';

const CalendarContainer = ({ productId, reservas }) => {

    const Navigate = useNavigate();
    const [disabledDays, setDisabledDays] = useState([]);
    const { state, dispatch } = useContext(ContextGlobal);

    useEffect(() => {
        let dayArr = [];
        reservas?.forEach((reserva) => dayArr = dayArr.concat(getDaysArray(reserva.fechaInicial, reserva.fechaFinal)))
        setDisabledDays(dayArr)
    }, [reservas])

    const hanldeClickReserva = () => {
        if(!localStorage.getItem("jwt")) {
            Navigate("/login");
            dispatch({
                type: "register",
                payload: {
                  ...state,
                  location: "reserva"
                }
            })   
        } else {
            Navigate(`/product/${productId}/reservas`)
        }
    }

    return (
        <div className='calendario-container'>
            <h3>Fechas disponibles</h3>
            <section className='fechas-container'>    
                <Calendar 
                readOnly
                minDate={new Date()}
                numberOfMonths={2}
                disableMonthPicker
                disableYearPicker
                className='custom-calendar'
                mapDays={({ date }) => {
                    let isDisabled = disabledDays.includes(date.format("YYYY/M/D"))
                    if (isDisabled) return {
                        disabled: true,
                        style: { color: "#8798ad" }
                    }
                }}  
                />
                <Calendar 
                readOnly
                minDate={new Date()}
                numberOfMonths={1}
                disableMonthPicker
                disableYearPicker
                className='custom-calendar mobile'
                mapDays={({ date }) => {
                    let isDisabled = disabledDays.includes(date.format("YYYY/M/D"))
                    if (isDisabled) return {
                        disabled: true,
                        style: { color: "#8798ad" }
                    }
                }}  
                />
                <div className="submit-container">
                    <p>Agregá tus fechas de viaje para obtener precios exactos</p>
                    <button className='small-button' 
                    onClick={() => hanldeClickReserva()}>
                        Iniciar reserva
                    </button>
                </div>
            </section>
        </div>
    )
}
export default CalendarContainer