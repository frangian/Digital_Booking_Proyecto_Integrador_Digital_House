import React from 'react'
import { useNavigate } from 'react-router-dom'
import Calendario from './Calendario';
import { Calendar, DateObject } from "react-multi-date-picker"

const CalendarContainer = ({ productId }) => {

    const Navigate = useNavigate();

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
                />
                <Calendar 
                readOnly
                minDate={new Date()}
                numberOfMonths={1}
                disableMonthPicker
                disableYearPicker
                className='custom-calendar mobile'
                />
                <div className="submit-container">
                    <p>Agregá tus fechas de viaje para obtener precios exactos</p>
                    <button className='small-button' 
                    onClick={() => Navigate(`/product/${productId}/reservas`)}>
                        Iniciar reserva
                    </button>
                </div>
            </section>
        </div>
    )
}
export default CalendarContainer