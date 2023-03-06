import { DirectionsCarFilled, CountertopsOutlined, AcUnitOutlined, TvOutlined, PetsOutlined, PoolOutlined, WifiOutlined } from '@mui/icons-material';

export const elegirServicio = (titulo) => {
    if (titulo === "Cocina") {
        return <CountertopsOutlined sx={{color: "#1DBEB4"}}/>
    }
    if (titulo === "Televisor") {
        return <TvOutlined sx={{color: "#1DBEB4"}}/>
    }
    if (titulo === "Aire acondicionado") {
        return <AcUnitOutlined sx={{color: "#1DBEB4"}}/>
    }
    if (titulo === "Apto mascotas") {
        return <PetsOutlined sx={{color: "#1DBEB4"}}/>
    }
    if (titulo === "Estacionamiento gratuito") {
        return <DirectionsCarFilled sx={{color: "#1DBEB4"}}/>
    }
    if (titulo === "Pileta") {
        return <PoolOutlined sx={{color: "#1DBEB4"}}/>
    }
    if (titulo === "Wifi") {
        return <WifiOutlined sx={{color: "#1DBEB4"}}/>
    }
}

