import React, { useState, useContext, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faEyeSlash, faEye } from '@fortawesome/free-regular-svg-icons'
import { faExclamationCircle } from '@fortawesome/free-solid-svg-icons'
import { ContextGlobal } from './Utils/globalContext'
import { normalizarMail } from './Utils/validaciones'
import Swal from 'sweetalert2'
import axios from 'axios'

const LoginForm = () => {

    const navigate = useNavigate();
    const [eye, setEye] = useState(false);
    const [error, setError] = useState("");
    const [user, setUser] = useState({email: "", password: ""});
    const [reserva, setReserva] = useState(false);
    const { state, dispatch } = useContext(ContextGlobal);

    const handleSubmitLogin = (e) => {
        e.preventDefault();
        if (user.email && user.password) {  
                axios.post("http://localhost:8080/login", user)
                .then(res => {
                    localStorage.setItem("jwt", res.headers.authorization.split(" ")[1]);
                    navigate("/");
                    dispatch({
                        type: "register",
                        payload: {
                          ...state,
                          logged: true,
                        }
                      })
                })
                .catch(err => {
                    Swal.fire({
                        icon: 'error',
                        title: 'Oops',
                        text: 'Lamentablemente no ha podido iniciar sesión. Por favor intente más tarde',
                    })
                })
        } else {
            setError("Las credenciales ingresadas no coinciden");
        }
    }

    useEffect(() => {
        setReserva(false);
        if(state.location === "reserva") {
            setReserva(true);
            dispatch({
                type: "register",
                payload: {
                  ...state,
                  location: ""
                }
              })            
        }
    }, [])

    return (
        <div className='register-container'>
            <div className={`error-reserva-login ${reserva ? "" : "oculto"}`}>
                <FontAwesomeIcon icon={faExclamationCircle}/> 
                <p>Para realizar una reserva necesitas estar logueado</p>
            </div>
            <h2>Iniciar sesión</h2>
            <form id='login-form' onSubmit={(e) => handleSubmitLogin(e)}>
                <div className='mail'>
                    <label htmlFor="mail">Correo electrónico</label>
                    <input type="text" name="mail" id="mail" 
                    onChange={(e) => {
                        setUser({...user, email: normalizarMail(e.target.value)})
                    }}
                    />
                </div>
                <div className='password'>
                    <label htmlFor="password">Contraseña</label>
                    <FontAwesomeIcon 
                    icon={eye ? faEye : faEyeSlash} 
                    className="icon-eye"
                    onClick={() => setEye(!eye)}
                    />
                    <input type={eye ? "text" : "password"} name="password" id="password" 
                    onChange={(e) => {
                        setUser({...user, password: e.target.value})
                    }}
                    />
                </div>
            </form>
            <p className='login-error'>{error}</p>
            <button type='submit' form='login-form' className='small-button'>Ingresar</button>
            <p>¿Aún no tenes cuenta? <span onClick={() => navigate("/register")}>Registrate</span></p>
        </div>
    )
}

export default LoginForm