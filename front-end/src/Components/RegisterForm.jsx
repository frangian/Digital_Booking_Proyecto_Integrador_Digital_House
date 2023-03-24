import React, { useState, useContext } from 'react'
import { useNavigate } from 'react-router-dom'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faEyeSlash, faEye } from '@fortawesome/free-regular-svg-icons'
import { ContextGlobal } from './Utils/globalContext'
import { validarMail, validarPassword, confirmarPassword, campoRequerido, normalizarMail, normalizarNombre } from './Utils/validaciones'
import axios from 'axios'
import CircularProgress from '@mui/material/CircularProgress';
import { API_URL } from './Utils/api'

const RegisterForm = () => {

    const navigate = useNavigate();
    const [eye, setEye] = useState(false);
    const [errorNombre, setErrorNombre] = useState("");
    const [errorApellido, setErrorApellido] = useState("");
    const [errorMail, setErrorMail] = useState("");
    const [errorPass, setErrorPass] = useState("");
    const [errorConfirmPass, setErrorConfirmPass] = useState("");
    const [sendLoad, setSendLoad] = useState(false);
    const [user, setUser] = useState({nombre: "", apellido: "", mail: "", pass: "", confirmPass: ""})
    const { state, dispatch } = useContext(ContextGlobal);

    const resetErrors = () => {
        setErrorNombre("");
        setErrorApellido("");
        setErrorMail("");
        setErrorPass("");
        setErrorConfirmPass("");
    }

    const handleRegisterSubmit = (e) => {
        e.preventDefault();
        resetErrors();
        let envio = true;
        if (!campoRequerido(user.nombre)) {
            envio = false;
            setErrorNombre("Este campo es obligatorio");
        }
        if (!campoRequerido(user.apellido)) {
            envio = false;
            setErrorApellido("Este campo es obligatorio");
        }
        if (!campoRequerido(user.mail)) {
            envio = false;
            setErrorMail("Este campo es obligatorio");
        } else if (!validarMail(user.mail)) {
            envio = false;
            setErrorMail("El formato del mail no es válido");
        }
        if (!campoRequerido(user.pass)) {
            envio = false;
            setErrorPass("Este campo es obligatorio");
        } else if (!validarPassword(user.pass)) {
            envio = false;
            setErrorPass("Minimo 6 caracteres");
        }
        if (!campoRequerido(user.confirmPass)) {
            envio = false;
            setErrorConfirmPass("Este campo es obligatorio");
        } else if (!confirmarPassword(user.pass, user.confirmPass)) {
            envio = false;
            setErrorConfirmPass("Las contraseñas no coinciden");
        }
        if (envio) {
            setSendLoad(true);
            let objRegister = {
                nombre: user.nombre,
                apellido: user.apellido,
                email: user.mail,
                password: user.pass
            }
            let objLogin = {
                email: user.mail,
                password: user.pass
            }
            axios.post(`${API_URL}/usuario/registro`, objRegister)
            .then(res => {
                setSendLoad(false);
                dispatch({
                    type: "register",
                    payload: {
                        ...state, 
                        user: res.data,
                        logged: true
                    }
                })
                axios.post(`${API_URL}/login`, objLogin)
                .then(res => {
                    localStorage.setItem("jwt", res.headers.authorization.split(" ")[1])
                    navigate("/")
                })
            }).catch(err => {
                setErrorMail("Este correo ya esta registrado")
            })
        }
    }

    return (
        <div className='register-container'>
            <h2>Crear cuenta</h2>
            <form id='register-form' onSubmit={(e) => handleRegisterSubmit(e)}>
                <fieldset>
                    <div className={`nombre ${errorNombre ? "error" : ""}`}>
                        <label htmlFor="nombre">Nombre</label>
                        <input type="text" name="nombre" id="nombre" 
                        onChange={(e) => {
                            e.target.parentElement.classList.remove("error");
                            setErrorNombre("");
                            setUser({...user, nombre: normalizarNombre(e.target.value)})
                        }}
                        />
                        <p>{errorNombre}</p>
                    </div>
                    <div className={`apellido ${errorApellido ? "error" : ""}`}>
                        <label htmlFor="apellido">Apellido</label>
                        <input type="text" name="apellido" id="apellido" 
                        onChange={(e) => {
                            e.target.parentElement.classList.remove("error");
                            setErrorApellido("");
                            setUser({...user, apellido: normalizarNombre(e.target.value)})
                        }}
                        />
                        <p>{errorApellido}</p>
                    </div>                 
                </fieldset>
                <div className={`mail ${errorMail ? "error" : ""}`}>
                    <label htmlFor="mail">Correo electrónico</label>
                    <input type="text" name="mail" id="mail" 
                    onChange={(e) => {
                        e.target.parentElement.classList.remove("error");
                        setErrorMail("");
                        setUser({...user, mail: normalizarMail(e.target.value)})
                    }}
                    />
                    <p>{errorMail}</p>
                </div>
                <div className={`password ${errorPass ? "error" : ""}`}>
                    <label htmlFor="password">Contraseña</label>
                    <FontAwesomeIcon 
                    icon={eye ? faEye : faEyeSlash} 
                    className="icon-eye"
                    onClick={() => setEye(!eye)}
                    />
                    <input type={eye ? "text" : "password"} name="password" id="password" 
                    onChange={(e) => {
                        e.target.parentElement.classList.remove("error");
                        setErrorPass("");
                        setUser({...user, pass: e.target.value})
                    }}
                    />
                    <p>{errorPass}</p>
                </div>
                <div className={`confirm ${errorConfirmPass ? "error" : ""}`}>
                    <label htmlFor="confirm">Confirmar contraseña</label>
                    <input type="password" name="confirm" id="confirm"
                    onChange={(e) => {
                        e.target.parentElement.classList.remove("error")
                        setErrorConfirmPass("");
                        setUser({...user, confirmPass: e.target.value})
                    }}
                    />
                    <p>{errorConfirmPass}</p>
                </div>
            </form>
            <button type='submit' form='register-form' className='small-button' id='submit-register' disabled={sendLoad}>
                {
                    sendLoad ? <CircularProgress sx={{ color: "#fff", marginTop: "5px"}} size="1.4rem"/> : "Crear cuenta"
                }
            </button>
            <p>¿Ya tienes una cuenta? <span onClick={() => navigate("/login")}>Iniciar sesión</span></p>
        </div>
    )
}

export default RegisterForm