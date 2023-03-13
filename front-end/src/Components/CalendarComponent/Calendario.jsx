import React from 'react'
import { Calendar, DateObject } from "react-multi-date-picker"

const Calendario = () => {
    return (
        <>
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
        </>
    )
}

export default Calendario