import React, { useContext, useEffect } from 'react'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faX } from '@fortawesome/free-solid-svg-icons'
import { socialNetworks, routes } from './Utils/routes'
import { useNavigate, useLocation } from 'react-router-dom'
import { ContextGlobal } from './Utils/globalContext'
import axios from 'axios'

const Menu = ({ open, openClose }) => {

    const navigate = useNavigate();
    const location = useLocation();
    const { state, dispatch } = useContext(ContextGlobal);

    const cerrarSesion = () => {
        dispatch({
            type: "register",
            payload: {
              ...state,
              user: {nombre: "Juan", apellido: "Gomez"},
              logged: false
            }
        })
        localStorage.removeItem("jwt");
    }

    useEffect(() => {
        let jwt = localStorage.getItem("jwt");
        if (jwt) {
          let partes = jwt.split('.');
          let contenido = atob(partes[1]);
          let datos = JSON.parse(contenido);
          const headers = { 'Authorization': `Bearer ${jwt}` };
          axios.get(`http://localhost:8080/usuario/email/${datos.sub}`, { headers })
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
      }, [])

    return (
        <div style={open ? {display: "block"} : {display: "none"}} className="menu">
            <div className="top-menu">
                <FontAwesomeIcon icon={faX} onClick={() => openClose()} style={{ cursor: "pointer" }}/>
                <h5 className={`${state.logged ? "oculto" : ""}`}>MENÚ</h5>
                <div className={`menu-logged-user ${state.logged ? "" : "oculto"}`}>
                    <div className='avatar'>
                        <p>{state.user.nombre[0]}{state.user.apellido[0]}</p>
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