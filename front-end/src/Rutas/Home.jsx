import React,{ useState, useEffect } from 'react'
import Buscador from '../Components/Buscador'
import Categorias from '../Components/Categorias'
import Recomendaciones from '../Components/Recomendaciones'
import axios from 'axios';


const Home = () => {

  // Estado para almacenar las categorías
  const [categories, setCategories] = useState([]);
  //Estado para almacenar la categoria clickeada
  const [categoriaSeleccionada, setCategoriaSeleccionada] = useState(null);

  const [ciudadSeleccionada, setCiudadSeleccionada] = useState(null);

  const handleCiudadSeleccionada = (ciudad) => {
    setCiudadSeleccionada(ciudad);
  };

  // Obtener las categorías desde la API cuando se monta el componente
  useEffect(() => {
    fetch("http://localhost:8080/categoria")
      .then((response) => response.json())
      .then((data) => setCategories(data));
  }, []);


  const actualizarCategoriaSeleccionada = (categoriaId) => setCategoriaSeleccionada(categoriaId);

  return (
    <div className="container">
      <Buscador onCiudadSeleccionada={handleCiudadSeleccionada} />
      <Categorias categories={categories} actualizarCategoriaSeleccionada={actualizarCategoriaSeleccionada}/>
      <Recomendaciones categoriaSeleccionada={categoriaSeleccionada} ciudadSeleccionada={ciudadSeleccionada}/>
    </div>
  )
}

export default Home