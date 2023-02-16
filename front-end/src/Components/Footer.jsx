import React from 'react'
import { Typography, Box } from '@mui/material';
import { socialNetworks } from './Utils/routes';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { footerConatiner, footerLogo } from '../styles';

const Footer = () => {
  return (
    <footer style={footerConatiner}>
      <Typography
      variant="h6"
      component="div"
      >
        ©2021 Digital Booking
      </Typography>
      <Box sx={{ display: {xs: "none", sm: "none", md: "block"} }}>
        {
          socialNetworks.map(({id, logo, path}) => (
            <a href={path} target="e_blank" key={id}>
              <FontAwesomeIcon icon={logo} style={footerLogo}/>
            </a>
          ))
        }
      </Box>
    </footer>
  )
}

export default Footer