import * as React from "react";
import { useState, useEffect, useContext } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faStar } from "@fortawesome/free-solid-svg-icons";
import { faHeart } from "@fortawesome/free-solid-svg-icons";
import { faMapMarkerAlt } from "@fortawesome/free-solid-svg-icons";
import { useNavigate } from "react-router-dom";
import { elegirServicio } from "./Utils/utils";
import { ContextGlobal } from "./Utils/globalContext";
import axios from "axios";
import { setFavInStorage, removeFavInStorage } from "./Utils/localStorage";
import Modal from "./Modal";
import { API_URL } from './Utils/api'
import Swal from 'sweetalert2'
import CardProducto from "./CardProducto";

const CardRecomendaciones = ({
  id,
  title,
  imagen,
  category,
  location,
  description,
  puntuacion,
  checkIn,
  checkOut,
  reserva,
  llegada,
  idReserva,
  codigoReserva
}) => {
  //const navigate = useNavigate();
  const MAX_LENGTH = 100;
  const { state, dispatch, removeReserva } = useContext(ContextGlobal);

  //const [showModal, setShowModal] = useState(false);
  const [favorite, setFavorite] = useState(() => {
    const storedValue = localStorage.getItem(`favorite-${id}`);
    return storedValue ? JSON.parse(storedValue) : false;
  });
  const [favoriteId, setFavoriteId] = useState(0);
  const [characteristics, setCharacteristics] = useState([]);

  const shortDescription =
    description?.length > MAX_LENGTH
      ? description.substring(0, MAX_LENGTH) + "..."
      : description;

  // const handleToggleDescription = () => {
  //   setShowModal(!showModal);
  // };


  useEffect(() => {
    axios
      .get(`${API_URL}/caracteristica/producto/${id}`)
      .then((response) => {
        setCharacteristics(response.data);
      })
      .catch((error) => {
        console.log(error);
        console.log(id);
      });
  }, [id]);

  const cancelarReserva = () => {
    Swal.fire({
      title: "Cancelar reserva",
      text: `Para cancelar su reserva en ${title} ingrese el codigo de reserva que le fue enviado a su correo cuando la realizó.`,
      inputAttributes: {
        autocapitalize: 'off'
      },
      input: 'text',
      showCancelButton: true,
      confirmButtonText: 'Cancelar reserva',
      showLoaderOnConfirm: true,
      confirmButtonColor: "#1dbeb4",
      cancelButtonText: "Cancelar acción",
      cancelButtonColor: "#ff0000",
      footer: "<b style='text-align: center;'>Asegurese de haber leído atentamente las políticas de cancelación</b>",
      preConfirm: (codigo) => {
        let jwt = localStorage.getItem("jwt");
        if (codigo === codigoReserva) {
          const config = {
            method: 'DELETE',
            headers: {
              'Authorization': `Bearer ${jwt}`,
              'Content-Type': 'application/json'
            }
          }
          return fetch(`${API_URL}/reserva/${idReserva}`, config)
          .then(response => {
            if (!response.ok) {
              throw new Error(response.statusText)
            }
            return response.text()
          })
          .catch(error => {
            Swal.showValidationMessage(`El procedimiento fallo. Intentelo de nuevo más tarde.`)
          })
        } else {
          Swal.showValidationMessage(`El codigo de reserva es incorrecto.`)
        }
      },
      allowOutsideClick: () => !Swal.isLoading()
    }).then((result) => {
      if (result.isConfirmed) {
        removeReserva(idReserva);
        Swal.fire({
          icon: 'success',
          title: 'Su reserva fue cancelada con exito.',
          confirmButtonColor: "#1dbeb4",
        })
      }
    })
  }

  const encontrarFav = (array) => {
    array.forEach(fav => {
      if (fav.producto.id === id) {
        setFavorite(true);
        setFavoriteId(fav.id)
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
        setFavorite(true);
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
        id,
        title,
        imagen,
        category,
        location,
        description,
        puntuacion,
      });
      localStorage.setItem(`favorite-${id}`, JSON.stringify(true));
      setFavorite(true);
    }
  };

  const removeFav = () => {
    let jwt = localStorage.getItem("jwt");
    if(jwt) {
      const headers = { 'Authorization': `Bearer ${jwt}` };
      axios.delete(`${API_URL}/favorito/${favoriteId}`, { headers })
      .then(res => {
        setFavorite(false);
        dispatch({
          type: "REMOVE_FAVORITE",
          payload: favoriteId
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
      setFavorite(false);
    }
  };

  

  useEffect(() => {
    let jwt = localStorage.getItem("jwt");
    if (jwt) { 
      encontrarFav(state.user.favoritos)
    }
  }, [encontrarFav])

  return (

    
      <CardProducto
      id={id}
      imagen={imagen}
      title={title}
      favorite={favorite}
      removeFav={removeFav}
      addFav={addFav}
      category={category}
      puntuacion={puntuacion}
      location={location}
      reserva={reserva}
      checkIn={checkIn}
      checkOut={checkOut}
      llegada={llegada}
      shortDescription={shortDescription}
      description={description}
      characteristics={characteristics}
      cancelarReserva={cancelarReserva}
      />
    // <div className="card-recomendaciones">
    //   <div className="image-container">
    //     <img src={imagen} alt={title} />
    //     <FontAwesomeIcon
    //       icon={faHeart}
    //       className={
    //         favorite
    //           ? "fa-heart heart-active grow-heart"
    //           : "fa-heart grow-heart"
    //       }
    //       onClick={favorite ? removeFav : addFav}
    //     />
    //   </div>
    //   <div className="info-container">
    //     <div className="info-container-header">
    //       <div>
    //         <div className="info-categoria-container">
    //           <h3>{category}</h3>
    //           <FontAwesomeIcon icon={faStar} className="icono icono-verde" />
    //           <FontAwesomeIcon icon={faStar} className="icono icono-verde" />
    //           <FontAwesomeIcon icon={faStar} className="icono icono-verde" />
    //           <FontAwesomeIcon icon={faStar} className="icono icono-verde" />
    //         </div>
    //         <h2>{title}</h2>
    //       </div>
    //       <div className="puntuacion-container">
    //         <div className="square8">
    //           <span id="square8-number">{puntuacion}</span>
    //         </div>
    //         <p>Muy bueno</p>
    //       </div>
    //     </div>
    //     <div className="extras-container">
    //       <p>
    //         <FontAwesomeIcon
    //           icon={faMapMarkerAlt}
    //           className="icono ubicacion"
    //         />
    //         {location}{" "}
    //         <a
    //           href={`http://group8-bucket-front.s3-website.us-east-2.amazonaws.com/product/${id}#mapa`}
    //           className="enlace-mapa"
    //         >
    //           MOSTRAR EN EL MAPA
    //         </a>
    //       </p>

    //       {
    //         !reserva ? characteristics.map((caracteristica) => (
    //           <span key={caracteristica.id} className="icono-servicio">
    //             {elegirServicio(caracteristica.titulo, "#383b58")}
    //           </span>
    //         )) :
    //         (<div style={{ marginTop: "20px" }}>
    //           <p>Check in: {checkIn + " " + llegada}</p>
    //           <p>Check out: {checkOut}</p>
    //         </div>)
    //       }
    //     </div>
    //     <p>
    //       {shortDescription}
    //       {description?.length > MAX_LENGTH && (
    //         <button
    //           onClick={handleToggleDescription}
    //           className="btn-vermas-vermenos"
    //         >
    //           más...
    //         </button>
    //       )}
    //     </p>
    //     <button
    //       className="ver-mas-btn"
    //       onClick={() => {
    //         navigate(`/product/${id}`);
    //         dispatch({
    //           type: "register",
    //           payload: {
    //             ...state,
    //             map: false,
    //           },
    //         });
    //       }}
    //     >
    //       ver más
    //     </button>
    //   </div>
    //   {showModal && (
    //     <Modal onClose={handleToggleDescription}>
    //       <h2>{title}</h2>
    //       <p>{description}</p>
    //     </Modal>
    //   )}
    // </div>
  );
};

export default CardRecomendaciones;
