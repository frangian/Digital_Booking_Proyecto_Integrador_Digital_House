import React from 'react'
import { socialNetworks } from './Utils/routes';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'

const Footer = () => {
  return (
    <footer>
      <p>©2021 Digital Booking</p>
      <div>
        {
          socialNetworks.map(({ id, logo, path }) => (
              <a href={path} key={id} target="e_blank">
                  <FontAwesomeIcon icon={logo}/>
              </a>
          ))
        }
      </div>
    </footer>
  )
}

export default Footer
