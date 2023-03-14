import React, { useState, useEffect } from 'react'
import axios from 'axios';
import Card from '../Components/CardRecomendaciones';

const Recomendaciones = ({ categoriaSeleccionada, ciudadSeleccionada }) => {

  const [productosRecomendados, setProductosRecomendados] = useState([]);

  //Falta agregar consulta con fecha
  useEffect(() => {
    let url = 'http://localhost:8080/producto/random';
  
    if (categoriaSeleccionada) {
      url = `http://localhost:8080/producto/categoria/${categoriaSeleccionada}`;
    } else if (ciudadSeleccionada) {
      url = `http://localhost:8080/producto/ciudad/${ciudadSeleccionada}`;
    }
  
    axios.get(url)
      .then((response) => {
        setProductosRecomendados(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, [categoriaSeleccionada, ciudadSeleccionada]);

  return (

    <div className="recomendaciones-container">
        <h2>Recomendaciones</h2>
        <div className='card-grid-recomendaciones'>
      {productosRecomendados.map(product => (
              <Card
                key={product.id}
                id={product.id}
                title={product.titulo}
                imagen={product.imagenes[0].url_imagen}
                category={product.categoria}
                location={product.descripcion_ubicacion}
                description={product.descripcion_producto}
                
              />
            ))}

        </div>
    </div>
  )
}

export default Recomendaciones