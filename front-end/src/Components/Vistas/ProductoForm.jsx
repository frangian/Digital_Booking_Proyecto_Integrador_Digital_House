import React, { useState, useEffect } from "react";
import CircularProgress from "@mui/material/CircularProgress";
import { API_URL } from "../Utils/api";
import axios from "axios";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faSquarePlus, faSquareXmark } from "@fortawesome/free-solid-svg-icons";
import { tituloXIdServicio } from "../Utils/utils";
import { campoRequerido, validarUrl } from "../Utils/validaciones";
import CiudadForm from "../CiudadForm";
import Modal from "../Modal";
import Slider from 'rsuite/Slider';

const ProductoForm = ({ handleConfirmacion, loading, handleLoading, producto }) => {
  const [sendLoad, setSendLoad] = useState(false);

  const [titulo, setTitulo] = useState("");
  const [descripcion, setDescripcion] = useState("");
  const [categoria, setCategoria] = useState({ id: "" });
  const [ciudad, setCiudad] = useState({ id: "" });
  const [direccion, setDireccion] = useState("");
  const [puntuacion, setPuntuacion] = useState("");
  const [urlMapa, setUrlMapa] = useState("");
  const [desUbicacion, setDesUbicacion] = useState("");

  const [attributes, setAttributes] = useState([]);
  const [newAttribute, setNewAttribute] = useState({ id: "" });

  const [imagenes, setImagenes] = useState([]);
  const [newImg, setNewImg] = useState({ titulo: "", url_imagen: "" });

  const [normas, setNormas] = useState("");
  const [seguridad, setSeguridad] = useState("");
  const [cancelacion, setCancelacion] = useState("");

  const [categories, setCategories] = useState([]);
  const [cities, setCities] = useState([]);

  const [caracteristica, setCaracteristica] = useState("");
  const [caracteristicasArray, setCaracteristicasArray] = useState([]);

  const [error, setError] = useState("");
  const [errorTitulo, setErrorTitulo] = useState("");
  const [errorDescripcion, setErrorDescripcion] = useState("");
  const [errorCategoria, setErrorCategoria] = useState("");
  const [errorCiudad, setErrorCiudad] = useState("");
  const [errorDireccion, setErrorDireccion] = useState("");
  const [errorPuntuacion, setErrorPuntuacion] = useState("");
  const [errorCaracteristica, setErrorCaracteristica] = useState("");
  const [errorNormas, setErrorNormas] = useState("");
  const [errorSeguridad, setErrorSeguridad] = useState("");
  const [errorCancelacion, setErrorCancelacion] = useState("");
  const [errorImagen, setErrorImagen] = useState("");
  const [errorUrlMapa, setErrorUrlMapa] = useState("");
  const [errorDesUbicacion, setErrorDesUbicacion] = useState("");

  const [ciudadCreada, setCiudadCreada] = useState(false);
  const [showModal, setShowModal] = useState(false);

  const resetErrors = () => {
    setErrorTitulo("");
    setErrorDescripcion("");
    setErrorCategoria("");
    setErrorCiudad("");
    setErrorDireccion("");
    setErrorPuntuacion("");
    setErrorCaracteristica("");
    setErrorNormas("");
    setErrorSeguridad("");
    setErrorCancelacion("");
    setErrorImagen("");
    setErrorUrlMapa("");
    setErrorDesUbicacion("");
  };

  const camposRequeridos = [
    {
      campo: titulo,
      mensaje: "Este campo es obligatorio",
      error: setErrorTitulo,
    },
    {
      campo: direccion,
      mensaje: "Este campo es obligatorio",
      error: setErrorDireccion,
    },
    {
      campo: categoria.id,
      mensaje: "Este campo es obligatorio",
      error: setErrorCategoria,
    },
    {
      campo: ciudad.id,
      mensaje: "Este campo es obligatorio",
      error: setErrorCiudad,
    },
    {
      campo: descripcion,
      mensaje: "Este campo es obligatorio",
      error: setErrorDescripcion,
    },
    {
      campo: puntuacion,
      mensaje: "Este campo es obligatorio",
      error: setErrorPuntuacion,
    },
    {
      campo: attributes,
      mensaje: "Este campo es obligatorio",
      error: setErrorCaracteristica,
    },
    {
      campo: normas,
      mensaje: "Este campo es obligatorio",
      error: setErrorNormas,
    },
    {
      campo: seguridad,
      mensaje: "Este campo es obligatorio",
      error: setErrorSeguridad,
    },
    {
      campo: cancelacion,
      mensaje: "Este campo es obligatorio",
      error: setErrorCancelacion,
    },
    {
      campo: imagenes,
      mensaje: "Este campo es obligatorio",
      error: setErrorImagen,
    },
    {
      campo: urlMapa,
      mensaje: "Este campo es obligatorio",
      error: setErrorUrlMapa,
    },
    {
      campo: desUbicacion,
      mensaje: "Este campo es obligatorio",
      error: setErrorDesUbicacion,
    },
  ];

  useEffect(() => {
    if (producto) {
      console.log(producto);
      setTitulo(producto.titulo);
      setDescripcion(producto.descripcion_producto);
      setCategoria("");
      setCiudad({id: `${producto.ciudad.id}`});
      setDireccion(producto.direccion);
      setPuntuacion(producto.puntuacion);
      setUrlMapa(producto.url_ubicacion);
      setDesUbicacion(producto.descripcion_ubicacion);
      setNormas(producto.normas);
      setSeguridad(producto.seguridad);
      setCancelacion(producto.cancelacion);
      setImagenes(producto.imagenes);
    }
  }, [])

  useEffect(() => {
    fetchCategories();
    fetchCities();
    fetchCaracteristicas();
  }, [ciudadCreada]);

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
        setCaracteristicasArray(response.data);
        console.log(response.data);
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
      })
      .catch((error) => {
        console.log(error);
        setError("Error al obtener ciudades");
      });
  }

  function handleSubmit(event) {
    event.preventDefault();
    resetErrors();
    let envio = true;

    camposRequeridos.forEach(({ campo, mensaje, error }) => {
      if (!campoRequerido(campo)) {
        envio = false;
        error(mensaje);
      }
    });

    if (
      !validarUrl(urlMapa, "https://www.google.com/maps/embed") &&
      campoRequerido(urlMapa)
    ) {
      envio = false;
      setErrorUrlMapa("El formato de la url no es correcto");
    }
    if (envio) {
      setSendLoad(true);
      const caracteristicas = attributes.map((atribute) =>
        tituloXIdServicio(atribute.id)
      );
      const descripcion_producto = descripcion;
      const descripcion_ubicacion = desUbicacion;
      const url_ubicacion = urlMapa;

      if (producto) {
        const data = {
          titulo,
          descripcion_producto,
          descripcion_ubicacion,
          url_ubicacion,
          normas,
          seguridad,
          cancelacion,
          puntuacion,
          categoria,
          direccion,
          ciudad,
          caracteristicas,
          imagenes,
        };
  
        console.log(data);
        axios
          .post(`${API_URL}/producto`, data, {
            headers: { "Content-Type": "application/json" },
          })
          .then((response) => {
            setSendLoad(false);
            handleConfirmacion();
            console.log(response.data);
          })
          .catch((error) => {
            console.error(error);
            setSendLoad(false);
            setError(
              "Lamentablemente el producto no ha podido crearse. Por favor intente más tarde."
            );
          });
        
      } else {
        const data = {
          id: producto.id,
          titulo,
          descripcion_producto,
          descripcion_ubicacion,
          url_ubicacion,
          normas,
          seguridad,
          cancelacion,
          puntuacion,
          categoria,
          direccion,
          ciudad,
          caracteristicas,
          imagenes,
        };
  
        console.log(data);
        axios
          .put(`${API_URL}/producto`, data, {
            headers: { "Content-Type": "application/json" },
          })
          .then((response) => {
            setSendLoad(false);
            handleConfirmacion();
            console.log(response.data);
          })
          .catch((error) => {
            console.error(error);
            setSendLoad(false);
            setError(
              "Lamentablemente el producto no ha podido crearse. Por favor intente más tarde."
            );
          });
      }

    }
  }

  const handleChangeCaracteristica = (event) => {
    setCaracteristica(event.target.value);
    setNewAttribute((prevAttribute) => ({
      ...prevAttribute,
      id: event.target.value,
    }));
  };

  function handleAddAttribute(event) {
    event.preventDefault();
    if (
      newAttribute.id !== "" &&
      !attributes.some((obj) => obj.id === newAttribute.id)
    ) {
      setAttributes((prevAttributes) => [...prevAttributes, newAttribute]);
      setNewAttribute({ id: "" });
      setCaracteristica("");
    }
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

  function handleNewImgChange(event) {
    const { name, value } = event.target;
    setNewImg((prevImg) => ({ ...prevImg, [name]: value }));
  }
  function handleAddImg(event) {
    event.preventDefault();
    if (newImg.url_imagen !== "" && validarUrl(newImg.url_imagen, "https")) {
      setImagenes((prevImg) => [...prevImg, newImg]);
      setNewImg({ titulo: "", url_imagen: "" });
    } else {
      setErrorImagen("El formato no es valido");
    }
  }
  function handleImgChange(index, event) {
    const { name, value } = event.target;
    setImagenes((prevImg) => {
      const newImg = [...prevImg];
      newImg[index][name] = value;
      return newImg;
    });
  }
  function handleDeleteImg(index) {
    setImagenes((prevImg) => {
      const newImg = [...prevImg];
      newImg.splice(index, 1);
      return newImg;
    });
  }

  const handleCiudadCreada = (bool) => {
    setCiudadCreada(bool);
    setShowModal(false);
  };

  const handleToggleDescription = () => {
    setShowModal(!showModal);
    setCiudadCreada(false);
  };

  return (
    <div className="crear-producto-container">
      <h1>
        {producto ? `Actualizar el producto con id: ${producto.id}` : "Crear un producto"} <span className="link-demo"> (Link video demo)</span>
      </h1>

      <div className="producto-form-container">
        <form id="producto-form" onSubmit={(event) => handleSubmit(event)}>
          <fieldset className="info-producto-container">
            <label className={` ${errorTitulo ? "error" : ""}`}>
              <span>Nombre del producto:</span>
              <input
                type="text"
                value={titulo}
                onChange={(event) => {
                  event.target.parentElement.classList.remove("error");
                  setErrorTitulo("");
                  setTitulo(event.target.value);
                }}
              />
              <p>{errorTitulo}</p>
            </label>
            <label className={` ${errorCategoria ? "error" : ""}`}>
              <span>Categoría:</span>
              <select
                value={categoria.titulo}
                onChange={(event) => {
                  event.target.parentElement.classList.remove("error");
                  setErrorCategoria("");
                  setCategoria({ id: parseInt(event.target.value) });
                }}
              >
                <option value="">Selecciona una categoría</option>
                {categories.map((category) => (
                  <option key={category.id} value={category.id}>
                    {category.titulo}
                  </option>
                ))}
              </select>
              <p>{errorCategoria}</p>
            </label>
            <label className={` ${errorDireccion ? "error" : ""}`}>
              <span>Dirección:</span>
              <input
                type="text"
                value={direccion}
                onChange={(event) => {
                  event.target.parentElement.classList.remove("error");
                  setErrorDireccion("");
                  setDireccion(event.target.value);
                }}
              />
              <p>{errorDireccion}</p>
            </label>
            <label className={` ${errorCiudad ? "error" : ""}`}>
              <span>
                Ciudad:
                <span onClick={handleToggleDescription} id="crear-ciudad">
                  (Crear nueva ciudad)
                </span>
              </span>
              <select
                value={ciudad.name}
                onChange={(event) => {
                  event.target.parentElement.classList.remove("error");
                  setErrorCiudad("");
                  setCiudad({ id: parseInt(event.target.value) });
                }}
              >
                <option value="">Selecciona una ciudad</option>
                {cities.map((city) => (
                  <option key={city.id} value={city.id}>
                    {`${city.nombre}, ${city.pais}`}
                  </option>
                ))}
              </select>
              <p>{errorCiudad}</p>
            </label>
            <label className={` ${errorUrlMapa ? "error" : ""}`}>
              <span>Url mapa:</span>
              <input
                type="text"
                value={urlMapa}
                placeholder={"https://www.google.com/maps/..."}
                onChange={(event) => {
                  event.target.parentElement.classList.remove("error");
                  setErrorUrlMapa("");
                  setUrlMapa(event.target.value);
                }}
              />
              <p>{errorUrlMapa}</p>
            </label>
            <label className={` ${errorDesUbicacion ? "error" : ""}`}>
              <span>Descripcion ubicacion:</span>
              <input
                type="text"
                value={desUbicacion}
                maxLength={70}
                onChange={(event) => {
                  event.target.parentElement.classList.remove("error");
                  setErrorDesUbicacion("");
                  setDesUbicacion(event.target.value);
                }}
              />
              <p>{errorDesUbicacion}</p>
            </label>
          </fieldset>

          <div className="descripcion-section">
            <label className={` ${errorDescripcion ? "error" : ""}`}>
              <span>Descripción:</span>
              <textarea
                value={descripcion}
                onChange={(event) => {
                  event.target.parentElement.classList.remove("error");
                  setErrorDescripcion("");
                  setDescripcion(event.target.value);
                }}
              />
              <p>{errorDescripcion}</p>
            </label>
            
            <label className={` ${errorPuntuacion ? "error" : ""}`}>
              <span>Puntuacion: {puntuacion}</span>
              <input
                type="range"
                name="puntuacion"
                min="1"
                max="10"
                step="1"
                value={puntuacion}
                onChange={(event) => {
                  event.target.parentElement.classList.remove("error");
                  setPuntuacion(parseInt(event.target.value));
                }}
              ></input>
              <p>{errorPuntuacion}</p>
            </label>
          </div>
          <h2>Agregar atributos</h2>

          <fieldset className=" atributos">
            {attributes.map((attribute, index) => (
              <div key={index} className="atributo-container inputs-delete">
                <label>
                  <input
                    type="text"
                    value={attribute.id}
                    onChange={(event) => handleAttributeChange(index, event)}
                    name="name"
                    className="atributo-container-select"
                  />
                </label>

                <FontAwesomeIcon
                  icon={faSquareXmark}
                  style={{ color: "#545776" }}
                  className="icono-agregar"
                  onClick={() => handleDeleteAttribute(index)}
                />
              </div>
            ))}
            <div className="atributo-container">
              <div
                className={`atributo-container-select ${
                  errorCaracteristica ? "error" : ""
                }`}
              >
                <select
                  value={caracteristica}
                  onChange={(event) => {
                    handleChangeCaracteristica(event);
                  }}
                  className={"atributo-container-select "}
                >
                  <option value="">Selecciona una caracteristica</option>
                  {caracteristicasArray.map((caracteristica, index) => (
                    <option
                      key={caracteristica.id}
                      value={caracteristica.titulo}
                    >
                      {caracteristica.titulo}
                    </option>
                  ))}
                </select>
                <p>{errorCaracteristica}</p>
              </div>

              <FontAwesomeIcon
                icon={faSquarePlus}
                className="icono-agregar"
                style={{ color: "#1dbeb4" }}
                onClick={(event) => {
                  event.target.parentElement.classList.remove("error");
                  setErrorCaracteristica("");
                  handleAddAttribute(event);
                }}
              />
            </div>
          </fieldset>

          <h2>Politicas del producto</h2>
          <fieldset className="politicas-producto">
            <label className={`${errorNormas ? "error" : ""}`}>
              <h5>Normas de la casa:</h5>
              <span>Descripcion</span>
              <textarea
                value={normas}
                onChange={(event) => {
                  event.target.parentElement.classList.remove("error");
                  setErrorNormas("");
                  setNormas(event.target.value);
                }}
              />
              <p className="politicas-parrafo">{errorNormas}</p>
            </label>
            <label className={`${errorSeguridad ? "error" : ""}`}>
              <h5>Salud y seguridad:</h5>
              <span>Descripcion</span>
              <textarea
                value={seguridad}
                onChange={(event) => {
                  event.target.parentElement.classList.remove("error");
                  setErrorSeguridad("");
                  setSeguridad(event.target.value);
                }}
              />
              <p className="politicas-parrafo">{errorSeguridad}</p>
            </label>
            <label className={`${errorCancelacion ? "error" : ""}`}>
              <h5>Política de cancelación:</h5>
              <span>Descripcion</span>
              <textarea
                value={cancelacion}
                onChange={(event) => {
                  event.target.parentElement.classList.remove("error");
                  setErrorCancelacion("");
                  setCancelacion(event.target.value);
                }}
              />
              <p className="politicas-parrafo">{errorCancelacion}</p>
            </label>
          </fieldset>
          <h2>Agregar Imagenes</h2>
          <fieldset className=" atributos">
            {imagenes.map((img, index) => (
              <div key={index} className="atributo-container inputs-delete">
                <label>
                  <input
                    type="text"
                    value={img.url_imagen}
                    onChange={(event) => handleImgChange(index, event)}
                    name="url_imagen"
                    className="atributo-container-select"
                  />
                </label>

                <FontAwesomeIcon
                  icon={faSquareXmark}
                  style={{ color: "#545776" }}
                  className="icono-agregar"
                  onClick={() => handleDeleteImg(index)}
                />
              </div>
            ))}
            <div className="atributo-container">
              <label className={`${errorImagen ? "error" : ""}`}>
                <input
                  type="text"
                  value={newImg.url_imagen}
                  placeholder="Instertar https://..."
                  onChange={(event) => {
                    handleNewImgChange(event);
                  }}
                  name="url_imagen"
                  className="atributo-container-select"
                />
                <p>{errorImagen}</p>
              </label>

              <FontAwesomeIcon
                icon={faSquarePlus}
                className="icono-agregar"
                style={{ color: "#1dbeb4" }}
                onClick={(event) => {
                  event.target.parentElement.classList.remove("error");
                  setErrorImagen("");
                  handleAddImg(event);
                }}
              />
            </div>
          </fieldset>
        </form>
        <button
          type="submit"
          form="producto-form"
          className="btn-crear-producto"
          id="submit-product"
          disabled={sendLoad}
        >
          {sendLoad ? (
            <CircularProgress
              sx={{ color: "#fff", marginTop: "5px" }}
              size="1.4rem"
            />
          ) : (
            "Crear"
          )}
        </button>
      </div>
      {showModal && (
        <Modal onClose={handleToggleDescription}>
          <CiudadForm handleCiudadCreada={(e) => handleCiudadCreada(e)} />
        </Modal>
      )}
    </div>
  );
};

export default ProductoForm;
