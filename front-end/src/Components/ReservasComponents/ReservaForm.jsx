import React, { useState, useEffect, useContext } from 'react'
import { ContextGlobal } from '../Utils/globalContext'
import { Calendar, getAllDatesInRange } from "react-multi-date-picker"
import Swal from 'sweetalert2'
import CardReserva from './CardReserva'
import DatosReserva from './DatosReserva'
import Llegada from './Llegada'
import { getDaysArray, normalizarFecha, deshabilitarSeleccionIntermedida } from '../Utils/utils'
import axios from 'axios'
import { API_URL } from '../Utils/api'
import emailjs from '@emailjs/browser'

const ReservaForm = ({ 
    tituloCategoria, 
    tituloProducto, 
    ubicacion, 
    img, 
    reservas, 
    productoId,
    handleConfirmacion 
}) => {

    const { state, dispatch } = useContext(ContextGlobal);
    const [disabledDays, setDisabledDays] = useState([]);
    const [checks, setChecks] = useState([]);
    const [allDates, setAllDates] = useState([]);
    const [sendLoad, setSendLoad] = useState(false);
    const [codigoCambio, setCodigoCambio] = useState(false);
    const [reservaId, setReservaId] = useState(0);
    const [values, setValues] = useState({
        usuarioId: "",
        nombre: "", 
        apellido: "", 
        mail: "", 
        ciudad: "",
        horaLlegada: "",
    })


    //Enviar un mail con un link que al darle click haga un put de usuario y cambie la validacion a true y ademas te envie a la pagina de reserva
    
    const handleChangeCiudad = (value) => {
        setValues({...values, ciudad: value})
    }

    const handleChangeHour = (value) => {
        setValues({...values, horaLlegada: value})
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        let jwt = localStorage.getItem("jwt");
        const headers = { 'Authorization': `Bearer ${jwt}` };
        let objPostReserva = {
            horaComienzo: values.horaLlegada,
            fechaInicial: checks[0]?.day ? normalizarFecha(checks[0]) : "",
            fechaFinal: checks[1]?.day ? normalizarFecha(checks[1]) : "",
            usuario: {
                id: values.usuarioId
            },
            producto: {
                id: productoId
            }
        }
        let objPutUsuario = {
            id: values.usuarioId,
            ciudad: values.ciudad,
            validado: true,
            usuarioRole: "USER"
        }
         
        if (!objPostReserva.horaComienzo || !objPostReserva.fechaFinal || !objPostReserva.fechaInicial || !values.ciudad || !values.usuarioId) {
            mostrarAlerta()
        } else {
            setSendLoad(true);
            axios.post(`${API_URL}/reserva`, objPostReserva, { headers })
            .then(res => {
                handleConfirmacion()
                setSendLoad(false);
                setReservaId(res.data.id);                
                setCodigoCambio(true);
            })
            .catch(err => {
                Swal.fire({
                    icon: 'error',
                    title: 'Oops',
                    text: 'La reserva no pudo ser realizada intentalo de nuevo más tarde!',
                    confirmButtonColor: "#1dbeb4"
                })
                setSendLoad(false);
            })
            axios.put(`${API_URL}/usuario`, objPutUsuario, { headers })
            .then(res => {
                setSendLoad(false)
            })
            .catch(err => {
                Swal.fire({
                    icon: 'error',
                    title: 'Oops',
                    text: 'La reserva no pudo ser realizada intentalo de nuevo más tarde!',
                })
                setSendLoad(false);
            })
        }

    }

    useEffect(() => {
        let dayArr = [];
        reservas?.forEach((reserva) => dayArr = dayArr.concat(getDaysArray(reserva.fechaInicial, reserva.fechaFinal)))
        setDisabledDays(dayArr)
    }, [reservas])

    useEffect(() => {
        if(deshabilitarSeleccionIntermedida(allDates, disabledDays)) {
            setChecks([null, null]);
            setAllDates([]);
        }
    }, [allDates])

    useEffect(() => {
        if (codigoCambio) {
            let jwt = localStorage.getItem("jwt");
            const headers = { 'Authorization': `Bearer ${jwt}` };
            
            axios.get(`${API_URL}/reserva/${reservaId}`, { headers })
            .then(res => {
                let templateParams = {
                    nombre: `${values.nombre} ${values.apellido}`,
                    user_email: values.mail,
                    producto: tituloProducto,
                    checkIn: `${res.data.fechaInicial} ${res.data.horaComienzo}`,
                    checkOut: res.data.fechaFinal,
                    codigo: res.data.codigoReserva
                }
                emailjs.send("service_nc0296c", "template_b5rk35n", templateParams, "8EQLpJMNLWle12z9B")
                .then(res => {
                })
                .catch(err => {
                    console.log(err);
                })
                setCodigoCambio(false)
            })
            
        }
    }, [codigoCambio])

    useEffect(() => {
        let jwt = localStorage.getItem("jwt");
        if(jwt) {
            let partes = jwt.split('.');
            let contenido = atob(partes[1]);
            let datos = JSON.parse(contenido);
            const headers = { 'Authorization': `Bearer ${jwt}` };
            axios.get(`${API_URL}/usuario/email/${datos.sub}`, { headers })
            .then(res => {
                dispatch({
                    type: "register",
                    payload: {
                      ...state,
                      user: res.data,
                      logged: true
                    }
                  })
                setValues({
                    ...values,
                    usuarioId: res.data.id,
                    nombre: res.data.nombre, 
                    apellido: res.data.apellido, 
                    mail: res.data.email, 
                    ciudad: res.data.ciudad
                })
            })
        }

    }, [])

    const mostrarAlerta = () => {
        Swal.fire({
            icon: 'error',
            title: 'La reserva no se realizo',
            text: 'Asegurate de haber elegido un rango de fechas, un horario de llegada y una ciudad!',
            confirmButtonColor: "#1dbeb4"
        })
    }

    return (
        <div className="reservas-container">
            <div className='reserva-form-container'>
                <form id='reserva-form' onSubmit={(e) => handleSubmit(e)}>
                    <DatosReserva values={values} changeCiudad={handleChangeCiudad}/>
                    <div className="calendar-container-reserva">
                        <h3>Seleccioná tu fecha de reserva</h3>
                        <Calendar 
                        value={checks}
                        minDate={new Date()}
                        onChange={dateObjects => {
                            setChecks(dateObjects);
                            setAllDates(getAllDatesInRange(dateObjects));
                        }}
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
                                style: { color: "#8798ad" }
                            }
                        }}              
                        />
                        <Calendar 
                        value={checks}
                        minDate={new Date()}
                        onChange={dateObjects => {
                            setChecks(dateObjects);
                            setAllDates(getAllDatesInRange(dateObjects));
                        }}
                        numberOfMonths={1}
                        disableMonthPicker
                        disableYearPicker
                        range
                        className='custom-calendar mobile'
                        mapDays={({ date }) => {
                            let isDisabled = disabledDays.includes(date.format("YYYY/M/D"))
                            if (isDisabled) return {
                                disabled: true,
                                style: { color: "#8798ad" }
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
                    sendLoad={sendLoad}
                />
            </div>
        </div>
    )
}

export default ReservaForm