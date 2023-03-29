import React, { useState, useEffect } from "react";
import CircularProgress from "@mui/material/CircularProgress";
import { API_URL } from "./Utils/api";
import axios from "axios";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import {faSquarePlus,faSquareXmark} from '@fortawesome/free-solid-svg-icons'

const ProductoForm = ({ handleConfirmacion, loading, handleLoading }) => {
  const [productName, setProductName] = useState("");
  const [category, setCategory] = useState("");
  const [city, setCity] = useState("");
  const [description, setDescription] = useState("");
  const [address, setAddress] = useState("");
  const [attributes, setAttributes] = useState([]);
  const [newAttribute, setNewAttribute] = useState({ name: "", iconClass: "" });
  const [images, setImages] = useState([]);
  const [houseRules, setHouseRules] = useState("");
  const [healthAndSafety, setHealthAndSafety] = useState("");
  const [cancellationPolicy, setCancellationPolicy] = useState("");
  const [categories, setCategories] = useState([]);
  const [cities, setCities] = useState([]);
  const [caracteristicas, setCaracteristicas] = useState("");
  const [error, setError] = useState("");

  useEffect(() => {
    fetchCategories();
    fetchCities();
    fetchCaracteristicas()
  }, []);

  function fetchCategories() {
    axios
      .get(`${API_URL}/categoria`)
      .then((response) => {
        setCategories(response.data);
      })
      .catch((error) => {
        console.log(error);
        setError("Error al obtener categorías");
      });
  }

  function fetchCaracteristicas() {
    axios
      .get(`${API_URL}/caracteristica`)
      .then((response) => {
        setCaracteristicas(response.data);
      })
      .catch((error) => {
        console.log(error);
        setError("Error al obtener caracteristicas");
      });
  }

  function fetchCities() {
    axios
      .get(`${API_URL}/ciudad`)
      .then((response) => {
        const data = response.data;
        setCities(data);

        console.log(response.data);
      })
      .catch((error) => {
        console.log(error);
        setError("Error al obtener ciudades");
      });
  }

  function handleSubmit(event) {
    event.preventDefault();
    const data = {
      productName,
      category,
      city,
      description,
      address,
      attributes,
      images,
      houseRules,
      healthAndSafety,
      cancellationPolicy,
    };
    axios
      .post("api/post", data, {
        headers: { "Content-Type": "application/json" },
      })
      .then((response) => {
        if (response.ok) {
          handleConfirmacion();
        } else {
          throw new Error("Error al crear producto");
        }
      })
      .catch((error) => {
        console.error(error);
        setError(
          "Lamentablemente el producto no ha podido crearse. Por favor intente más tarde."
        );
      });
  }

  function handleNewAttributeChange(event) {
    const { name, value } = event.target;
    setNewAttribute((prevAttribute) => ({ ...prevAttribute, [name]: value }));
  }

  function handleAddAttribute(event) {
    event.preventDefault();
    setAttributes((prevAttributes) => [...prevAttributes, newAttribute]);
    setNewAttribute({ name: "", iconClass: "" });
  }

  function handleAttributeChange(index, event) {
    const { name, value } = event.target;
    setAttributes((prevAttributes) => {
      const newAttributes = [...prevAttributes];
      newAttributes[index][name] = value;
      return newAttributes;
    });
  }

  function handleDeleteAttribute(index) {
    setAttributes((prevAttributes) => {
      const newAttributes = [...prevAttributes];
      newAttributes.splice(index, 1);
      return newAttributes;
    });
  }

  function handleImageChange(event) {
    setImages(Array.from(event.target.files));
  }

  return (
    <div className="crear-producto-container">
      <h1>Crear un producto</h1>
      <div className="producto-form-container">
        <form id="producto-form" onSubmit={(event) => handleSubmit(event)}>
          <fieldset className="info-producto-container">
            <label>
              <span>Nombre del producto:</span>
              <input
                type="text"
                value={productName}
                onChange={(event) => setProductName(event.target.value)}
              />
            </label>
            <label>
              <span>Categoría:</span>
              <select
                value={category}
                onChange={(event) => setCategory(event.target.value)}
              >
                <option value="">Selecciona una categoría</option>
                {categories.map((category) => (
                  <option key={category.id} value={category.id}>
                    {category.titulo}
                  </option>
                ))}
              </select>
            </label>
            <label>
              <span>Dirección:</span>
              <input
                type="text"
                value={address}
                onChange={(event) => setAddress(event.target.value)}
              />
            </label>
            <label>
              <span>Ciudad:</span>
              <select
                value={city}
                onChange={(event) => setCity(event.target.value)}
              >
                <option value="">Selecciona una ciudad</option>
                {cities.map((city) => (
                  <option key={city.id} value={city.id}>
                    {`${city.nombre}, ${city.pais}`}
                  </option>
                ))}
              </select>
            </label>
          </fieldset>
          <div className="descripcion-section">
            <label>
              <span>Descripción:</span>
              <textarea
                value={description}
                onChange={(event) => setDescription(event.target.value)}
              />
            </label>
          </div>
          <h2>Agregar atributos</h2>
          <fieldset className=" atributos"> 
            {attributes.map((attribute, index) => (
              <div key={index} className="atributo-container">
                <label>
                  <span>Nombre:</span>
                  <input
                    type="text"
                    value={attribute.name}
                    onChange={(event) => handleAttributeChange(index, event)}
                    name="name"
                    className="input1"
                  />
                </label>
                <label>
                  <span>Clase de icono:</span>
                  <input
                    type="text"
                    value={attribute.iconClass}
                    onChange={(event) => handleAttributeChange(index, event)}
                    name="iconClass"
                    className="input2"
                  />
                </label>
                <FontAwesomeIcon icon={faSquareXmark}  style={{color: "#545776",}} onClick={() => handleDeleteAttribute(index)}/>
              </div>
            ))}
            <div className="atributo-container">
              <label>
                <span>Nombre:</span>
                <input
                  type="text"
                  value={newAttribute.name}
                  onChange={handleNewAttributeChange}
                  name="name"
                  className="input1"
                />
              </label>
              <label>
                <span>Clase de icono:</span>
                <input
                  type="text"
                  value={newAttribute.iconClass}
                  onChange={handleNewAttributeChange}
                  name="iconClass"
                  className="input2"
                />
              </label>
              <FontAwesomeIcon icon={faSquarePlus} className="icono-agregar" style={{color: "#1dbeb4",}} onClick={handleAddAttribute} />
            </div>
          </fieldset>

          <h2>Politicas del producto</h2>
          <fieldset className="politicas-producto">
            
            <label>
              <span>Normas de la casa:</span>
              <p>Descripcion</p>
              <textarea
                value={houseRules}
                onChange={(event) => setHouseRules(event.target.value)}
              />
            </label>
            <label>
              <span>Salud y seguridad:</span>
              <p>Descripcion</p>
              <textarea
                value={healthAndSafety}
                onChange={(event) => setHealthAndSafety(event.target.value)}
              />
            </label>
            <label>
              <span>Política de cancelación:</span>
              <p>Descripcion</p>
              <textarea
                value={cancellationPolicy}
                onChange={(event) => setCancellationPolicy(event.target.value)}
              />
            </label>
          </fieldset>
        </form>
        <button
          type="submit"
          form="producto-form"
          className="btn-crear-producto"
          id="submit-product"
          disabled={loading}
        >
          {loading ? (
            <CircularProgress
              sx={{ color: "#fff", marginTop: "5px" }}
              size="1.4rem"
            />
          ) : (
            "Crear"
          )}
        </button>
      </div>
    </div>
  );
};

export default ProductoForm;
