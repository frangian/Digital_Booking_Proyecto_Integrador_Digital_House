import React, { useState, useEffect } from "react";
import axios from "axios";
import Card from "./CardProductoContainer";
import { getProductosRecomendados } from "./Utils/api";
import Skeleton from "react-loading-skeleton";
import "react-loading-skeleton/dist/skeleton.css";

const Recomendaciones = ({
  categoriaSeleccionada,
  ciudadSeleccionada,
  dates,
  isLoading,
  onLoading,
  recomendadosRef
}) => {
  const [productosRecomendados, setProductosRecomendados] = useState([]);
  

  useEffect(() => {
    const fetchData = async () => {
      onLoading(true);
      try {
        const data = await getProductosRecomendados(categoriaSeleccionada, ciudadSeleccionada, dates);
        setProductosRecomendados(data);
        if(categoriaSeleccionada || ciudadSeleccionada || dates) {
          recomendadosRef.current.scrollIntoView({ behavior: 'smooth' })
        }
        onLoading(false)
      } catch (error) {
        console.log(error);
        onLoading(false);
      }
    };
    fetchData();
  }, [categoriaSeleccionada, ciudadSeleccionada, dates]);

  return (
    <div className="recomendaciones-container">
      <h2>Recomendaciones</h2>
      <div className="card-grid-recomendaciones">
        {isLoading ? (
          <>
            <Skeleton height={250} count={4} style={{ marginRight: "20px" }} />
            <Skeleton height={250} count={4} />
          </>
        ) : (
          productosRecomendados.map((product) => (
            <Card
              key={product.id}
              id={product.id}
              title={product.titulo}
              imagen={product.imagenes[0].url_imagen}
              category={product.categoria}
              location={product.descripcion_ubicacion}
              description={product.descripcion_producto}
              puntuacion={product.puntuacion}
              reserva={false}
            />
          ))
        )}
      </div>
    </div>
  );
};

export default Recomendaciones;
