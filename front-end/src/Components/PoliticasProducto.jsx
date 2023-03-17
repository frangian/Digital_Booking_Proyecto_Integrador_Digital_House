import React from 'react'

const PoliticasProducto = ({ normas, seguridad, cancelacion }) => {
    return (
    <div className="info-extra-container" >
        <h3 id="mapa">Qué tenés que saber</h3>
        <div className="info-extra">
            <section className="info">
                <h4>Normas de la casa</h4>
                <ul>
                    {normas?.map((element, i) => (<li key={i}>{element}</li>))}
                </ul>
            </section>
            <section className="info">
                <h4>Salud y seguridad</h4>
                <ul>
                    {seguridad?.map((element, i) => (<li key={i}>{element}</li>))}
                </ul>
            </section>
            <section className="info">
                <h4>Política de cancelación</h4>
                <ul>
                    <li>{cancelacion}</li>
                </ul>
            </section>
        </div>
    </div>
    )
}

export default PoliticasProducto