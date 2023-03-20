import React, { useContext } from 'react'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faX } from '@fortawesome/free-solid-svg-icons'
import { socialNetworks, routes } from './Utils/routes'
import { useNavigate, useLocation } from 'react-router-dom'
import { ContextGlobal } from './Utils/globalContext'

const Menu = ({ open, openClose }) => {

    const navigate = useNavigate();
    const location = useLocation();
    const { state, dispatch } = useContext(ContextGlobal);

    const cerrarSesion = () => {
        dispatch({
            type: "register",
            payload: {
                ...state,
                logged: false
            }
        })
        navigate("/login");
    }

    return (
        <div style={open ? {display: "block"} : {display: "none"}} className="menu">
            <div className="top-menu">
                <FontAwesomeIcon icon={faX} onClick={() => openClose()} style={{ cursor: "pointer" }}/>
                <h5 className={`${state.logged ? "oculto" : ""}`}>MENÚ</h5>
                <div className={`menu-logged-user ${state.logged ? "" : "oculto"}`}>
                    <div className='avatar'>
                        <p>{"J"}{"G"}</p>
                    </div>
                    <div className="saludo">
                        <p>Hola,</p>
                        <p className='nombres'>{state.user.nombre} {state.user.apellido}</p>
                    </div>
                </div>
            </div>
            <div className={`mid-menu ${state.logged ? "oculto" : ""}`}>
            {
            routes.map(({ id, path, title }) => {
                if (id === 2 && id !== routes.length && location.pathname !== "/register") {
                    return (
                    <button onClick={() => navigate(path)} key={id}>
                        {title}
                    </button>
                    )
                } else if (id === 3 && location.pathname !== "/login") {
                    return (
                    <button onClick={() => navigate(path)} key={id}>
                        {title}
                    </button>
                    )
                }
            })
            }
            </div>
            <div className={`cerrar-sesion ${state.logged ? "" : "oculto"}`}>
                <p>¿Deseas <span onClick={() => cerrarSesion()}>cerrar sesión</span>?</p>
            </div>
            <div className="bottom-menu">
                {
                    socialNetworks.map(({ id, logo, path }) => (
                        <a href={path} key={id} target="e_blank">
                            <FontAwesomeIcon icon={logo}/>
                        </a>
                    ))
                }
            </div>
        </div>
    )
}

export default Menu