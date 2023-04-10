import axios from 'axios';
import React, { useContext, useState, useEffect } from 'react'
import ProductHeader from '../Components/ProductHeader'
import { ContextGlobal } from '../Components/Utils/globalContext';
import { API_URL } from '../Components/Utils/api';
import CardProducto from '../Components/CardProductoContainer';
import Skeleton from "react-loading-skeleton";
import "react-loading-skeleton/dist/skeleton.css";
import { useNavigate } from 'react-router-dom';

const ReservasUsuario = () => {

  const { state, dispatch } = useContext(ContextGlobal);
  const [isLoading, setIsLoading] = useState(true);
  const navigation = useNavigate();

  useEffect(() => {
    let jwt = localStorage.getItem("jwt");
    if (jwt) {
      setIsLoading(true)
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
          setIsLoading(false);
      })
    } else {
      navigation("/")
    }
  }, [])

  return (
    <div className='reservas-usuario-page' style={{ marginBottom: "0" }}>
      <ProductHeader tituloCategoria={`Cantidad de reservas: ${state.user?.reservas.length}`} tituloProducto={"Mis reservas"}/>
      <div className="recomendaciones-container" style={{ minHeight: "70vh" }}>
        <div className="card-grid-recomendaciones">
          {
            isLoading ? 
            (<>
              <Skeleton height={250} count={4} style={{ marginRight: "20px" }} />
              <Skeleton height={250} count={4} />
            </>) :
            (
              // <p onClick={() => {console.log(state.user.reservas)}}>Hola</p>
              state.user?.reservas?.map(reserva => (
                <CardProducto
                key={reserva.id}
                id={reserva.producto.id}
                title={reserva.producto.titulo}
                imagen={reserva.producto.imagenes[0].url_imagen}
                category={reserva.producto.categoria}
                location={reserva.producto.descripcion_ubicacion}
                description={reserva.producto.descripcion_producto}
                puntuacion={reserva.producto.puntuacion}
                reserva={true}
                checkIn={reserva.fechaInicial}
                checkOut={reserva.fechaFinal}
                llegada={reserva.horaComienzo}
                idReserva={reserva.id}
                codigoReserva={reserva.codigoReserva}
                />
              ))
            )
          }
        </div>
      </div>
    </div>
  )
}

export default ReservasUsuario