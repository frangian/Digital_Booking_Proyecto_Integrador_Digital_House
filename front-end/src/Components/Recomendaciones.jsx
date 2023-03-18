import React, { useState, useEffect } from "react";
import axios from "axios";
import Card from "../Components/CardRecomendaciones";
import { getProductosRecomendados } from "./Utils/api";
import Skeleton from "react-loading-skeleton";
import "react-loading-skeleton/dist/skeleton.css";

const Recomendaciones = ({
  categoriaSeleccionada,
  ciudadSeleccionada,
  dates,
}) => {
  const [productosRecomendados, setProductosRecomendados] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      setIsLoading(true);
      try {
        const data = await getProductosRecomendados(categoriaSeleccionada, ciudadSeleccionada, dates);
        setProductosRecomendados(data);
        setIsLoading(false);
      } catch (error) {
        console.log(error);
        setIsLoading(false);
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
            />
          ))
        )}
      </div>
    </div>
  );
};

export default Recomendaciones;
