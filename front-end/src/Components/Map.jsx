import React from 'react'

const Map = ({ url, titulo }) => {
  return (
    <div className='map'>
        <h3>¿Dónde vas a estar?</h3>
        <p>{titulo}</p>  
        <div className="map-container">
        <iframe 
        src={url} 
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