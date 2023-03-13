import { DirectionsCarFilled, CountertopsOutlined, AcUnitOutlined, TvOutlined, PetsOutlined, PoolOutlined, WifiOutlined } from '@mui/icons-material';

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

export const horas = [
    {i: 0, value: "01:00 AM", label: "01:00 AM"},
    {i: 1, value: "02:00 AM", label: "02:00 AM"},
    {i: 2, value: "03:00 AM", label: "03:00 AM"},
    {i: 3, value: "04:00 AM", label: "04:00 AM"},
    {i: 4, value: "05:00 AM", label: "05:00 AM"},
    {i: 5, value: "06:00 AM", label: "06:00 AM"},
    {i: 6, value: "07:00 AM", label: "07:00 AM"},
    {i: 7, value: "08:00 AM", label: "08:00 AM"},
    {i: 8, value: "09:00 AM", label: "09:00 AM"},
    {i: 9, value: "10:00 AM", label: "10:00 AM"},
    {i: 10, value: "11:00 AM", label: "11:00 AM"},
    {i: 11, value: "12:00 AM", label: "12:00 AM"},
    {i: 12, value: "01:00 PM", label: "01:00 PM"},
    {i: 13, value: "02:00 PM", label: "02:00 PM"},
    {i: 14, value: "03:00 PM", label: "03:00 PM"},
    {i: 15, value: "04:00 PM", label: "04:00 PM"},
    {i: 16, value: "05:00 PM", label: "05:00 PM"},
    {i: 17, value: "06:00 PM", label: "06:00 PM"},
    {i: 18, value: "07:00 PM", label: "07:00 PM"},
    {i: 19, value: "08:00 PM", label: "08:00 PM"},
    {i: 20, value: "09:00 PM", label: "09:00 PM"},
    {i: 21, value: "10:00 PM", label: "10:00 PM"},
    {i: 22, value: "11:00 PM", label: "11:00 PM"},
    {i: 23, value: "12:00 PM", label: "12:00 PM"},
]

