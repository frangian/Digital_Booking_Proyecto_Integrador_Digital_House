import * as React from "react";

const CardCategoria = ({ nombre, imagen, cantidad }) => {
  
  return (
    <div className="card">
      <img src={imagen} alt={nombre} />
      <div>
        <h3>{nombre}</h3>
        <p>{cantidad} hoteles</p>
      </div>
    </div>
  );
};

export default CardCategoria;
