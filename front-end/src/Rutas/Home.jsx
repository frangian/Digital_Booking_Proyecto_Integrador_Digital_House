import React from 'react'
import Categorias from '../Components/Categorias'
import Recomendaciones from '../Components/Recomendaciones'


const Home = () => {
  return (
    <div className="container">
      <Categorias />
      <Recomendaciones />
    </div>
  )
}

export default Home