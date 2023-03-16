import * as React from "react";

const CardCategoria = ({ nombre, imagen, cantidad, handleClick }) => {
  return (
    <div className="card" onClick={handleClick}>
      <img src={imagen} alt={nombre} />
      <div>
        <h3>{nombre}</h3>
        <p>
          {cantidad} {nombre}
        </p>
      </div>
    </div>
  );
};

export default CardCategoria;
