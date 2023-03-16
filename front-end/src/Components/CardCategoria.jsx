import * as React from "react";

const CardCategoria = ({ nombre, imagen, cantidad, handleClick, isSelected }) => {
  return (
    <div className={`card ${isSelected ? "selected" : ""}`} onClick={handleClick}>
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
