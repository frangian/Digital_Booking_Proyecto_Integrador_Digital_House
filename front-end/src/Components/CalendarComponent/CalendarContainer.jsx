import React, { useState, useEffect, useContext } from 'react'
import { useNavigate } from 'react-router-dom'
import { Calendar } from "react-multi-date-picker"
import { getDaysArray } from '../Utils/utils';
import { ContextGlobal } from '../Utils/globalContext';
import Swal from 'sweetalert2'
import emailjs from '@emailjs/browser'
import { API_URL } from '../Utils/api';
import axios from 'axios';

const CalendarContainer = ({ productId, reservas }) => {

    const Navigate = useNavigate();
    const [disabledDays, setDisabledDays] = useState([]);
    const { state, dispatch } = useContext(ContextGlobal);

    useEffect(() => {
        let dayArr = [];
        reservas?.forEach((reserva) => dayArr = dayArr.concat(getDaysArray(reserva.fechaInicial, reserva.fechaFinal)))
        setDisabledDays(dayArr)
    }, [reservas])

    const actualizarUserState = () => {
        let jwt = localStorage.getItem("jwt");
        const headers = { 'Authorization': `Bearer ${jwt}` };
        axios.get(`${API_URL}/usuario/${state.user.id}`, { headers })
        .then(res => {
            dispatch({
                type: "register",
                payload: {
                    ...state,
                    user: res.data,
                    logged: true
                }
            })
        })
    }

    const enviarMail = () => {
        let templateParams = {
            to_name: `${state.user.nombre} ${state.user.apellido}`,
            to_email: state.user.email,
            html: `
            <p>Para activar su cuenta haga click en el botón.</p>
            <br/>
            <a href="http://group8-bucket-front.s3-website.us-east-2.amazonaws.com/validado?source=gmail" target="e_blank">
              <button 
                style="border: 1px solid #1dbeb4; height: 40px; width: 207px; border-radius: 5px; cursor: pointer; font-size: 1rem; font-weight: 600; color: #ffffff; background-color: #1dbeb4"
              >
                    Activa tu cuenta
              </button>
            </a> 
            `
        }
        emailjs.send("service_nc0296c", "template_efd615c", templateParams, "8EQLpJMNLWle12z9B")
        .then(res => {
        })
        .catch(err => {
            console.log(err);
        })
    }

    const hanldeClickReserva = () => {
        if(!localStorage.getItem("jwt")) {
            Navigate("/login");
            dispatch({
                type: "register",
                payload: {
                  ...state,
                  location: `/product/${productId}`
                }
            })   
        } else {
            if (state.user.validado) {
                Navigate(`/product/${productId}/reservas`)
            } else {
                Swal.fire({
                    icon: 'info',
                    title: 'Activa tu cuenta',
                    willClose: () => actualizarUserState(),
                    text: "Debes activar tu cuenta para realizar una reserva.",
                    confirmButtonColor: "#1dbeb4",
                    footer: `<b style='cursor: pointer; color: #1dbeb4;' onclick="${enviarMail()}">Si no le llego un correo de activación al registrarse haga click aquí</b>`
                })
            }
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