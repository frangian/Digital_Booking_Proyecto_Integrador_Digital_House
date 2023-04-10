import axios from 'axios';
import React, { useState, useEffect } from 'react'
import { API_URL } from '../Utils/api';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPenToSquare, faTrashCan } from '@fortawesome/free-regular-svg-icons'
import Swal from 'sweetalert2'
import ProductoForm from '../ProductoComponents/ProductoForm';

const AdminTable = ({ handleConfirmacion }) => {

    const [metodoBusqueda, setMetodoBusqueda] = useState("id");
    const [valorBusqueda, setValorBusqueda] = useState("");
    const [productos, setProductos] = useState([]);
    const [ciudades, setCiudades] = useState([]);
    const [categorias, setCategorias] = useState([]);
    const [productoSeleccionado, setProductoSeleccionado] = useState(false);
    let jwt = localStorage.getItem('jwt');

    const handleSubmit = () => {
        if (metodoBusqueda === "categoria") {
            axios.get(`${API_URL}/producto/categoria/${valorBusqueda}`)
            .then(res => {
                setProductos(res.data)
            })
        }
        if (metodoBusqueda === "ciudad") {
            axios.get(`${API_URL}/producto/ciudad/${valorBusqueda}`)
            .then(res => {
                setProductos(res.data)
            })
        }
        if (metodoBusqueda === "id") {
            axios.get(`${API_URL}/producto/${valorBusqueda}`)
            .then(res => {
                setProductos([res.data])
            })
        }
        if (metodoBusqueda === "producto") {
            axios.get(`${API_URL}/producto/titulo/${valorBusqueda}`)
            .then(res => {
                setProductos(res.data)
            })
        }
    }

    const handleEliminar = (productoId) => {
        Swal.fire({
            title: '¡Cuidado!',
            text: "No seras capaz de revertir esta acción",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#1dbeb4',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Eliminar',
            cancelButtonText: "Cancelar"
        }).then((result) => {
            if (result.isConfirmed) {
                axios.delete(`${API_URL}/producto/${productoId}`,
                {headers: { 'Authorization': `Bearer ${jwt}` }})
                .then(res => {
                    handleSubmit()
                    Swal.fire({
                        title: "Eliminado",
                        text: "El producto fue eliminado con exito",
                        icon: "success",
                        confirmButtonColor: "#1dbeb4"
                    })
                }).catch(err => {
                    Swal.fire({
                        icon: 'error',
                        title: 'Oops',
                        text: 'Lamentablemente no ha podido eliminar el prdoucto. Por favor intente más tarde',
                        confirmButtonColor: "#1dbeb4"
                    })
                })
            }
        })
    }

    const handlePut = (productoId) => {
        setProductoSeleccionado(false)
        axios.get(`${API_URL}/producto/${productoId}`)
        .then(res => {
            setProductoSeleccionado(res.data);
        }).catch(err => {
            Swal.fire({
                icon: 'error',
                title: 'Oops',
                text: 'No se puede actualizar el prdoucto. Por favor intente más tarde',
                confirmButtonColor: "#1dbeb4"
            })
        })
    }

    useEffect(() => {
        axios.get(`${API_URL}/producto/random`)
        .then(res => {
            setProductos(res.data)
        })
        axios.get(`${API_URL}/categoria`)
        .then(res => {
            setCategorias(res.data)
        })
        axios.get(`${API_URL}/ciudad`)
        .then(res => {
            setCiudades(res.data)
        })
        setProductoSeleccionado(false);
        setMetodoBusqueda("id");
        setValorBusqueda("");
    }, [])

    return (
        <>
            <div className='table-page'>
                <div className="busqueda-tabla">
                    <div className="inputs-tabla-busqueda">
                        <select 
                        value={metodoBusqueda}
                        onChange={(e) => {
                            setMetodoBusqueda(e.target.value);
                            if (e.target.value === "categoria" || e.target.value === "ciudad") {
                                setValorBusqueda(1)
                            }
                        }}
                        placeholder='Buscar por...'
                        style={{ padding: "10px" }}
                        >
                            <option value="id">Id</option>
                            <option value="producto">Nombre del producto</option>
                            <option value="categoria">Categoria</option>
                            <option value="ciudad">Ciudad</option>
                        </select>
                        {
                            metodoBusqueda === "categoria" || metodoBusqueda === "ciudad" ? 
                            (
                                <select 
                                value={valorBusqueda}
                                onChange={(e) => {
                                    setValorBusqueda(e.target.value);
                                }}
                                placeholder='Buscar por...'
                                style={{ padding: "10px" }}
                                >
                                    {
                                        metodoBusqueda === "categoria" ? 
                                        (
                                            categorias.map(categoria => (
                                                <option key={categoria.id} value={categoria.id}>
                                                    {categoria.titulo}
                                                </option>
                                            ))
                                        ) : (
                                            ciudades.map(ciudad => (
                                                <option value={ciudad.id} key={ciudad.id}>
                                                    {`${ciudad.nombre}, ${ciudad.pais}`}
                                                </option>
                                            ))
                                        )
                                    }
                                </select>
                            ) : (
                                <input 
                                style={{ padding: "10px" }}
                                type="text"
                                placeholder={`Inserte un ${metodoBusqueda}`}
                                onChange={(e) => {
                                    setValorBusqueda(e.target.value)
                                }}
                                />
                            )
                        }
                    </div>
                    <div className="btn-busqueda">
                        <button 
                        className='small-button'
                        onClick={() => handleSubmit()}
                        >
                            Buscar
                        </button>
                    </div>
                </div>
                <div className="tabla-productos">
                    <div className="tabla-celda">
                        <div className="id-celda">#</div>
                        <div className="nombre-celda">Nombre</div>
                        <div className="categoria-celda">Categoria</div>
                        <div className="ciudad-celda">Ciudad</div>
                        <div className="editar-borrar">Editar</div>
                        <div className="editar-borrar">Borrar</div>
                    </div>
                    {
                        productos?.map(producto => (
                            <div className="tabla-celda" key={producto.id}>
                                <div className="id-celda">{producto.id}</div>
                                <div className="nombre-celda">{producto.titulo}</div>
                                <div className="categoria-celda">{producto.categoria}</div>
                                <div className="ciudad-celda">{`${producto.ciudad.nombre}, ${producto.ciudad.pais}`}</div>
                                <div className="editar-borrar">
                                    <FontAwesomeIcon 
                                    icon={faPenToSquare} 
                                    style={{color: "#005eff", fontSize: "1.2rem", cursor: "pointer"}} 
                                    onClick={() => handlePut(producto.id)}
                                    />
                                </div>
                                <div className="editar-borrar">
                                    <FontAwesomeIcon 
                                    icon={faTrashCan} 
                                    style={{color: "#ff0000", fontSize: "1.2rem", cursor: "pointer"}} 
                                    onClick={() => handleEliminar(producto.id)}
                                    />
                                </div>
                            </div>
                        ))
                    }
                </div>
            </div>
            {
                productoSeleccionado ? 
                <ProductoForm handleConfirmacion={handleConfirmacion} producto={productoSeleccionado}/>
                : ""
            }
        </>
    )
}

export default AdminTable