import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faEyeSlash, faEye } from '@fortawesome/free-regular-svg-icons'
import '../styles.css'

const RegisterForm = () => {

    const navigate = useNavigate();
    const [eye, setEye] = useState(false);

    return (
        <div className='register-container'>
            <h2>Crear cuenta</h2>
            <form id='register-form'>
                <fieldset>
                    <div className="nombre">
                        <label htmlFor="nombre">Nombre</label>
                        <input type="text" name="nombre" id="nombre" />
                    </div>
                    <div className="apellido">
                        <label htmlFor="apellido">Apellido</label>
                        <input type="text" name="apellido" id="apellido" />
                    </div>                 
                </fieldset>
                <label htmlFor="mail">Correo electrónico</label>
                <input type="text" name="mail" id="mail" />
                <label htmlFor="password">Contraseña</label>
                <div className='password'>
                    <FontAwesomeIcon 
                    icon={eye ? faEye : faEyeSlash} 
                    className="icon-eye"
                    onClick={() => setEye(!eye)}
                    />
                    <input type={eye ? "text" : "password"} name="password" id="password" />
                </div>
                <label htmlFor="confirm">Confirmar contraseña</label>
                <input type="password" name="confirm" id="confirm" />
            </form>
            <button type='submit' form='register-form' className='small-button'>Crear cuenta</button>
            <p>¿Ya tienes una cuenta? <span onClick={() => navigate("/login")}>Iniciar sesión</span></p>
        </div>
    )
}

export default RegisterForm