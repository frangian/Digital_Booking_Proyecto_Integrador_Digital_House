import Home from "../../Rutas/Home";
import Login from "../../Rutas/Login";
import Register from "../../Rutas/Register";

export const routes = [
    { id: 1, path: "/", Element: Home, title: "Home" },
    { id: 2, path: "/login", Element: Login, title: "login" },
    { id: 3, path: "/register", Element: Register, title: "register" }, 
]