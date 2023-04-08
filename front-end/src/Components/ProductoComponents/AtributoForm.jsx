import React from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faSquarePlus, faSquareXmark } from "@fortawesome/free-solid-svg-icons";

const AtributoForm = ({
  attributes,
  handleAttributeChange,
  handleDeleteAttribute,
  errorCaracteristica,
  caracteristica,
  caracteristicasArray,
  handleChangeCaracteristica,
  setErrorCaracteristica,
  handleAddAttribute,
  manejarCambios
}) => {
  return (
    <div>
        {attributes.map((attribute, index) => (
          <div key={index} className="atributo-container inputs-delete atributos">
            
            <label>
              <input
                type="text"
                value={attribute.id}
                onChange={(event) => handleAttributeChange(index, event)}
                name="name"
                className="atributo-container-select"
              />
            </label>

            <FontAwesomeIcon
              icon={faSquareXmark}
              style={{ color: "#545776" }}
              className="icono-agregar"
              onClick={() => handleDeleteAttribute(index)}
            />
          </div>
        ))}
      
      
      <div className="atributo-container atributos">
        <div
          className={`atributo-container-select ${
            errorCaracteristica ? "error" : ""
          }`}
        >
          <select
            value={caracteristica}
            onChange={(event) => {
              handleChangeCaracteristica(event);
              
            }}
            className={"atributo-container-select "}
          >
            <option value="">Selecciona una caracteristica</option>
            {caracteristicasArray.map((caracteristica, index) => (
              <option key={caracteristica.id} value={caracteristica.titulo}>
                {caracteristica.titulo}
              </option>
            ))}
          </select>
          <p>{errorCaracteristica}</p>
        </div>

        <FontAwesomeIcon
          icon={faSquarePlus}
          className="icono-agregar"
          style={{ color: "#1dbeb4" }}
          onClick={(event) => {
            event.target.parentElement.classList.remove("error");
            setErrorCaracteristica("");
            handleAddAttribute(event);
          }}
        />
      </div>
    </div>
  );
};

export default AtributoForm;
