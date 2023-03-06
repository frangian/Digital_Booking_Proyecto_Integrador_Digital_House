import React, { useState, useEffect } from 'react'
import { useNavigate } from "react-router-dom";
import Card from '../Components/CardRecomendaciones';

const Recomendaciones = ({ categoriaSeleccionada, ciudadSeleccionada }) => {

  const [productosRecomendados, setProductosRecomendados] = useState([]);

  const navigate = useNavigate();

  //Si hay una categoria seleccionada traigo por categoria los recomendados
  useEffect(() => {
    if (categoriaSeleccionada) {
      fetch(`/api/recomendados?categoria=${categoriaSeleccionada}`)
        .then((response) => response.json())
        .then((data) => setProductosRecomendados(data));
    }
  }, [categoriaSeleccionada]);

  //Si no hay categoria seleccionada traigo los recomendados al azar del back
  useEffect(() => {
    fetch('/api/recomendados')
      .then((response) => response.json())
      .then((data) => setProductosRecomendados(data));
  }, []);

  //si hay na ciudad seleccionada trae los de esa ciudad
  useEffect(() => {
    async function fetchRecomendados() {
      const response = await fetch(`API_URL/recomendados?ciudad=${ciudadSeleccionada}`);
      const data = await response.json();
      setProductosRecomendados(data);
    }
    fetchRecomendados();
  }, [ciudadSeleccionada]);

  return (

    <div className="recomendaciones-container">
        <h2>Recomendaciones</h2>
        <div className='card-grid-recomendaciones'>
      {productosRecomendados.map(product => (
              <Card
                key={product.id}
                title={product.title}
                imagen={product.img}
                category={product.category}
                location={product.location}
                description={product.description}
                onClick={() => {navigate(`/product/${product.id}`)}}
              />
            ))}

        </div>
    </div>
  )
}

export default Recomendaciones