import React, { useState, useContext } from 'react'
import { useNavigate } from 'react-router-dom'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faEyeSlash, faEye } from '@fortawesome/free-regular-svg-icons'
import '../styles.css'
import { ContextGlobal } from './Utils/globalContext'
import { normalizarMail } from './Utils/validaciones'

const LoginForm = () => {

    const navigate = useNavigate();
    const [eye, setEye] = useState(false);
    const [error, setError] = useState("");
    const [user, setUser] = useState({mail: "", pass: ""});
    const { state, dispatch } = useContext(ContextGlobal);

    const handleSubmitLogin = (e) => {
        e.preventDefault();
        if (user.mail && user.pass) {
            if (user.mail === state.mail && user.pass === state.pass) {
                navigate("/");
                dispatch({
                    type: "register",
                    payload: {
                        ...state,
                        logged: true
                    }
                })
            } else {
                setError("Las credenciales ingresedas no coinciden");
            }
        } else {
            setError("Por favor ingrese un mail y una contraseña");
        }
        console.log(state);
    }

    return (
        <div className='register-container'>
            <h2>Iniciar sesión</h2>
            <form id='login-form' onSubmit={(e) => handleSubmitLogin(e)}>
                <div className='mail'>
                    <label htmlFor="mail">Correo electrónico</label>
                    <input type="text" name="mail" id="mail" 
                    onChange={(e) => {
                        setUser({...user, mail: normalizarMail(e.target.value)})
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
                        setUser({...user, pass: e.target.value})
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