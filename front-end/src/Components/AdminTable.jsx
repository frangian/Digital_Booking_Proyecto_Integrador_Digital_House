import axios from 'axios';
import React, { useState, useEffect } from 'react'

const AdminTable = () => {

    const [metodoBusqueda, setMetodoBusqueda] = useState("id");
    const [valorBusqueda, setValorBusqueda] = useState("");

    const handleSubmit = () => {
        let obj = {
            mb: metodoBusqueda,
            vb: valorBusqueda
        }
        console.log(obj);
    }

    return (
        <div className='table-page'>
            <div className="busqueda-tabla">
                <div className="inputs-tabla-busqueda">
                    <select 
                    value={metodoBusqueda}
                    onChange={(e) => {
                        setMetodoBusqueda(e.target.value);
                        console.log(e.target.value);
                    }}
                    placeholder='Buscar por...'
                    style={{ padding: "10px" }}
                    >
                        <option value="id">Id</option>
                        <option value="producto">Nombre del producto</option>
                        <option value="categoria">Categoria</option>
                    </select>
                    {
                        metodoBusqueda === "categoria" ? 
                        (
                            <select 
                            value={valorBusqueda}
                            onChange={(e) => {
                                setValorBusqueda(e.target.value);
                                console.log(e.target.value);
                            }}
                            placeholder='Buscar por...'
                            style={{ padding: "10px" }}
                            >
                                <option value="id">Id</option>
                                <option value="producto">Nombre del producto</option>
                                <option value="categoria">Categoria</option>
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
                    <div class="nombre-celda">Nombre del producto</div>
                    <div class="editar-borrar">Editar</div>
                    <div class="editar-borrar">Borrar</div>
                </div>
                <div className="tabla-celda">
                    <div className="id-celda">#</div>
                    <div class="nombre-celda">Nombre del producto</div>
                    <div class="editar-borrar">Editar</div>
                    <div class="editar-borrar">Borrar</div>
                </div>
                <div className="tabla-celda">
                    <div className="id-celda">#</div>
                    <div class="nombre-celda">Nombre del producto</div>
                    <div class="editar-borrar">Editar</div>
                    <div class="editar-borrar">Borrar</div>
                </div>
                <div className="tabla-celda">
                    <div className="id-celda">#</div>
                    <div class="nombre-celda">Nombre del producto</div>
                    <div class="editar-borrar">Editar</div>
                    <div class="editar-borrar">Borrar</div>
                </div>
                <div className="tabla-celda">
                    <div className="id-celda">#</div>
                    <div class="nombre-celda">Nombre del producto</div>
                    <div class="editar-borrar">Editar</div>
                    <div class="editar-borrar">Borrar</div>
                </div>
                <div className="tabla-celda">
                    <div className="id-celda">#</div>
                    <div class="nombre-celda">Nombre del producto</div>
                    <div class="editar-borrar">Editar</div>
                    <div class="editar-borrar">Borrar</div>
                </div>
                <div className="tabla-celda">
                    <div className="id-celda">#</div>
                    <div class="nombre-celda">Nombre del producto</div>
                    <div class="editar-borrar">Editar</div>
                    <div class="editar-borrar">Borrar</div>
                </div>
                <div className="tabla-celda">
                    <div className="id-celda">#</div>
                    <div class="nombre-celda">Nombre del producto</div>
                    <div class="editar-borrar">Editar</div>
                    <div class="editar-borrar">Borrar</div>
                </div>
                <div className="tabla-celda">
                    <div className="id-celda">#</div>
                    <div class="nombre-celda">Nombre del producto</div>
                    <div class="editar-borrar">Editar</div>
                    <div class="editar-borrar">Borrar</div>
                </div>
                <div className="tabla-celda">
                    <div className="id-celda">#</div>
                    <div class="nombre-celda">Nombre del producto</div>
                    <div class="editar-borrar">Editar</div>
                    <div class="editar-borrar">Borrar</div>
                </div>
                <div className="tabla-celda">
                    <div className="id-celda">#</div>
                    <div class="nombre-celda">Nombre del producto</div>
                    <div class="editar-borrar">Editar</div>
                    <div class="editar-borrar">Borrar</div>
                </div>
                <div className="tabla-celda">
                    <div className="id-celda">#</div>
                    <div class="nombre-celda">Nombre del producto</div>
                    <div class="editar-borrar">Editar</div>
                    <div class="editar-borrar">Borrar</div>
                </div>
                <div className="tabla-celda">
                    <div className="id-celda">#</div>
                    <div class="nombre-celda">Nombre del producto</div>
                    <div class="editar-borrar">Editar</div>
                    <div class="editar-borrar">Borrar</div>
                </div>
                <div className="tabla-celda">
                    <div className="id-celda">#</div>
                    <div class="nombre-celda">Nombre del producto</div>
                    <div class="editar-borrar">Editar</div>
                    <div class="editar-borrar">Borrar</div>
                </div>
                <div className="tabla-celda">
                    <div className="id-celda">#</div>
                    <div class="nombre-celda">Nombre del producto</div>
                    <div class="editar-borrar">Editar</div>
                    <div class="editar-borrar">Borrar</div>
                </div>
                <div className="tabla-celda">
                    <div className="id-celda">#</div>
                    <div class="nombre-celda">Nombre del producto</div>
                    <div class="editar-borrar">Editar</div>
                    <div class="editar-borrar">Borrar</div>
                </div>
                <div className="tabla-celda">
                    <div className="id-celda">#</div>
                    <div class="nombre-celda">Nombre del producto</div>
                    <div class="editar-borrar">Editar</div>
                    <div class="editar-borrar">Borrar</div>
                </div>
                <div className="tabla-celda">
                    <div className="id-celda">#</div>
                    <div class="nombre-celda">Nombre del producto</div>
                    <div class="editar-borrar">Editar</div>
                    <div class="editar-borrar">Borrar</div>
                </div>
                <div className="tabla-celda">
                    <div className="id-celda">#</div>
                    <div class="nombre-celda">Nombre del producto</div>
                    <div class="editar-borrar">Editar</div>
                    <div class="editar-borrar">Borrar</div>
                </div>
                <div className="tabla-celda">
                    <div className="id-celda">#</div>
                    <div class="nombre-celda">Nombre del producto</div>
                    <div class="editar-borrar">Editar</div>
                    <div class="editar-borrar">Borrar</div>
                </div>
                <div className="tabla-celda">
                    <div className="id-celda">#</div>
                    <div class="nombre-celda">Nombre del producto</div>
                    <div class="editar-borrar">Editar</div>
                    <div class="editar-borrar">Borrar</div>
                </div>
            </div>
        </div>
    )
}

export default AdminTable