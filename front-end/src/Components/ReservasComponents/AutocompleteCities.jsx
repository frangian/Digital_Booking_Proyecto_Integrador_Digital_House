import React, { useState } from 'react'
import { AutoComplete } from "antd"
import axios from 'axios'

const mockVal = (str) => ({
    value: str
})

const AutocompleteCities = ({ ciudad, changeCiudad }) => {

    const [ciudades, setCiudades] = useState([]);

    const getPanelValue = (searchText) => {
        if (!searchText) {
            return [];
        } else {
            changeCiudad(searchText);
            axios.get(`https://api.teleport.org/api/cities/?search=${searchText}`)
            .then(res => {
                setCiudades(res.data._embedded['city:search-results'].map(city => mockVal(city.matching_full_name)));
            })
            return ciudades;
        }
    }

    const onChange = (data) => {
        changeCiudad(data);
    }


    return (
        <div className='autocomplete-container'>
            <AutoComplete
            value={ciudad}
            options={ciudades}
            onSearch={(text) => setCiudades(getPanelValue(text))}
            onChange={onChange}
            className="autocomplete-cities"
            style={{ width: "100%", height: "40px" }}
            />
        </div>
    )
}

export default AutocompleteCities