import Home from "../../Rutas/Home";
import Login from "../../Rutas/Login";
import Register from "../../Rutas/Register";


export const routes = [
    { id: 1, path: "/", Element: Home, title: "Home" },
    { id: 2, path: "/login", Element: Login, title: "Iniciar sesión" },
    { id: 3, path: "/register", Element: Register, title: "Crear cuenta" }, 
]

export const socialNetworks = [
    {id: 1, title: "Facebook", path: "https://www.facebook.com/", img: "/facebook.svg"},
    {id: 2, title: "Linkedin", path: "https://ar.linkedin.com/", img: "/linkedin-in.svg"},
    {id: 3, title: "Twitter", path: "https://twitter.com/", img: "/twitter.svg"},
    {id: 4, title: "Instagram", path: "https://www.instagram.com/", img: "/instagram.svg"},
]