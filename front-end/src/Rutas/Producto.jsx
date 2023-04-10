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
import { setFavInStorage, removeFavInStorage } from "../Components/Utils/localStorage";
import Estrellas from '../Components/Estrellas'

const Producto = () => {

    const { id } = useParams();
    const { state, dispatch } = useContext(ContextGlobal);
    const [data, setData] = useState({});
    const [images, setImages] = useState([]);
    const [normas, setNormas] = useState([]);
    const [seguridad, setSeguridad] = useState([]);
    const [services, setServices] = useState([]);
    const [reservas, setReservas] = useState([]);
    const [like, setLike] = useState(() => {
        const storedValue = localStorage.getItem(`favorite-${id}`);
        return storedValue ? JSON.parse(storedValue) : false;
    });
    const [likeId, setLikeId] = useState(0);
    const [isLoading, setIsLoading] = useState(false);
    const [shareObj, setShareObj] = useState({title: "", text: "", url: ""});
    const MySwal = withReactContent(Swal);

    const encontrarFav = (array) => {
        array.forEach(fav => {
            if (fav.producto.id === Number(id)) {
                setLike(true);
                setLikeId(fav.id);  
            } 
        })
      }

    const addFav = () => {
        let jwt = localStorage.getItem("jwt");
        if(jwt) {
            const headers = { 'Authorization': `Bearer ${jwt}` };
            let objPostFav = {
                producto: {
                id: id
                },
                usuario: {
                    id: state.user.id,
                    validado: true,
                    usuarioRole: "USER"
                }
            }
            axios.post(`${API_URL}/favorito`, objPostFav, { headers })
            .then(res => {
                setLike(true)
                dispatch({
                    type: "ADD_FAVORITE",
                    payload: res.data
                })
            })
            .catch(err => {
                Swal.fire({
                icon: 'error',
                title: 'Oops',
                text: 'El prdocuto no pudo ser agregado a favoritos intentalo de nuevo más tarde!',
            })
        })
        } else {
          setFavInStorage({
            id: id,
            title: data?.titulo,
            imagen: images[0].url_imagen,
            category: data?.categoria,
            location: data?.descripcion_ubicacion,
            description: data?.descripcion_producto,
            puntuacion: data?.puntuacion,
          });
          localStorage.setItem(`favorite-${id}`, JSON.stringify(true));
          setLike(true);
        }
      };
    
    const removeFav = () => {
        let jwt = localStorage.getItem("jwt");
        if(jwt) {
            const headers = { 'Authorization': `Bearer ${jwt}` };
            axios.delete(`${API_URL}/favorito/${likeId}`, { headers })
            .then(res => {
                setLike(false)
                dispatch({
                    type: "REMOVE_FAVORITE",
                    payload: likeId
                })
            })
            .catch(err => {
                Swal.fire({
                icon: 'error',
                title: 'Oops',
                text: 'El prdocuto no pudo ser eliminado de favoritos intentalo de nuevo más tarde!',
                })
            })    
        } else {
            removeFavInStorage(id);
            localStorage.setItem(`favorite-${id}`, JSON.stringify(false));
            setLike(false);
        }
    };

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

    useEffect(() => {
        let jwt = localStorage.getItem("jwt");
        if (jwt) {
            encontrarFav(state.user.favoritos)
        } 
    }, [state.user])

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
                .then()
                .catch(err => console.log(err))
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
                            <Estrellas 
                                stars={data?.puntuacion/2}
                                colorStar={"#1dbeb4"}
                                sizeStar={15}
                                />
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
                <IconButton onClick={like ? removeFav : addFav}>
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