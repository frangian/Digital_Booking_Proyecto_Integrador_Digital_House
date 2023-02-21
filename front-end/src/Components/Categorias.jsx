import React from 'react'
import Card from '../Components/Card';
import data from '../Components/Utils/categorias.json'

const Categorias = () => {
  return (
    <div className="categorias-container">
        <h2>Buscar por tipo de alojamiento</h2>
        <div className='card-grid'>
      {data.categorias.map(categoria => (
              <Card
                key={categoria.nombre}
                nombre={categoria.nombre}
                imagen={categoria.imagen}
                cantidad={categoria.cantidad}
              />
            ))}

        </div>
    </div>
  )
}

export default Categorias
