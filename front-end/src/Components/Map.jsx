import React from 'react'

const Map = () => {
  return (
    <div className='map'>
        <h3>¿Dónde vas a estar?</h3>
        <p>Buenos Aires, Argentina</p>  
        <div className="map-container">
        <iframe 
        src="https://www.google.com/maps/embed?pb=!1m14!1m8!1m3!1d13142.323927441405!2d-59.1213975!3d-34.5641632!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x95bc7deea7385433%3A0xcfc11574d47db937!2zQmFzw61saWNhIGRlIEx1asOhbiAtIEx1asOhbg!5e0!3m2!1ses-419!2sar!4v1677601176867!5m2!1ses-419!2sar" 
        style={{ border: "0"}}
        allowFullScreen=""
        loading="lazy" 
        referrerPolicy="no-referrer-when-downgrade">
        </iframe>
        </div>
    </div>
  )
}

export default Map