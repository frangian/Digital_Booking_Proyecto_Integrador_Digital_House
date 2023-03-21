import React, { useState, useContext, useEffect } from 'react'
import { useNavigate, useLocation } from 'react-router-dom'
import { routes } from './Utils/routes';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faBars, faX } from '@fortawesome/free-solid-svg-icons'
import Menu from './Menu';
import { ContextGlobal } from './Utils/globalContext';
import axios from 'axios';

const Header = () => {

  const navigate = useNavigate();
  const location = useLocation();
  const [mobileOpen, setMobileOpen] = useState(false);
  const { state, dispatch } = useContext(ContextGlobal);

  const handleDrawerToggle = () => {
    setMobileOpen(!mobileOpen);
  };

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
    if (location.pathname.includes("reservas")) {
      navigate("/")
    }
  }

useEffect(() => {

  dispatch({
    type: "register",
    payload: {
      ...state,
      location: ""
    }
  })

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
        console.log(1);
    })
  }
}, [])

  return (
    <header onClick={() => { console.log(location); }}>
      <div className='logo-slogan' onClick={() => mobileOpen ? "" : navigate("/")}>
        <img src="/logo.png" alt="Logo Digital Booking"/>
        <h6 onMouseOver={() => console.log(state)}>Sentite como en tu hogar</h6>
      </div>
      <div className={`btns ${!state.logged ? "" : "oculto"}`}>
        {
          routes.map(({ id, path, title }) => {
              if (id === 2 && id !== routes.length && location.pathname !== "/register" ) {
                return (
                  <button className='small-button' onClick={() => navigate(path)} key={id}>
                    {title}
                  </button>
                )
              } else if (id === 3 && location.pathname !== "/login") {
                return (
                  <button className='small-button' onClick={() => navigate(path)} key={id}>
                    {title}
                  </button>
                )
              }
          })
        }
      </div>
      <div className={`header-logged-user ${!state.logged ? "oculto" : ""}`} >
        <div className='avatar'>
          <p>{state.user.nombre[0]}{state.user.apellido[0]}</p>
        </div>
        <div className="saludo">
          <p>Hola,</p>
          <p className='nombres'>{state.user.nombre} {state.user.apellido}</p>
        </div>
        <FontAwesomeIcon icon={faX} className="x-icon" onClick={() => cerrarSesion()}/>
      </div>
      <button className='icon-button' onClick={handleDrawerToggle} style={mobileOpen ? {display: "none"} : {display: "block"}}>
        <FontAwesomeIcon icon={faBars} className="menu-icon"/>
      </button>
      <div className="drawer" onClick={handleDrawerToggle} style={mobileOpen ? {display: "block"} : {display: "none"}}>
        <Menu open={mobileOpen} openClose={handleDrawerToggle}/>
      </div>
    </header>
  )
}

export default Header