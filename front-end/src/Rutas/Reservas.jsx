import axios from 'axios';
import React, { useState, useEffect } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import PoliticasProducto from '../Components/PoliticasProducto';
import ProductHeader from '../Components/ProductHeader';
import ReservaForm from '../Components/ReservasComponents/ReservaForm';

const Reservas = () => {

    const { id } = useParams();
    const navigate = useNavigate();
    const [data, setData] = useState({});
    const [categoria, setCategoria] = useState({});
    const [normas, setNormas] = useState([]);
    const [seguridad, setSeguridad] = useState([]);
    const [imagen, setImagen] = useState({})
    const [ciudad, setCiudad] = useState({});
    const [reservas, setReservas] = useState([]);

    useEffect(() => {
        axios.get(`http://localhost:8080/producto/${id}`)
        .then(res => {
            setData(res.data);
            setCategoria(res.data.categoria);
            setNormas(res.data.normas.split(","));
            setSeguridad(res.data.seguridad.split(","));
            setImagen(res.data.imagenes[0]);
            setCiudad(res.data.ciudad);
        }) 
        axios.get(`http://localhost:8080/reserva/producto/${id}`)
        .then(res => {
            setReservas(res.data)
        })  
    }, [id])

    return (
        <div className='reservas-page'>
            <div className="reservas-page-container">
                <ProductHeader tituloCategoria={categoria?.titulo} tituloProducto={data?.titulo}/>
                <ReservaForm 
                tituloCategoria={categoria?.titulo} 
                tituloProducto={data?.titulo}
                ubicacion={`${ciudad?.nombre}, ${ciudad?.provincia}, ${ciudad?.pais}`}
                img={imagen?.url_imagen}
                reservas={reservas}
                productoId={id}
                />
                <PoliticasProducto 
                cancelacion={data?.cancelacion}
                normas={normas}
                seguridad={seguridad}
                />
            </div>
            <div className="reserva-correcta-container oculto">
                <div className="card-reserva-confirmada">
                    <img src="/vector.png" alt="Tick reserva"/>
                    <div className="texto-reserva-confirmada">
                        <h3>¡Muchas gracias!</h3>
                        <h5>Su reserva se ha realizado con éxito</h5>
                    </div>
                    <button className='small-button' onClick={() => navigate("/")}>ok</button>
                </div>
            </div>
        </div>
    )
}

export default Reservas