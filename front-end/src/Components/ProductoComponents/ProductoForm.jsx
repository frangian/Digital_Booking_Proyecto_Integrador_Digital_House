import React, { useState, useEffect } from "react";
import CircularProgress from "@mui/material/CircularProgress";
import { API_URL } from "../Utils/api";
import axios from "axios";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faSquarePlus, faSquareXmark } from "@fortawesome/free-solid-svg-icons";
import { tituloXIdServicio, findCategoria } from "../Utils/utils";
import { campoRequerido, validarUrl } from "../Utils/validaciones";
import CiudadForm from "../Vistas/CiudadForm";
import Modal from "../Modal";
import AtributoForm from "./AtributoForm";
import AddImagenForm from "./AddImagenForm";

import { elegirServicio, findCategoriaNombre } from "../Utils/utils.js";
import CardProducto from "../CardProducto";

const ProductoForm = ({
  handleConfirmacion,
  loading,
  handleLoading,
  producto,
}) => {
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
  const [datos, setDatos] = useState({
    title: "",
    shortDescription: "",
    puntuacion: "",
    category: "",
    caracteristicasArray: "",
    url_imagen: "",
    characteristics: "",
    desUbicacion: "",
  });

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
      setTitulo(producto.titulo);
      setDescripcion(producto.descripcion_producto);
      setCiudad({ id: producto.ciudad.id });
      setDireccion(producto.direccion);
      setPuntuacion(producto.puntuacion);
      setUrlMapa(producto.url_ubicacion);
      setDesUbicacion(producto.descripcion_ubicacion);
      setNormas(producto.normas);
      setSeguridad(producto.seguridad);
      setCancelacion(producto.cancelacion);
      setImagenes(producto.imagenes);
      setAttributes(
        producto.caracteristicas.map((res) => ({ id: res.titulo }))
      );
    }
  }, []);

  useEffect(() => {
    fetchCategories();
    fetchCities();
    fetchCaracteristicas();
  }, [ciudadCreada]);

  useEffect(() => {
    if (producto) {
      setCategoria({ id: findCategoria(categories, producto.categoria) });
    }
  }, [categories]);

  function fetchCategories() {
    axios
      .get(`${API_URL}/categoria`)
      .then((response) => {
        setCategories(response.data);
      })
      .catch((error) => {
        setError("Error al obtener categorías");
      });
  }

  function fetchCaracteristicas() {
    axios
      .get(`${API_URL}/caracteristica`)
      .then((response) => {
        setCaracteristicasArray(response.data);
      })
      .catch((error) => {
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
      let jwt = localStorage.getItem("jwt");

      if (!producto) {
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

        axios
          .post(`${API_URL}/producto`, data, {
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${jwt}`,
            },
          })
          .then((response) => {
            setSendLoad(false);
            handleConfirmacion();
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

        axios
          .put(`${API_URL}/producto`, data, {
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${jwt}`,
            },
          })
          .then((response) => {
            setSendLoad(false);
            handleConfirmacion();
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
  // MANEJO DE CAMBIOS EN EL COMPONENTE
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
  function handleDeleteImg(event, index) {
    event.preventDefault()
    setImagenes((prevImg) => {
      const newImg = [...prevImg];
      newImg.splice(index, 1);
      console.log(index);
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

  const manejarCambios = (event) => {
    const { name, value } = event.target;
    setDatos({
      ...datos,
      [name]: value,
    });
  };

  return (
    <div className="crear-producto-container">
      <h1>
        {producto
          ? `Actualizar el producto con id: ${producto.id}`
          : "Crear un producto"}{" "}
        <a
          className="link-demo"
          href="https://youtu.be/kfvH1oHYTLE"
          target="e_blank"
        >
          {" "}
          (Link video demo)
        </a>
      </h1>

      <div className="producto-form-container">
        <form id="producto-form" onSubmit={(event) => handleSubmit(event)}>
          <fieldset className="info-producto-container">
            <label className={` ${errorTitulo ? "error" : ""}`}>
              <span>Nombre del producto:</span>
              <input
                type="text"
                value={titulo}
                name="title"
                onChange={(event) => {
                  event.target.parentElement.classList.remove("error");
                  setErrorTitulo("");
                  setTitulo(event.target.value);
                  manejarCambios(event);
                }}
              />
              <p>{errorTitulo}</p>
            </label>
            <label className={` ${errorCategoria ? "error" : ""}`}>
              <span>Categoría:</span>
              <select
                value={categoria.id}
                name="category"
                onChange={(event) => {
                  event.target.parentElement.classList.remove("error");
                  setErrorCategoria("");
                  setCategoria({ id: parseInt(event.target.value) });
                  manejarCambios(event);
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
                  manejarCambios(event);
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
                value={ciudad.id}
                onChange={(event) => {
                  event.target.parentElement.classList.remove("error");
                  setErrorCiudad("");
                  setCiudad({ id: parseInt(event.target.value) });
                  manejarCambios(event);
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
                name="desUbicacion"
                onChange={(event) => {
                  event.target.parentElement.classList.remove("error");
                  setErrorDesUbicacion("");
                  setDesUbicacion(event.target.value);
                  manejarCambios(event);
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
                name="shortDescription"
                onChange={(event) => {
                  event.target.parentElement.classList.remove("error");
                  setErrorDescripcion("");
                  setDescripcion(event.target.value);
                  manejarCambios(event);
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
                  manejarCambios(event);
                }}
              ></input>
              <p>{errorPuntuacion}</p>
            </label>
          </div>

          {/* AGREGUE LOS ICONOS AL LADO DEL H2 */}
          <h2>
            Agregar atributos{" "}
            <span>
              {attributes.map((attribute) => (
                <span className="icono-servicio">
                  {elegirServicio(attribute.id, "#1dbeb4")}
                </span>
              ))}
            </span>
          </h2>

          <AtributoForm
            attributes={attributes}
            handleAttributeChange={handleAttributeChange}
            handleDeleteAttribute={handleDeleteAttribute}
            errorCaracteristica={errorCaracteristica}
            caracteristica={caracteristica}
            caracteristicasArray={caracteristicasArray}
            handleChangeCaracteristica={handleChangeCaracteristica}
            setErrorCaracteristica={setErrorCaracteristica}
            handleAddAttribute={handleAddAttribute}
            manejarCambios={manejarCambios}
          />
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
          <AddImagenForm
            imagenes={imagenes}
            handleImgChange={handleImgChange}
            handleDeleteImg={handleDeleteImg}
            errorImagen={errorImagen}
            newImg={newImg}
            handleNewImgChange={handleNewImgChange}
            setErrorImagen={setErrorImagen}
            handleAddImg={handleAddImg}
            manejarCambios={manejarCambios}
          />

          {/* AGREGUE IMAGENES VISTAS */}
          {imagenes.length !== 0 ? (
            <div className="seccion-imagenes-crear-producto">
              {imagenes.map((img, i) => (
                <div className="seccion-imagenes-crear-producto-imagen">
                  {/* <h4 className="nombre-imagen">{img.titulo}</h4> */}
                  <img src={img.url_imagen} alt="imagen" />
                  <button className="verde" onClick={(event) => handleDeleteImg(event, i)}>
                    X
                  </button>
                </div>
              ))}
            </div>
          ) : (
            <></>
          )}
        </form>
        <div className="card-producto-creado">
          {!producto && Object.values(datos).some(valor => !!valor) ? (
            <CardProducto
              title={datos.title}
              shortDescription={datos.shortDescription}
              puntuacion={datos.puntuacion}
              category={findCategoriaNombre(categories, datos.category)}
              imagen={imagenes[0]?.url_imagen}
              characteristics={attributes.map((item, index) => {
                return {
                  id: index + 1,
                  titulo: item.id,
                };
              })}
              reserva={false}
              location={datos.desUbicacion}
              cardPrueba={true}
            />
          ) : (
            <></>
          )}
        </div>

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
          ) : producto ? (
            "Actualizar"
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
