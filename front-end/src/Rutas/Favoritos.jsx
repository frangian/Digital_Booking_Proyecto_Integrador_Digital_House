import axios from 'axios'
import React, { useEffect, useState, useContext } from 'react'
import { useNavigate } from 'react-router-dom'
import CardProducto from '../Components/CardProductoContainer'
import ProductHeader from '../Components/ProductHeader'
import { API_URL } from '../Components/Utils/api'
import { ContextGlobal } from '../Components/Utils/globalContext'
import Skeleton from "react-loading-skeleton";
import "react-loading-skeleton/dist/skeleton.css";

const Favoritos = () => {

  const { state, dispatch } = useContext(ContextGlobal);
  const [isLoading, setIsLoading] = useState(true);
  const [favs, setFavs] = useState([]);


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
    } else if (!jwt) {
      setIsLoading(false);
      setFavs(JSON.parse(localStorage.getItem("favs")));
    }
  }, [])

  return (
    <div className='reservas-usuario-page' style={{ marginBottom: "0" }}>
      <ProductHeader tituloCategoria={`Cantidad de favoritos: ${localStorage.getItem("jwt") ? state.user?.favoritos.length : (favs?.length !== undefined ? favs.length : "0")}`} tituloProducto={"Favoritos"}/>
      <div className="recomendaciones-container" style={{ minHeight: "70vh" }}>
        <div className="card-grid-recomendaciones">
          {
            isLoading ? (
            <>
              <Skeleton height={250} count={4} style={{ marginRight: "20px" }} />
              <Skeleton height={250} count={4} />
            </>
            ) : (
              localStorage.getItem("jwt") ? 
              (
                state.user?.favoritos.map(fav => (
                  <CardProducto
                  key={fav.producto.id}
                  id={fav.producto.id}
                  category={fav.producto.categoria}
                  description={fav.producto.descripcion_producto}
                  imagen={fav.producto.imagenes[0].url_imagen}
                  puntuacion={fav.producto.puntuacion}
                  title={fav.producto.titulo}
                  location={fav.producto.descripcion_ubicacion}
                  reserva={false}
                  />
                ))
              ) : (
                favs?.map(fav => (
                  <CardProducto
                  key={fav.id}
                  id={fav.id}
                  category={fav.category}
                  description={fav.description}
                  imagen={fav.imagen}
                  puntuacion={fav.puntuacion}
                  location={fav.location}
                  title={fav.title}
                  reserva={false}
                  />
                ))
              )
            )
          }
        </div>
      </div>
    </div>
  )
}

export default Favoritos