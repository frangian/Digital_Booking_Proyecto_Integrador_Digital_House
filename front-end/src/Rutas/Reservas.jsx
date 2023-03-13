import axios from 'axios';
import React, { useState, useEffect } from 'react'
import { useParams } from 'react-router-dom'
import PoliticasProducto from '../Components/PoliticasProducto';
import ProductHeader from '../Components/ProductHeader';
import ReservaForm from '../Components/ReservasComponents/ReservaForm';

const Reservas = () => {

    const { id } = useParams();

    const [data, setData] = useState({});
    const [categoria, setCategoria] = useState({});
    const [normas, setNormas] = useState([]);
    const [seguridad, setSeguridad] = useState([]);
    const [imagen, setImagen] = useState({})
    const [ciudad, setCiudad] = useState({});

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
    }, [id])

    return (
        <div className='product-page'>
            <ProductHeader tituloCategoria={categoria?.titulo} tituloProducto={data?.titulo}/>
            <ReservaForm 
            tituloCategoria={categoria?.titulo} 
            tituloProducto={data?.titulo}
            ubicacion={`${ciudad?.nombre}, ${ciudad?.provincia}, ${ciudad?.pais}`}
            img={imagen?.url_imagen}
            />
            <PoliticasProducto 
            cancelacion={data?.cancelacion}
            normas={normas}
            seguridad={seguridad}
            />
        </div>
    )
}

export default Reservas