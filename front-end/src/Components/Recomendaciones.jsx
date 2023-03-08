import React, { useState, useEffect } from 'react'
import axios from 'axios';
import Card from '../Components/CardRecomendaciones';

const Recomendaciones = ({ categoriaSeleccionada, ciudadSeleccionada }) => {

  const [productosRecomendados, setProductosRecomendados] = useState([]);

  

  //Si hay una categoria seleccionada traigo por categoria los recomendados
  useEffect(() => {
    if (categoriaSeleccionada) {
      axios.get(`http://localhost:8080/producto/categoria/${categoriaSeleccionada}`)
        .then(response => setProductosRecomendados(response.data))
        .catch(error => console.log(error));
    }
  }, [categoriaSeleccionada]);

  //Si no hay categoria seleccionada traigo los recomendados al azar del back
  useEffect(() => {
    axios.get('http://localhost:8080/producto/random')
      .then((response) => {
        setProductosRecomendados(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  //si hay na ciudad seleccionada trae los de esa ciudad
  useEffect(() => {
    if (ciudadSeleccionada) {
      axios.get(`http://localhost:8080/producto/ciudad/${ciudadSeleccionada}`)
        .then((response) => {
          setProductosRecomendados(response.data);
          console.log(ciudadSeleccionada);
        })
        .catch((error) => {
          console.log(error);
        });
    }
  }, [ciudadSeleccionada]);

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
                category={product.categoria.titulo}
                location={product.descripcion_ubicacion}
                description={product.descripcion_producto}
                
              />
            ))}

        </div>
    </div>
  )
}

export default Recomendaciones