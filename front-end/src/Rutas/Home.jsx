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

  const [dates, setDates] = useState(null);


  const handleCiudadSeleccionada = (ciudad, selectedDates) => {
    setCiudadSeleccionada(ciudad);
    setDates(selectedDates);
  };

  // Obtener las categorías desde la API cuando se monta el componente
  useEffect(() => {
    axios.get('http://localhost:8080/categoria')
      .then((response) => {
        setCategories(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);


  const actualizarCategoriaSeleccionada = (categoriaId) => setCategoriaSeleccionada(categoriaId);

  return (
    <div className="container">
      <Buscador onCiudadSeleccionada={handleCiudadSeleccionada} />
      <Categorias categories={categories} actualizarCategoriaSeleccionada={actualizarCategoriaSeleccionada}/>
      <Recomendaciones categoriaSeleccionada={categoriaSeleccionada} ciudadSeleccionada={ciudadSeleccionada} dates={dates}/>
    </div>
  )
}

export default Home