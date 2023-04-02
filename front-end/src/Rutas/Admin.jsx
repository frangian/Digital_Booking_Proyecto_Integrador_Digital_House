import React, { useState } from "react";
import ProductoForm from "../Components/ProductoForm";
import ProductHeader from "../Components/ProductHeader";
import {  useNavigate } from "react-router-dom";

const Admin = () => {
  const navigate = useNavigate();

  const [confirmada, setConfirmada] = useState(false);
  const [loading, setLoading] = useState(true);

  const handleConfirmacion = () => {
    setConfirmada(true);
  };

  const handleLoading = (bool) => {
    setLoading(bool);
  }

  return (
    <div className="admin-page">
      <div className={`producto-page-container ${!confirmada ? "" : "oculto"}`}>
        <ProductHeader tituloProducto={"Administracion"} tituloCategoria={"oculto"} isLoading={loading} handleLoading={()=>handleLoading()}/>
        <ProductoForm handleConfirmacion={handleConfirmacion} producto={false}/>
      </div>
      <div
        className={`reserva-correcta-container ${confirmada ? "" : "oculto"}`}
      >
        <div className="card-reserva-confirmada">
          <img src="/Vector.png" alt="Tick reserva" />
          <div className="texto-reserva-confirmada">
            <h3>¡Muchas gracias!</h3>
            <h5>El producto se ha registrado con éxito</h5>
          </div>
          <button
            className="small-button"
            onClick={() => {
              navigate("/");
              setConfirmada(false);
            }}
          >
            ok
          </button>
        </div>
      </div>
    </div>
  );
};

export default Admin;
