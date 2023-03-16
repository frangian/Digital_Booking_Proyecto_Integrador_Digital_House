import React, { useState, useEffect, useContext } from 'react'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faLocationDot, faStar } from '@fortawesome/free-solid-svg-icons'
import ShareOutlinedIcon from '@mui/icons-material/ShareOutlined';
import FavoriteBorderOutlinedIcon from '@mui/icons-material/FavoriteBorderOutlined';
import FavoriteIcon from '@mui/icons-material/Favorite';
import { useNavigate, useParams } from 'react-router-dom'
import { IconButton } from '@mui/material';
import Servicios from '../Components/Servicios';
import Calendario from '../Components/CalendarComponent/CalendarContainer';
import Map from '../Components/Map';
import ImgContainer from '../Components/ImgComponent/ImgContainer';
import axios from 'axios'
import { ContextGlobal } from '../Components/Utils/globalContext';
import ProductHeader from '../Components/ProductHeader';
import PoliticasProducto from '../Components/PoliticasProducto';

const Producto = () => {

    const { id } = useParams();
    const { state, dispatch } = useContext(ContextGlobal);
    const [data, setData] = useState({});
    const [images, setImages] = useState([]);
    const [normas, setNormas] = useState([]);
    const [seguridad, setSeguridad] = useState([]);
    const [ciudad, setCiudad] = useState({});
    const [services, setServices] = useState([]);
    const [reservas, setReservas] = useState([]);
    const [like, setLike] = useState(false);

    useEffect(() => {
        const seccion = document.getElementById('mapa');
        if (seccion && state.map === true) {
          seccion.scrollIntoView({behavior: "smooth"});
          dispatch({
            type: "register",
            payload: {
                ...state,
                map: false
            }
        })
        } else {
            window.scrollTo({top: 0})
        }
      }, []);

    useEffect(() => {
        axios.get(`http://localhost:8080/producto/${id}`)
        .then(res => {
            setData(res.data);
            setCiudad(res.data.ciudad);
            setImages(res.data.imagenes);
            setNormas(res.data.normas.split(","));
            setSeguridad(res.data.seguridad.split(","));
        })
        axios.get(`http://localhost:8080/caracteristica/producto/${id}`)
        .then(res => {
            setServices(res.data);
        })
        axios.get(`http://localhost:8080/reserva/producto/${id}`)
        .then(res => {
            setReservas(res.data)
        }) 
    }, [id])

    return (
        <div className='product-page'>
            <ProductHeader tituloCategoria={data?.categoria} tituloProducto={data?.titulo}/>
            <div className="top-location">
                <div className="left-top-location">
                    <FontAwesomeIcon icon={faLocationDot} className="location-icon"/>
                    <p>
                        {data.ciudad?.nombre}, {ciudad?.provincia}, {ciudad?.pais}
                        <br /> 
                        {data?.descripcion_ubicacion}
                    </p>
                </div>
                <div className="puntuacion">
                    <div className="left-puntuacion">
                        <p>Muy bueno</p>
                        <div className="stars">
                            <FontAwesomeIcon icon={faStar} className="icono icono-verde" />
                            <FontAwesomeIcon icon={faStar} className="icono icono-verde" />
                            <FontAwesomeIcon icon={faStar} className="icono icono-verde" />
                            <FontAwesomeIcon icon={faStar} className="icono icono-verde" />
                            <FontAwesomeIcon icon={faStar} className="icono" />
                        </div>
                    </div>
                    <div className="number"><p>{data?.puntuacion}</p></div>
                </div>
            </div>
            <div className='interacciones'>
                <IconButton>
                    <ShareOutlinedIcon />
                </IconButton>
                <IconButton onClick={() => setLike(!like)}>
                    {like ? <FavoriteIcon sx={{color: "red"}}/> : <FavoriteBorderOutlinedIcon />}
                </IconButton>
            </div>
            <ImgContainer imgList={images}/>
            <div className="descripcion">
                <h3>Alójate en el corazón de {ciudad?.nombre}</h3>
                <p>{data?.descripcion_producto}</p>
            </div>
            <Servicios servicios={services}/>
            <Calendario productId={id} reservas={reservas}/>
            <Map url={data?.url_ubicacion} titulo={`${ciudad?.nombre}, ${ciudad?.pais}`}/>
            <PoliticasProducto 
            cancelacion={data?.cancelacion}
            normas={normas}
            seguridad={seguridad}
            />
        </div>
    )
}

export default Producto