import React, { useState } from 'react'
import '../styles.css'
import { useNavigate, useLocation } from 'react-router-dom'
import { routes } from './Utils/routes';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faBars } from '@fortawesome/free-solid-svg-icons'
import Menu from './Menu';

const Header = () => {

  const navigate = useNavigate();
  const location = useLocation();
  const [mobileOpen, setMobileOpen] = useState(false);

  const handleDrawerToggle = () => {
    setMobileOpen(!mobileOpen);
  };

  return (
    <header>
      <div className='logo-slogan' onClick={() => mobileOpen ? "" : navigate("/")}>
        <img src="/logo.png" alt="Logo Digital Booking"/>
        <h6>Sentite como en tu hogar</h6>
      </div>
      <div className="btns">
        {
          routes.map(({ id, path, title }) => {
              if (id !== 1 && id !== routes.length && location.pathname !== "/register") {
                return (
                  <button onClick={() => navigate(path)} key={id}>
                    {title}
                  </button>
                )
              } else if (id === routes.length && location.pathname !== "/login") {
                return (
                  <button onClick={() => navigate(path)} key={id}>
                    {title}
                  </button>
                )
              }
          })
        }
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