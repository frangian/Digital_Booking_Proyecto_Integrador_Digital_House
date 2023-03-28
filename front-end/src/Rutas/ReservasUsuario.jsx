import axios from 'axios';
import React, { useContext, useState, useEffect } from 'react'
import ProductHeader from '../Components/ProductHeader'
import { ContextGlobal } from '../Components/Utils/globalContext';
import { API_URL } from '../Components/Utils/api';
import CardRecomendaciones from '../Components/CardRecomendaciones';
import Skeleton from "react-loading-skeleton";
import "react-loading-skeleton/dist/skeleton.css";
import { useNavigate } from 'react-router-dom';

const ReservasUsuario = () => {

  const { state, dispatch } = useContext(ContextGlobal);
  const [isLoading, setIsLoading] = useState(true);
  const navigation = useNavigate();

  useEffect(() => {
    let jwt = localStorage.getItem("jwt");
    setIsLoading(true)
    if (jwt) {
      let partes = jwt.split('.');
      let contenido = atob(partes[1]);
      let datos = JSON.parse(contenido);
      const headers = { 'Authorization': `Bearer ${jwt}` };
      axios.get(`${API_URL}/usuario/email/${datos.sub}`, { headers })
      .then(res => {
          dispatch({
            type: "register",
            payload: {
              ...state,
              user: res.data,
              logged: true
            }
          })
      })
      .catch(setIsLoading(false))    
      setIsLoading(false);
    } else {
      navigation("/")
    }
  }, [])


  return (
    <div className='product-page' style={{ marginBottom: "0" }}>
      <ProductHeader tituloCategoria={`${state.user.nombre} ${state.user.apellido}`} tituloProducto={"Mis reservas"}/>
      <div className="recomendaciones-container" style={{ minHeight: "70vh" }}>
        <div className="card-grid-recomendaciones">
          {
            isLoading ? 
            (<>
              <Skeleton height={250} count={4} style={{ marginRight: "20px" }} />
              <Skeleton height={250} count={4} />
            </>) :
            (
              <p onClick={() => {console.log(state.user.reservas)}}>Hola</p>
              // state.user?.reservas?.map(reserva => (
              //   <CardRecomendaciones
              //   key={reserva.id}
              //   id={reserva.producto.id}
              //   title={reserva.producto.titulo}
              //   imagen={reserva.producto.imagenes[0].url_imagen}
              //   category={reserva.producto.categoria}
              //   location={reserva.producto.descripcion_ubicacion}
              //   description={reserva.producto.descripcion_producto}
              //   puntuacion={reserva.producto.puntuacion}
              //   reserva={true}
              //   checkIn={reserva.fechaInicial}
              //   checkOut={reserva.fechaFinal}
              //   />
              // ))
            )
          }
        </div>
      </div>
    </div>
  )
}

export default ReservasUsuario