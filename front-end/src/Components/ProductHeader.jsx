import React from 'react'
import { useNavigate } from 'react-router-dom'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faChevronLeft } from '@fortawesome/free-solid-svg-icons'
import Skeleton, { SkeletonTheme } from 'react-loading-skeleton'

const ProductHeader = ({ tituloCategoria, tituloProducto }) => {

    const navigate = useNavigate(); 

    return (
        <div className="product-header">
            <div className="left-product-header skeleton-header">
                <SkeletonTheme baseColor="#545776" highlightColor="#fff">
                    <p className={tituloCategoria}>{tituloCategoria || <Skeleton style={{width: "150px"}}/>}</p>
                    <h3>{tituloProducto || <Skeleton style={{width: "200px"}}/>}</h3>
                </SkeletonTheme>
            </div>
            <FontAwesomeIcon icon={faChevronLeft} className="go-back-icon"
            onClick={() => navigate(-1)}
            />
        </div>
    )
}

export default ProductHeader