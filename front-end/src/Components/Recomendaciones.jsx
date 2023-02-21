import React from 'react'
import Card from '../Components/CardRecomendaciones';
import data from '../Components/Utils/listado.json'

const Recomendaciones = () => {
  return (

    <div className="recomendaciones-container">
        <h2>Recomendaciones</h2>
        <div className='card-grid-recomendaciones'>
      {data.products.map(product => (
              <Card
                key={product.title}
                title={product.title}
                imagen={product.img}
                category={product.category}
                location={product.location}
                description={product.description}
              />
            ))}

        </div>
    </div>
  )
}

export default Recomendaciones