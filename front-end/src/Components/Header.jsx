import * as React from 'react';
import PropTypes from 'prop-types';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import CssBaseline from '@mui/material/CssBaseline';
import Divider from '@mui/material/Divider';
import Drawer from '@mui/material/Drawer';
import IconButton from '@mui/material/IconButton';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemText from '@mui/material/ListItemText';
import MenuIcon from '@mui/icons-material/Menu';
import CloseIcon from '@mui/icons-material/Close';
import Toolbar from '@mui/material/Toolbar';
import Button from '@mui/material/Button';
import { routes, socialNetworks } from './Utils/routes';
import { useLocation, useNavigate } from 'react-router-dom';
import { Typography } from '@mui/material';
import { navbar, logoContainer, btnNavbar, btnContainer, navbarContainer, menuTop, menuBottom, socialNetworkLogo } from '../styles.js'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'

const drawerWidth = "70%";

function Header(props) {
  const { window } = props;
  const [mobileOpen, setMobileOpen] = React.useState(false);
  const navigate = useNavigate();
  const location = useLocation();

  const handleDrawerToggle = () => {
    setMobileOpen((prevState) => !prevState);
  };

  const drawer = (
    <Box sx={{ height: "100vh" }}>
      <Box sx={menuTop}>
        <Box sx={{ textAlign: "left", width: "100%" }}>
          <IconButton onClick={handleDrawerToggle}>
            <CloseIcon sx={{ color: "#F3F1ED" }}/>
          </IconButton>
        </Box>
        <Typography sx={{ paddingRight: "15px", textTransform: "capitalize" }} variant="h6">
          Menú
        </Typography>
      </Box>
      <List sx={{ height: "67%" }}>
        {routes.map(({ id, path, title }) => {
          if (id !== 1 && id !== routes.length && location.pathname !== "/login") {
            return (
              <>
                <ListItem key={id} disablePadding onClick={() => navigate(path)}>
                  <ListItemButton sx={{ textAlign: 'right' }}>
                    <ListItemText primary={title} />
                  </ListItemButton>
                </ListItem>
                <hr style={{ color: "#000" }}/>
              </>
            )
          } else if (id === routes.length && location.pathname !== "/register") {
            return (
              <>
                <ListItem key={id} disablePadding onClick={() => navigate(path)}>
                  <ListItemButton sx={{ textAlign: 'right' }}>
                    <ListItemText primary={title} />
                  </ListItemButton>
                </ListItem>
                <hr style={{ color: "#000" }}/>
              </>
            )
          }
        })}
      </List>
      <Box sx={menuBottom}>
        {
          socialNetworks.map(({id, path, logo}) => (
            <a href={path} key={id} target="e_blank">
              <FontAwesomeIcon icon={logo} style={socialNetworkLogo}/>
            </a>
          ))
        }
      </Box>
    </Box>
  );

  const container = window !== undefined ? () => window().document.body : undefined;

  return (
    <Box sx={{ display: 'flex', height: "12vh" }}>
      <CssBaseline />
      <AppBar component="nav" sx={navbarContainer}>
        <Toolbar>
          <Box sx={navbar}>
            <Box sx={logoContainer}>
              <img src="/logo.png" alt="" onClick={() => navigate("/")} 
              style={{ cursor: "pointer" }}/>
              <Typography sx={{ marginLeft: "15px", fontWeight: "300", display: { xs: "none", sm: 'none', md: "block" } }} variant="h6">
                Sentite como en tu hogar
              </Typography>
            </Box>
            <Box sx={btnContainer}>
              {
                routes.map(({ id, path, title }) => {
                    if (title !== "Home") {
                      return (
                        <Button sx={btnNavbar} variant="outlined" onClick={() => navigate(path)} key={id}>
                          {title}
                        </Button>
                      )
                    }
                })
              }
            </Box>
          </Box>
          <IconButton
            color="inherit"
            aria-label="open drawer"
            edge="start"
            onClick={handleDrawerToggle}
            sx={{ mr: 2, display: { sm: 'none' } }}
          >
            <MenuIcon sx={{ color: "#545776" }}/>
          </IconButton>
        </Toolbar>
      </AppBar>
      <Box component="nav" sx={{ display: "flex" }}>
        <Drawer
          container={container}
          variant="temporary"
          open={mobileOpen}
          onClose={handleDrawerToggle}
          ModalProps={{
            keepMounted: true,
          }}
          sx={{
            display: { xs: 'flex', sm: 'none' },
            '& .MuiDrawer-paper': { boxSizing: 'border-box', width: drawerWidth },
          }}
        >
          {drawer}
        </Drawer>
      </Box>
    </Box>
  );
}

Header.propTypes = {
  /**
   * Injected by the documentation to work in an iframe.
   * You won't need it on your project.
   */
  window: PropTypes.func,
};

export default Header;