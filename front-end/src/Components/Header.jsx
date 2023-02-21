import React, { useState, useContext } from 'react'
import { useNavigate, useLocation } from 'react-router-dom'
import { routes } from './Utils/routes';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faBars, faX } from '@fortawesome/free-solid-svg-icons'
import Menu from './Menu';
import { ContextGlobal } from './Utils/globalContext';

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
            logged: false
        }
    })
    navigate("/login");
}

  return (
    <header>
      <div className='logo-slogan' onClick={() => mobileOpen ? "" : navigate("/")}>
        <img src="/logo.png" alt="Logo Digital Booking"/>
        <h6>Sentite como en tu hogar</h6>
      </div>
      <div className={`btns ${state.logged ? "oculto" : ""}`}>
        {
          routes.map(({ id, path, title }) => {
              if (id !== 1 && id !== routes.length && location.pathname !== "/register") {
                return (
                  <button className='small-button' onClick={() => navigate(path)} key={id}>
                    {title}
                  </button>
                )
              } else if (id === routes.length && location.pathname !== "/login") {
                return (
                  <button className='small-button' onClick={() => navigate(path)} key={id}>
                    {title}
                  </button>
                )
              }
          })
        }
      </div>
      <div className={`header-logged-user ${state.logged ? "" : "oculto"}`} >
        <div className='avatar'>
          <p>{state.nombre[0]}{state.apellido[0]}</p>
        </div>
        <div className="saludo">
          <p>Hola,</p>
          <p className='nombres'>{state.nombre} {state.apellido}</p>
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