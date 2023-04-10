import React, { useState, useRef } from "react";
import Buscador from "../Components/Buscador";
import Categorias from "../Components/Categorias";
import Recomendaciones from "../Components/Recomendaciones";

const Home = () => {
  //Estado para almacenar la categoria clickeada
  const [categoriaSeleccionada, setCategoriaSeleccionada] = useState(null);
  //Estado para almacenar la ciudad clickeada
  const [ciudadSeleccionada, setCiudadSeleccionada] = useState(null);
  //Estado para almacenar las fechas seleccionadas
  const [dates, setDates] = useState(null);

  const [isLoading, setIsLoading] = useState(true);

  const recomendadosRef = useRef(null);

  const handleCiudadSeleccionada = (ciudad, selectedDates) => {
    setCiudadSeleccionada(ciudad);
    setDates(selectedDates);
  };

  const actualizarCategoriaSeleccionada = (categoriaId) =>
    setCategoriaSeleccionada(categoriaId);

  const handleIsLoading = (isLoading) => setIsLoading(isLoading);

  return (
    <div className="container">
      <Buscador
        onCiudadSeleccionada={handleCiudadSeleccionada}
        isLoading={isLoading}
        recomendadosRef={recomendadosRef}
      />
      <Categorias
        actualizarCategoriaSeleccionada={actualizarCategoriaSeleccionada}
        categoriaSeleccionada={categoriaSeleccionada}
      />
      <div ref={recomendadosRef}>
        <Recomendaciones
          categoriaSeleccionada={categoriaSeleccionada}
          ciudadSeleccionada={ciudadSeleccionada}
          dates={dates}
          isLoading={isLoading}
          onLoading={handleIsLoading}
          recomendadosRef={recomendadosRef}
        />
      </div>
    </div>
  );
};

export default Home;
