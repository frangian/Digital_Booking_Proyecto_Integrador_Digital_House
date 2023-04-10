import React from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faSquarePlus, faSquareXmark } from "@fortawesome/free-solid-svg-icons";

const AddImagenForm = ({
  imagenes,
  handleImgChange,
  handleDeleteImg,
  errorImagen,
  newImg,
  handleNewImgChange,
  setErrorImagen,
  handleAddImg,
  manejarCambios
}) => {
  return (
    <div>
      {imagenes.map((img, index) => (
        <div key={index} className="atributo-container inputs-delete atributos">
          <label>
            <input
              type="text"
              value={img.url_imagen}
              onChange={(event) => handleImgChange(index, event)}
              name="url_imagen"
              className="atributo-container-select"
            />
          </label>
          
          <FontAwesomeIcon
            icon={faSquareXmark}
            style={{ color: "#545776" }}
            className="icono-agregar"
            onClick={(event) => handleDeleteImg(event,index)}
          />
        </div>
      ))}
      <div className="atributo-container atributos">
        <label className={`${errorImagen ? "error" : ""}`}>
          
          <input
            type="text"
            value={newImg.url_imagen}
            placeholder="Instertar https://..."
            onChange={(event) => {
              handleNewImgChange(event);
              manejarCambios(event)
            }}
            name="url_imagen"
            className="atributo-container-input"
          />
          <p>{errorImagen}</p>
        </label>

        {/* AGREGUE INPUT NUEVO */}
        <input 
            type="text"
            value={newImg.titulo}
            placeholder="Instertar titulo"
            maxLength={20}
            onChange={(event) => {
              handleNewImgChange(event);
            }}
            name="titulo"
            className="atributo-container-input"
          />

        <FontAwesomeIcon
          icon={faSquarePlus}
          className="icono-agregar"
          style={{ color: "#1dbeb4" }}
          onClick={(event) => {
            event.target.parentElement.classList.remove("error");
            setErrorImagen("");
            handleAddImg(event);
          }}
        />
      </div>

      
    </div>
  );
};

export default AddImagenForm;
