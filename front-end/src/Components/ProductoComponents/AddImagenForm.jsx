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
}) => {
  return (
    <div className=" atributos">
      {imagenes.map((img, index) => (
        <div key={index} className="atributo-container inputs-delete">
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
            onClick={() => handleDeleteImg(index)}
          />
        </div>
      ))}
      <div className="atributo-container">
        <label className={`${errorImagen ? "error" : ""}`}>
          <input
            type="text"
            value={newImg.url_imagen}
            placeholder="Instertar https://..."
            onChange={(event) => {
              handleNewImgChange(event);
            }}
            name="url_imagen"
            className="atributo-container-select"
          />
          <p>{errorImagen}</p>
        </label>

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
