import { DirectionsCarFilled, CountertopsOutlined, AcUnitOutlined, TvOutlined, PetsOutlined, PoolOutlined, WifiOutlined } from '@mui/icons-material';
import axios from 'axios';
import { API_URL } from './api';

export const elegirServicio = (titulo, color) => {
    if (titulo === "Cocina") {
        return <CountertopsOutlined sx={{color: color}}/>
    }
    if (titulo === "Televisor") {
        return <TvOutlined sx={{color: color}}/>
    }
    if (titulo === "Aire acondicionado") {
        return <AcUnitOutlined sx={{color: color}}/>
    }
    if (titulo === "Apto mascotas") {
        return <PetsOutlined sx={{color: color}}/>
    }
    if (titulo === "Estacionamiento gratuito") {
        return <DirectionsCarFilled sx={{color: color}}/>
    }
    if (titulo === "Pileta") {
        return <PoolOutlined sx={{color: color}}/>
    }
    if (titulo === "Wifi") {
        return <WifiOutlined sx={{color: color}}/>
    }
}

const servicios = {
    "Cocina": { id: 1 },
    "Televisor": { id: 2 },
    "Aire acondicionado": { id: 3 },
    "Apto mascotas": { id: 4 },
    "Estacionamiento gratuito": { id: 5 },
    "Pileta": { id: 6 },
    "Wifi": { id: 7 }
  };
  
  export const tituloXIdServicio = (tituloOId) => {
    if (typeof tituloOId === "string") {
      return servicios[tituloOId];
    } else {
      return Object.keys(servicios).find(key => servicios[key].id === tituloOId);
    }
  };

export const findCategoria = (array, nombreCategoria) => {
    let respuesta;
    array.forEach(categoria => {
        if (categoria.titulo === nombreCategoria) {
            respuesta = categoria.id;
        }
    })
    return respuesta;
}

export const findCategoriaNombre = (array, idCategoria) => {
    let respuesta;
    array.forEach(categoria => {
        if (categoria.id === parseInt(idCategoria)) {
            respuesta = categoria.titulo;
        }
    })
    
    return respuesta;
}

export const horas = [
    {i: 1, value: "10:00:00", label: "10:00 AM"},
    {i: 2, value: "11:00:00", label: "11:00 AM"},
    {i: 3, value: "12:00:00", label: "12:00 AM"},
    {i: 4, value: "13:00:00", label: "01:00 PM"},
    {i: 5, value: "14:00:00", label: "02:00 PM"},
    {i: 6, value: "15:00:00", label: "03:00 PM"},
    {i: 7, value: "16:00:00", label: "04:00 PM"},
    {i: 8, value: "17:00:00", label: "05:00 PM"},
    {i: 9, value: "18:00:00", label: "06:00 PM"},
    {i: 10, value: "19:00:00", label: "07:00 PM"},
    {i: 11, value: "20:00:00", label: "08:00 PM"},
    {i: 12, value: "21:00:00", label: "09:00 PM"},
    {i: 13, value: "22:00:00", label: "10:00 PM"},
    {i: 14, value: "23:00:00", label: "11:00 PM"},
]

export const getDaysArray = function (start, end) {
    for (var arr = [], date = new Date(start); date <= new Date(end); date.setDate(date.getDate() + 1)) {
        let tempDate = new Date(date)
        arr.push(`${tempDate.getFullYear()}/${tempDate.getMonth() + 1}/${tempDate.getDate() + 1}`);
    }
    return arr;
};

export const makeId = (length) => {
    let result = '';
    const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    const charactersLength = characters.length;
    let counter = 0;
    while (counter < length) {
      result += characters.charAt(Math.floor(Math.random() * charactersLength));
      counter += 1;
    }
    return result;
}

export const normalizarFecha = (fecha) => {
    let result = "";
    let dia = fecha.day < 10 ? `0${fecha.day}` : `${fecha.day}`;
    let mes = fecha.month < 10 ? `0${fecha.month}` : `${fecha.month}`;
    result = `${fecha.year}-${mes}-${dia}`
    return result;
}

export const deshabilitarSeleccionIntermedida = (selectedDates, disabledDates) => {
    let bool = false;
    selectedDates.forEach(day => {
        if (disabledDates.includes(day.format("YYYY/M/D"))) {
            bool = true;
        }
    })
    return bool;
}

export const guardarFavUsuario = (user, product) => {
    let jwt = localStorage.getItem("jwt");
    const headers = { 'Authorization': `Bearer ${jwt}` };
    let objPost = {
        producto: {
            id: product
        },
        usuario: {
            id: user
        }
    }

    axios.post(`${API_URL}/favorito`, objPost, { headers })
    .then(res => {

    })
}

export const eliminarFavorito = () => {
    
}