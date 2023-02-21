import React from 'react'
import Buscador from '../Components/Buscador'
import Categorias from '../Components/Categorias'
import Recomendaciones from '../Components/Recomendaciones'


const Home = () => {
  return (
    <div className="container">
      <Buscador />
      <Categorias />
      <Recomendaciones />
    </div>
  )
}

export default Home