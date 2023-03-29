import React, { useState, useEffect, useContext } from 'react'
import Swal from 'sweetalert2'
import withReactContent from 'sweetalert2-react-content'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faLocationDot, faStar } from '@fortawesome/free-solid-svg-icons'
import { faFacebook, faWhatsapp, faTwitter } from '@fortawesome/free-brands-svg-icons'
import ShareOutlinedIcon from '@mui/icons-material/ShareOutlined';
import FavoriteBorderOutlinedIcon from '@mui/icons-material/FavoriteBorderOutlined';
import FavoriteIcon from '@mui/icons-material/Favorite';
import { useParams } from 'react-router-dom'
import { IconButton } from '@mui/material';
import Servicios from '../Components/Servicios';
import Calendario from '../Components/CalendarComponent/CalendarContainer';
import Map from '../Components/Map';
import ImgContainer from '../Components/ImgComponent/ImgContainer';
import axios from 'axios'
import { ContextGlobal } from '../Components/Utils/globalContext';
import ProductHeader from '../Components/ProductHeader';
import PoliticasProducto from '../Components/PoliticasProducto';
import Skeleton from 'react-loading-skeleton'
import 'react-loading-skeleton/dist/skeleton.css'
import { FacebookShareButton, WhatsappShareButton, TwitterShareButton } from 'react-share'
import { API_URL } from '../Components/Utils/api'

const Producto = () => {

    const { id } = useParams();
    const { state, dispatch } = useContext(ContextGlobal);
    const [data, setData] = useState({});
    const [images, setImages] = useState([]);
    const [normas, setNormas] = useState([]);
    const [seguridad, setSeguridad] = useState([]);
    const [services, setServices] = useState([]);
    const [reservas, setReservas] = useState([]);
    const [like, setLike] = useState(false);
    const [isLoading, setIsLoading] = useState(false);
    const [shareObj, setShareObj] = useState({title: "", text: "", url: ""});
    const MySwal = withReactContent(Swal);

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
        setIsLoading(false);
        axios.get(`${API_URL}/producto/${id}`)
        .then(res => {
            setData(res.data);
            setImages(res.data.imagenes);
            setNormas(res.data.normas.split(","));
            setSeguridad(res.data.seguridad.split(","));
            setIsLoading(true);
            setShareObj({
                ...shareObj,
                title: `Digital Booking ${res.data.titulo}`,
                text: `Reservá ${res.data.titulo} en Digital Booking`,
                url: window.location.href
            })
        })
        axios.get(`${API_URL}/caracteristica/producto/${id}`)
        .then(res => {
            setServices(res.data);
        })
        axios.get(`${API_URL}/reserva/producto/${id}`)
        .then(res => {
            setReservas(res.data)
        }) 
    }, [id])

    const shareCard = () => {
        MySwal.fire({
            title: <strong>Comparte este producto!</strong>,
            html: <div className='share-card'>
                <FacebookShareButton url={shareObj.url} quote={shareObj.text}>
                    <FontAwesomeIcon icon={faFacebook} style={{ color: "#1877F2" }}/>
                </FacebookShareButton>
                <WhatsappShareButton url={shareObj.url} title={shareObj.text}>
                    <FontAwesomeIcon icon={faWhatsapp} style={{ color: "#25D366" }}/>
                </WhatsappShareButton>
                <TwitterShareButton url={shareObj.url} title={shareObj.text}>
                    <FontAwesomeIcon icon={faTwitter} style={{ color: "#1DA1F2" }}/>
                </TwitterShareButton>
            </div>,
 
        })
    }

    const shareAcross = (obj) => {
        if(window.innerWidth < 900) {
            if (navigator.share) {
                navigator.share(obj)
                .then(() => console.log("Objeto compartido"))
                .catch(err => console.log(err))
            } else {
                console.log("no soportado");
            }
        } else {
            shareCard()
        }
        
    }

    return (
        <div className='product-page'>
            <ProductHeader tituloCategoria={data?.categoria} tituloProducto={data?.titulo}/>
            {
            data.ciudad ? (<div className="top-location">
                    <div className="left-top-location">
                        <FontAwesomeIcon icon={faLocationDot} className="location-icon"/>
                        <p>
                            {data.ciudad ? `${data.ciudad?.nombre}, ${data.ciudad?.provincia}, ${data.ciudad?.pais}` : ""}
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
                </div>) :
                <Skeleton className='top-location' height={"8vh"}/>
            }
            <div className='interacciones'>
                <IconButton onClick={() => shareAcross(shareObj)}>
                    <ShareOutlinedIcon />
                </IconButton>
                <IconButton onClick={() => {setLike(!like)}}>
                    {like ? <FavoriteIcon sx={{color: "red"}}/> : <FavoriteBorderOutlinedIcon />}
                </IconButton>
            </div>
            <ImgContainer imgList={images} isLoading={isLoading} shareObj={shareObj} shareAcross={shareAcross}/>
            <div className="descripcion">
                <h3>Alójate en el corazón de {data.ciudad?.nombre}</h3>
                <p>{data?.descripcion_producto || <Skeleton width={"150px"}/>}</p>
            </div>
            <Servicios servicios={services}/>
            <Calendario productId={id} reservas={reservas}/>
            <Map url={data?.url_ubicacion} titulo={`${data.ciudad?.nombre}, ${data.ciudad?.pais}`}/>
            <PoliticasProducto 
            cancelacion={data?.cancelacion}
            normas={normas}
            seguridad={seguridad}
            isLoading={isLoading}
            />
        </div>
    )
}

export default Producto