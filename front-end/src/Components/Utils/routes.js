import Home from "../../Rutas/Home";
import Login from "../../Rutas/Login";
import Register from "../../Rutas/Register";
import Producto from "../../Rutas/Producto";
import Reservas from "../../Rutas/Reservas"
import ReservasUsuario from "../../Rutas/ReservasUsuario";
import Admin from "../../Rutas/Admin";
import AdminTablePage from "../../Rutas/AdminTablePage";
import Favoritos from "../../Rutas/Favoritos"
import Validado from "../../Rutas/Validado";
import { faFacebook, faLinkedinIn, faInstagram, faTwitter } from '@fortawesome/free-brands-svg-icons'



export const routes = [
    { id: 1, path: "/", Element: Home, title: "Home" },
    { id: 2, path: "/register", Element: Register, title: "Crear cuenta" }, 
    { id: 3, path: "/login", Element: Login, title: "Iniciar sesión" },
    { id: 4, path: "/product/:id", Element: Producto, title: "Product detail" },
    { id: 5, path: "/product/:id/reservas", Element: Reservas, title: "Reserva producto" },
    { id: 6, path: "/reservas", Element: ReservasUsuario, title: "Reservas usuario" },
    { id: 7, path: "/favoritos", Element: Favoritos, title: "Favoritos" },
    { id: 8, path: "/admin", Element: Admin, title: "Admin" },
    { id: 9, path: "/adminTable", Element: AdminTablePage, title: "Admin Table" },
    { id: 10, path: "/validado", Element: Validado, title: "validado" },
]

export const socialNetworks = [
    {id: 1, title: "Facebook", path: "https://www.facebook.com/", logo: faFacebook},
    {id: 2, title: "Linkedin", path: "https://ar.linkedin.com/", logo: faLinkedinIn},
    {id: 3, title: "Twitter", path: "https://twitter.com/", logo: faTwitter},
    {id: 4, title: "Instagram", path: "https://www.instagram.com/", logo: faInstagram},
]