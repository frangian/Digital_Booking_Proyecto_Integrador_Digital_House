import React from 'react'
import Skeleton, { SkeletonTheme } from 'react-loading-skeleton'

const Map = ({ url, titulo }) => {
  return (
    <div className='map'>
        <h3>¿Dónde vas a estar?</h3>
        <p>{titulo.includes("undefined") ? <Skeleton width={"150px"}/> : titulo}</p>  
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