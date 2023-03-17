import React from 'react'
import { useNavigate } from 'react-router-dom'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faChevronLeft } from '@fortawesome/free-solid-svg-icons'

const ProductHeader = ({ tituloCategoria, tituloProducto }) => {

    const navigate = useNavigate(); 

    return (
        <div className="product-header">
            <div className="left-product-header">
                <p>{tituloCategoria}</p>
                <h3>{tituloProducto}</h3>
            </div>
            <FontAwesomeIcon icon={faChevronLeft} className="go-back-icon"
            onClick={() => navigate(-1)}
            />
        </div>
    )
}

export default ProductHeader