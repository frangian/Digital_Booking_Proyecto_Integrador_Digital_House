import React, { useState, useEffect } from 'react'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faLocationDot, faChevronLeft, faStar } from '@fortawesome/free-solid-svg-icons'
import ShareOutlinedIcon from '@mui/icons-material/ShareOutlined';
import FavoriteBorderOutlinedIcon from '@mui/icons-material/FavoriteBorderOutlined';
import FavoriteIcon from '@mui/icons-material/Favorite';
import { useNavigate, useParams } from 'react-router-dom'
import { IconButton } from '@mui/material';
import Servicios from '../Components/Servicios';
import Calendario from '../Components/Calendario';
import Map from '../Components/Map';
import ImgContainer from '../Components/ImgComponent/ImgContainer';
import axios from 'axios'

const Producto = () => {

    const { id } = useParams();
    const navigate = useNavigate();
    const [data, setData] = useState({});
    const [images, setImages] = useState([]);
    const [normas, setNormas] = useState([]);
    const [seguridad, setSeguridad] = useState([]);
    const [ciudad, setCiudad] = useState({});
    const [categoria, setCategoria] = useState({});
    const [services, setServices] = useState([]);
    const [like, setLike] = useState(false);

    useEffect(() => {
        const seccion = document.getElementById('mapa');
        if (seccion) {
          seccion.scrollIntoView({ behavior: 'smooth' });
        }
      }, []);

    useEffect(() => {
        axios.get(`http://localhost:8080/producto/${id}`)
        .then(res => {
            setData(res.data);
            setCategoria(res.data.categoria);
            setCiudad(res.data.ciudad);
            setImages(res.data.imagenes);
            setNormas(res.data.normas.split(","));
            setSeguridad(res.data.seguridad.split(","));
        })
        axios.get(`http://localhost:8080/caracteristica/producto/${id}`)
        .then(res => {
            setServices(res.data);
        })
    }, [id])

    return (
        <div className='product-page'>
            <div className="product-header">
                <div className="left-product-header">
                    <p>{categoria?.titulo}</p>
                    <h3>{data?.titulo}</h3>
                </div>
                <FontAwesomeIcon icon={faChevronLeft} className="go-back-icon"
                onClick={() => navigate(-1)}
                />
            </div>
            <div className="top-location">
                <div className="left-top-location">
                    <FontAwesomeIcon icon={faLocationDot} className="location-icon"/>
                    <p>
                        {ciudad?.nombre}, {ciudad?.provincia}, {ciudad?.pais}
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
            <Calendario />
            <Map url={data?.url_ubicacion} titulo={`${ciudad?.nombre}, ${ciudad?.pais}`}/>
            <div className="info-extra-container" >
                <h3 id="mapa">Qué tenés que saber</h3>
                <div className="info-extra">
                    <section className="info">
                        <h4>Normas de la casa</h4>
                        <ul>
                            {normas?.map(element => (<li>{element}</li>))}
                        </ul>
                    </section>
                    <section className="info">
                        <h4>Salud y seguridad</h4>
                        <ul>
                            {seguridad?.map(element => (<li>{element}</li>))}
                        </ul>
                    </section>
                    <section className="info">
                        <h4>Política de cancelación</h4>
                        <ul>
                            <li>{data?.cancelacion}</li>
                        </ul>
                    </section>
                </div>
            </div>
        </div>
    )
}

export default Producto