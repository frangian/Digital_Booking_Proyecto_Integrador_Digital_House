import React,{ useState } from 'react'
import Buscador from '../Components/Buscador'
import Categorias from '../Components/Categorias'
import Recomendaciones from '../Components/Recomendaciones'



const Home = () => {

 
  //Estado para almacenar la categoria clickeada
  const [categoriaSeleccionada, setCategoriaSeleccionada] = useState(null);
  //Estado para almacenar la ciudad clickeada
  const [ciudadSeleccionada, setCiudadSeleccionada] = useState(null);
  //Estado para almacenar las fechas seleccionadas
  const [dates, setDates] = useState(null);

  
  const handleCiudadSeleccionada = (ciudad, selectedDates) => {
    setCiudadSeleccionada(ciudad);
    setDates(selectedDates);
  };

  const actualizarCategoriaSeleccionada = (categoriaId) => setCategoriaSeleccionada(categoriaId);

  return (
    <div className="container">
      <Buscador onCiudadSeleccionada={handleCiudadSeleccionada} />
      <Categorias  actualizarCategoriaSeleccionada={actualizarCategoriaSeleccionada}/>
      <Recomendaciones categoriaSeleccionada={categoriaSeleccionada} ciudadSeleccionada={ciudadSeleccionada} dates={dates}/>
    </div>
  )
}

export default Home