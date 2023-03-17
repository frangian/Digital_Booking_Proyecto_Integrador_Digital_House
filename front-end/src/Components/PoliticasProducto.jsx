import React from 'react'
import Skeleton from 'react-loading-skeleton'

const PoliticasProducto = ({ normas, seguridad, cancelacion, isLoading }) => {
    return (
    <div className="info-extra-container" >
        <h3 id="mapa">Qué tenés que saber</h3>
        <div className="info-extra">
            <section className="info">
                <h4>Normas de la casa</h4>
                <ul>
                    {
                    isLoading ? normas?.map((element, i) => (<li key={i}>{element}</li>)) : <Skeleton count={3}/>
                    }
                </ul>
            </section>
            <section className="info">
                <h4>Salud y seguridad</h4>
                <ul>
                    {isLoading ? seguridad?.map((element, i) => (<li key={i}>{element}</li>)) : <Skeleton count={3}/>}
                </ul>
            </section>
            <section className="info">
                <h4>Política de cancelación</h4>
                <ul>
                    <li>{isLoading ? cancelacion : <Skeleton />}</li>
                </ul>
            </section>
        </div>
    </div>
    )
}

export default PoliticasProducto