import React, { useState, useEffect } from "react";
import axios from "axios";
import Card from "../Components/CardRecomendaciones";

const Recomendaciones = ({
  categoriaSeleccionada,
  ciudadSeleccionada,
  dates,
}) => {
  const [productosRecomendados, setProductosRecomendados] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [url, setUrl] = useState("");

  useEffect(() => {
    let url = "http://localhost:8080/producto/random";
    let params = {};

    if (categoriaSeleccionada) {
      url = `http://localhost:8080/producto/categoria/${categoriaSeleccionada}`;
    }
    if (ciudadSeleccionada && dates && dates.length === 2) {
      const checkIn = dates[0];
      const checkOut = dates[1];
      console.log("busco por ciudad y fechas");
      url = `http://localhost:8080/producto/disponibles/fechaciudad`;
      params = {
        ciudadId: ciudadSeleccionada,
        fechaInicial: checkIn,
        fechaFinal: checkOut,
      };
    } else if (ciudadSeleccionada) {
      url = `http://localhost:8080/producto/ciudad/${ciudadSeleccionada}`;
    } else if (dates && dates.length === 2) {
      const checkIn = dates[0];
      const checkOut = dates[1];
      console.log("busco por fecha");
      url = `http://localhost:8080/producto/disponibles/fecha`;
      params = {
        fechaInicial: checkIn,
        fechaFinal: checkOut,
      };
    }

    const urlParams = new URLSearchParams(params);
    const finalUrl = url + "?" + urlParams.toString();

    setUrl(finalUrl);
    setIsLoading(true);

    axios
      .get(finalUrl)
      .then((response) => {
        setProductosRecomendados(response.data);
        setIsLoading(false);
      })
      .catch((error) => {
        console.log(error);
      });
  }, [categoriaSeleccionada, ciudadSeleccionada, dates]);

  return (
    <div className="recomendaciones-container">
      {isLoading ? (
        <p>BUSCANDO ALOJAMIENTOS...</p>
      ) : (
        <>
          {url && <code>{url}</code>}
          <h2>Recomendaciones</h2>
          <div className="card-grid-recomendaciones">
            {productosRecomendados.map((product) => (
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
            ))}
          </div>
        </>
      )}
    </div>
  );
};

export default Recomendaciones;
