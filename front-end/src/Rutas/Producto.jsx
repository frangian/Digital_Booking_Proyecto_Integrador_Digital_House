import React, { useState, useEffect } from 'react'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faLocationDot, faChevronLeft, faStar } from '@fortawesome/free-solid-svg-icons'
import ShareOutlinedIcon from '@mui/icons-material/ShareOutlined';
import FavoriteBorderOutlinedIcon from '@mui/icons-material/FavoriteBorderOutlined';
import FavoriteIcon from '@mui/icons-material/Favorite';
import { useNavigate } from 'react-router-dom'
import { IconButton } from '@mui/material';
import Servicios from '../Components/Servicios';
import Calendario from '../Components/Calendario';
import Map from '../Components/Map';
import ImgContainer from '../Components/ImgComponent/ImgContainer';

const Producto = () => {

    const navigate = useNavigate();
    const imagenes = [
        "https://images.unsplash.com/photo-1517840901100-8179e982acb7?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
        "https://images.unsplash.com/photo-1496417263034-38ec4f0b665a?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8M3x8aG90ZWx8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60",
        "https://images.unsplash.com/photo-1455587734955-081b22074882?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NXx8aG90ZWx8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60",
        "https://images.unsplash.com/photo-1566073771259-6a8506099945?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Nnx8aG90ZWx8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60",
        "https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8aG90ZWx8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60",
        "https://images.unsplash.com/photo-1618773928121-c32242e63f39?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8OHx8aG90ZWx8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60",
        "https://images.unsplash.com/photo-1564501049412-61c2a3083791?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTF8fGhvdGVsfGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60",
        "https://images.unsplash.com/photo-1582719508461-905c673771fd?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTB8fGhvdGVsfGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60",
        "https://images.unsplash.com/photo-1611892440504-42a792e24d32?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTZ8fGhvdGVsfGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60",
        "https://images.unsplash.com/photo-1444201983204-c43cbd584d93?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTh8fGhvdGVsfGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60",
    ]
    const servicios = [
        {id: 1, title: "Cocina"},
        {id: 2, title: "Televisor"},
        {id: 3, title: "Aire acondicionado"},
        {id: 4, title: "Apto mascotas"},
        {id: 5, title: "Estacionamiento gratuito"},
        {id: 6, title: "Pileta"},
        {id: 7, title: "Wifi"},
    ]
    const [like, setLike] = useState(false);

    useEffect(() => {
        const seccion = document.getElementById('mapa');
        if (seccion) {
          seccion.scrollIntoView({ behavior: 'smooth' });
        }
      }, []);

    return (
        <div className='product-page'>
            <div className="product-header">
                <div className="left-product-header">
                    <p>HOTEL</p>
                    <h3>Hermitage Hotel</h3>
                </div>
                <FontAwesomeIcon icon={faChevronLeft} className="go-back-icon"
                onClick={() => navigate(-1)}
                />
            </div>
            <div className="top-location">
                <div className="left-top-location">
                    <FontAwesomeIcon icon={faLocationDot} className="location-icon"/>
                    <p>
                        Buenos Aires, Ciudad Autónoma de Buenos Aires, Argentina 
                        <br /> 
                        A 940 m del centro
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
                    <div className="number"><p>8</p></div>
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
            <ImgContainer imgList={imagenes}/>
            <div className="descripcion">
                <h3>Alójate en el corazón de Buenos Aires</h3>
                <p>Está situado a solo unas calles de la avenida Alvear, de la avenida Quintana, del parque San Martín y del distrito de Recoleta. En las inmediaciones también hay varios lugares de interés, como la calle Florida, el centro comercial Galerías Pacífico, la zona de Puerto Madero, la plaza de Mayo y el palacio Municipal.</p>
                <p>Nuestros clientes dicen que esta parte de Buenos Aires es su favorita, según los comentarios independientes.</p>
                <p>
                    El Hotel es un hotel sofisticado de 4 estrellas que goza de una ubicación tranquila, a poca distancia de prestigiosas galerías de arte, teatros, museos y zonas comerciales. Además, hay WiFi gratuita.
                    El establecimiento sirve un desayuno variado de 07:00 a 10:30.
                </p>
            </div>
            <Servicios servicios={servicios}/>
            <Calendario />
            <Map />
            <div className="info-extra-container" >
                <h3 id="mapa">Qué tenés que saber</h3>
                <div className="info-extra">
                    <section className="info">
                        <h4>Normas de la casa</h4>
                        <ul>
                            <li>Check-out: 10:00</li>
                            <li>No se permiten fiestas</li>
                            <li>No fumar</li>
                        </ul>
                    </section>
                    <section className="info">
                        <h4>Salud y seguridad</h4>
                        <ul>
                            <li>Se aplican las pautas de distanciamiento social y otras normas relacionadas con el coronavirus</li>
                            <li>Detector de humo</li>
                            <li>Depósito de seguridad</li>
                        </ul>
                    </section>
                    <section className="info">
                        <h4>Política de cancelación</h4>
                        <ul>
                            <li>Agregá las fechas de tu viaje para obtener los detalles de cancelación de esta estadía.</li>
                        </ul>
                    </section>
                </div>
            </div>
        </div>
    )
}

export default Producto