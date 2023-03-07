import React from 'react'
import Card from '../Components/CardCategoria';
import data from '../Components/Utils/categorias.json'
import axios from 'axios';

const Categorias = ({ categories, actualizarCategoriaSeleccionada }) => {
  
  //manejo el click de la categoria y llamo a la funcion que setea la categoria seleccionada
  const handleClick = () => {
    actualizarCategoriaSeleccionada(categories.id);
  };

  return (
    <div className="categorias-container">
        <h2>Buscar por tipo de alojamiento</h2>
        <div className='card-grid'>
      {categories.map(categoria => (
              <Card
                key={categoria.id}
                nombre={categoria.titulo}
                imagen={categoria.imagen}
                cantidad={categoria.descripcion}
                handleClick={handleClick}
              />
            ))}

        </div>
    </div>
  )
}

export default Categorias
