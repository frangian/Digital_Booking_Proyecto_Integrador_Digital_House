import React, { useState, useEffect } from "react";
import Card from "../Components/CardCategoria";
import axios from 'axios';
import Skeleton from "react-loading-skeleton";
import "react-loading-skeleton/dist/skeleton.css";

const Categorias = ({  actualizarCategoriaSeleccionada }) => {
  
  const [isLoading, setIsLoading] = useState(true);
   // Estado para almacenar las categorías
   const [categories, setCategories] = useState([]);

  //manejo el click de la categoria y llamo a la funcion que setea la categoria seleccionada
  const handleClick = (id) => {
    actualizarCategoriaSeleccionada(id);
  };

   // Obtener las categorías desde la API cuando se monta el componente
   useEffect(() => {
    axios.get('http://localhost:8080/categoria')
      .then((response) => {
        setCategories(response.data);
        setIsLoading(false);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  return (
    <div className="categorias-container">
      <h2>Buscar por tipo de alojamiento</h2>
      <div className="card-grid">
        {isLoading ? (
          <>
          <Skeleton height={350} count={1} style={{ marginRight: "20px" }} />
          <Skeleton height={350} count={1} style={{ marginRight: "20px" }} />
          <Skeleton height={350} count={1} style={{ marginRight: "20px" }} />
          <Skeleton height={350} count={1} />
        </>
        ) : (
          categories.map((categoria) => (
            <Card
              key={categoria.id}
              nombre={categoria.titulo}
              imagen={categoria.imagenes[0].url_imagen}
              cantidad={categoria.productos.length}
              handleClick={() => handleClick(categoria.id)}
            />
          ))
        )}
      </div>
    </div>
  );
};

export default Categorias;
