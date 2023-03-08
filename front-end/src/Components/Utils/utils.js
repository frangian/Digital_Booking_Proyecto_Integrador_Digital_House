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

