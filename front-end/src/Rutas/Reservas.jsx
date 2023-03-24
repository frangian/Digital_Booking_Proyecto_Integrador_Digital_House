import axios from 'axios';
import React, { useState, useEffect } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import PoliticasProducto from '../Components/PoliticasProducto';
import ProductHeader from '../Components/ProductHeader';
import ReservaForm from '../Components/ReservasComponents/ReservaForm';
import { API_URL } from '../Components/Utils/api';

const Reservas = () => {

    const { id } = useParams();
    const navigate = useNavigate();
    const [data, setData] = useState({});
    const [normas, setNormas] = useState([]);
    const [seguridad, setSeguridad] = useState([]);
    const [imagen, setImagen] = useState({})
    const [ciudad, setCiudad] = useState({});
    const [reservas, setReservas] = useState([]);
    const [confirmada, setConfirmada] = useState(false);
    const [isLoading, setIsLoading] = useState(false);

    useEffect(() => {
        window.scrollTo({top: 0})
        if(!localStorage.getItem("jwt")) {
            navigate("/")
        } else {
            setIsLoading(false);
            axios.get(`${API_URL}/producto/${id}`)
            .then(res => {
                setData(res.data);
                setNormas(res.data.normas.split(","));
                setSeguridad(res.data.seguridad.split(","));
                setImagen(res.data.imagenes[0]);
                setCiudad(res.data.ciudad);
                setIsLoading(true);
            }) 
            axios.get(`${API_URL}/reserva/producto/${id}`)
            .then(res => {
                setReservas(res.data)
            })  
        }
    }, [id])

    const handleConfirmacion = () => {
        setConfirmada(true);
    }

    return (
        <div className='reservas-page'>
            <div className={`reservas-page-container ${!confirmada ? "" : "oculto"}`}>
                <ProductHeader tituloCategoria={data?.categoria} tituloProducto={data?.titulo}/>
                <ReservaForm 
                tituloCategoria={data?.categoria} 
                tituloProducto={data?.titulo}
                ubicacion={`${ciudad?.nombre}, ${ciudad?.provincia}, ${ciudad?.pais}`}
                img={imagen?.url_imagen}
                reservas={reservas}
                productoId={id}
                handleConfirmacion={handleConfirmacion}
                />
                <PoliticasProducto 
                cancelacion={data?.cancelacion}
                normas={normas}
                seguridad={seguridad}
                isLoading={isLoading}
                />
            </div>
            <div className={`reserva-correcta-container ${confirmada ? "" : "oculto"}`}>
                <div className="card-reserva-confirmada">
                    <img src="/Vector.png" alt="Tick reserva"/>
                    <div className="texto-reserva-confirmada">
                        <h3>¡Muchas gracias!</h3>
                        <h5>Su reserva se ha realizado con éxito</h5>
                    </div>
                    <button className='small-button' 
                    onClick={() => {
                        navigate("/")
                        setConfirmada(false);
                    }}>
                        ok
                    </button>
                </div>
            </div>
        </div>
    )
}

export default Reservas