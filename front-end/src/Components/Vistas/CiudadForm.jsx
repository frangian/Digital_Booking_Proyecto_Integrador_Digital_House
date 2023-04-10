import React, { useState } from "react";
import axios from "axios";
import { API_URL } from "../Utils/api"

const CiudadForm = ({ handleCiudadCreada }) => {
  const [nombre, setNombre] = useState("");
  const [pais, setPais] = useState("");
  const [provincia, setProvincia] = useState("");

  let jwt = localStorage.getItem('jwt');

  const handleSubmit = (e) => {
    e.preventDefault();
    const data = {
      nombre,
      pais,
      provincia,
    };
    axios
      .post(`${API_URL}/ciudad`, data, { headers: { "Content-Type": "application/json", 'Authorization': `Bearer ${jwt}`}})
      .then((response) => {
        handleCiudadCreada(true);
      })
      .catch((error) => {
        console.error(error);
      });
  };

  const handleChangeNombre = (e) => {
    setNombre(e.target.value );
  };
  const handleChangePais = (e) => {
    setPais(e.target.value );
  };
  const handleChangeProvincia = (e) => {
    setProvincia( e.target.value );
  };
  return (
    <div>
      <form className="form-crear-ciudad" onSubmit={handleSubmit}>
        <label>
          <span>Nombre ciudad:</span>
          <input type="text" value={nombre} onChange={handleChangeNombre} />
        </label>
        <label>
          <span>Nombre pais:</span>
          <input type="text" value={pais} onChange={handleChangePais} />
        </label>
        <label>
          <span>Nombre provincia:</span>
          <input
            type="text"
            value={provincia}
            onChange={handleChangeProvincia}
          />
        </label>
        <button type="submit" className="ciudad-button">Crear ciudad</button>
      </form>
    </div>
  );
};

export default CiudadForm;
