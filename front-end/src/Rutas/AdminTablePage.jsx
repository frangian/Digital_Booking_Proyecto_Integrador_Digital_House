import React, { useState, useEffect } from 'react'
import ProductHeader from '../Components/ProductHeader';
import AdminTable from '../Components/Vistas/AdminTable';
import { useNavigate } from 'react-router-dom';

const AdminTablePage = () => {

    const [confirmada, setConfirmada] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        let jwt = localStorage.getItem('jwt');
        if(!jwt) {
            navigate("/")
        } else { 
          let partes = jwt.split('.');
          let contenido = atob(partes[1]);
          let datos = JSON.parse(contenido);
          if(datos.authorities[0].authority !== "ADMIN") {
            navigate("/")
          }
        }
    }, [])

    const handleConfirmacion = () => {
        setConfirmada(true);
    };

    const renderTable = () => {
        if (confirmada) {
            return (
                <div className={`reserva-correcta-container`}>
                    <div className="card-reserva-confirmada">
                        <img src="/Vector.png" alt="Tick reserva" />
                        <div className="texto-reserva-confirmada">
                            <h3>¡Muchas gracias!</h3>
                            <h5>El producto se ha actualizado con éxito</h5>
                        </div>
                        <button
                            className="small-button"
                            onClick={() => {
                            navigate("/adminTable");
                            setConfirmada(false);
                            }}
                        >
                            ok
                        </button>
                    </div>
                    </div>
            )
        } else {
            return (
                <div className={`producto-page-container`}>
                    <ProductHeader tituloProducto={"Administracion"} tituloCategoria={"oculto"}/>
                    <AdminTable handleConfirmacion={handleConfirmacion}/>
                </div>
            )
        }
    }

    return (
        <div className="admin-page">
            {renderTable()}
        </div>
    )
}

export default AdminTablePage