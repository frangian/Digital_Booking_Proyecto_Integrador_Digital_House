import React, { useState, useEffect } from 'react'

import Card from '../Components/CardRecomendaciones';

const Recomendaciones = ({ categoriaSeleccionada, ciudadSeleccionada }) => {

  const [productosRecomendados, setProductosRecomendados] = useState([]);

  

  //Si hay una categoria seleccionada traigo por categoria los recomendados
  useEffect(() => {
    if (categoriaSeleccionada) {
      fetch(`http://localhost:8080/producto/categoria/${categoriaSeleccionada}`)
        .then((response) => response.json())
        .then((data) => setProductosRecomendados(data));
    }
  }, [categoriaSeleccionada]);

  //Si no hay categoria seleccionada traigo los recomendados al azar del back
  useEffect(() => {
    fetch('http://localhost:8080/producto')
      .then((response) => response.json())
      .then((data) => setProductosRecomendados(data));
  }, []);

  //si hay na ciudad seleccionada trae los de esa ciudad
  useEffect(() => {
    async function fetchRecomendados() {
      const response = await fetch(`http://localhost:8080/producto/ciudad/${ciudadSeleccionada}`);
      const data = await response.json();
      console.log(ciudadSeleccionada);
      setProductosRecomendados(data);
    }
    if(ciudadSeleccionada){
      fetchRecomendados();
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
                imagen={product.img}
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