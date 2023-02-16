import * as React from "react";

const CardRecomendaciones = ({ title, imagen, category, location, description }) => {
  
  return (
    <div className="card-recomendaciones">
      <img src={imagen} alt={title} />
      <div>
        <p>{category}</p>
        <h2>{title}</h2>
        <p>{location}</p>
        <p>{description}</p>
        <input type="button" className="ver-mas-btn" value="ver mas"/>
      </div>
    </div>
  );
};

export default CardRecomendaciones;
