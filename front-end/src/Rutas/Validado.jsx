import React, { useEffect, useContext } from 'react'
import { useNavigate, useLocation } from 'react-router-dom'
import { API_URL } from '../Components/Utils/api';
import { ContextGlobal } from '../Components/Utils/globalContext';
import axios from 'axios';

const Validado = () => {

    const {state, dispatch} = useContext(ContextGlobal);
    const navigate = useNavigate();
    const location = useLocation();
    const isFromGmail = new URLSearchParams(location.search).get('source') === 'gmail';

    const validarUsuario = (id, ciudad, jwt) => {
        
        const objPutUsuario = {
            id: id,
            ciudad: ciudad,
            validado: true,
            usuarioRole: "USER"
        }
        const config = {
            method: "PUT",
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${jwt}`
            },
            body: JSON.stringify(objPutUsuario)
        }
        fetch(`${API_URL}/usuario`, config)
        .then(res => res.json())
        .then(data => {
            console.log(data);
        })
        .catch(error => {
            console.error('Error:', error);
        });
    }

    useEffect(() => {
        let jwt = localStorage.getItem("jwt");
        if (jwt && isFromGmail) {
            let partes = jwt.split('.');
            let contenido = atob(partes[1]);
            let datos = JSON.parse(contenido);
            const headers = { 'Authorization': `Bearer ${jwt}` };
            axios.get(`${API_URL}/usuario/email/${datos.sub}`, { headers })
            .then(res => {
                dispatch({
                    type: "register",
                    payload: {
                    ...state,
                    user: res.data,
                    logged: true
                    }
                })
                validarUsuario(res.data.id, res.data.ciudad, jwt);
            })  
        } else {
            navigate("/");
        }
    }, [])

    return (
        <div className='reservas-page'>
            <div className={`reserva-correcta-container`}>
                <div className="card-reserva-confirmada">
                    <img src="/Vector.png" alt="Tick reserva"/>
                    <div className="texto-reserva-confirmada">
                        <h3>¡Muchas gracias!</h3>
                        <h5>Su usuario se ha activado con éxito</h5>
                    </div>
                    <button className='small-button' 
                    onClick={() => {
                        navigate("/")
                    }}>
                        Volver al home
                    </button>
                </div>
            </div>
        </div>
    )
}

export default Validado