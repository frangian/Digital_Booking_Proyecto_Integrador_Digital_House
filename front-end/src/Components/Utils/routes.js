import Home from "../../Rutas/Home";
import Login from "../../Rutas/Login";
import Register from "../../Rutas/Register";
import { faFacebook, faLinkedinIn, faInstagram, faTwitter } from '@fortawesome/free-brands-svg-icons'
import Producto from "../../Rutas/Producto";



export const routes = [
    { id: 1, path: "/", Element: Home, title: "Home" },
    { id: 2, path: "/register", Element: Register, title: "Crear cuenta" }, 
    { id: 3, path: "/login", Element: Login, title: "Iniciar sesión" },
    { id: 4, path: "/product/:id", Element: Producto, title: "Product detail" },
]

export const socialNetworks = [
    {id: 1, title: "Facebook", path: "https://www.facebook.com/", logo: faFacebook},
    {id: 2, title: "Linkedin", path: "https://ar.linkedin.com/", logo: faLinkedinIn},
    {id: 3, title: "Twitter", path: "https://twitter.com/", logo: faTwitter},
    {id: 4, title: "Instagram", path: "https://www.instagram.com/", logo: faInstagram},
]