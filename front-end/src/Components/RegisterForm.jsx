import React, { useState, useContext } from 'react'
import { useNavigate } from 'react-router-dom'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faEyeSlash, faEye } from '@fortawesome/free-regular-svg-icons'
import { ContextGlobal } from './Utils/globalContext'
import { validarMail, validarPassword, confirmarPassword, campoRequerido, normalizarMail, normalizarNombre } from './Utils/validaciones'
import '../styles.css'

const RegisterForm = () => {

    const navigate = useNavigate();
    const [eye, setEye] = useState(false);
    const [confirmPass, setConfirmPass] = useState("");
    const [errorNombre, setErrorNombre] = useState("");
    const [errorApellido, setErrorApellido] = useState("");
    const [errorMail, setErrorMail] = useState("");
    const [errorPass, setErrorPass] = useState("");
    const [errorConfirmPass, setErrorConfirmPass] = useState("");
    const [user, setUser] = useState({nombre: "", apellido: "", mail: "", pass: ""})
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
        if (!campoRequerido(confirmPass)) {
            setErrorConfirmPass("Este campo es obligatorio");
        } else if (!confirmarPassword(user.pass, confirmPass)) {
            envio = false;
            setErrorConfirmPass("Las contraseñas no coinciden");
        }
        if (envio) {
            dispatch({
                type: "register",
                payload: {
                    ...state,
                    nombre: user.nombre,
                    apellido: user.apellido,
                    mail: user.mail,
                    pass: user.pass,
                    logged: true
                }
            })
            navigate("/")
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
                        setConfirmPass(e.target.value)
                    }}
                    />
                    <p>{errorConfirmPass}</p>
                </div>
            </form>
            <button type='submit' form='register-form' className='small-button'>Crear cuenta</button>
            <p>¿Ya tienes una cuenta? <span onClick={() => navigate("/login")}>Iniciar sesión</span></p>
        </div>
    )
}

export default RegisterForm