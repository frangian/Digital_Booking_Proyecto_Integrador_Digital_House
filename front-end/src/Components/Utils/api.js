import axios from "axios";

export const API_URL = "http://localhost:8080";

export const getProductosRecomendados = async (categoriaSeleccionada, ciudadSeleccionada, dates) => {
  let url = API_URL + "/producto/random";
  let params = {};

  


  if (categoriaSeleccionada && ciudadSeleccionada && dates && dates.length === 2) {
    // Filtro por categoría, ciudad y fechas
    const checkIn = dates[0];
    const checkOut = dates[1];
    console.log("busco por categoria, ciudad y fechas" + dates);
    url = API_URL + `/producto/disponibles/categoriaciudadfecha`;
    params = {
      ciudadId: ciudadSeleccionada,
      categoriaId: categoriaSeleccionada,
      fechaInicial: checkIn,
      fechaFinal: checkOut,
    };
  } else if (categoriaSeleccionada && ciudadSeleccionada) {
    // Filtro por categoría y ciudad
    url = API_URL + `/producto/disponibles/categoriaciudad`;
    console.log("Busco por categoria y ciudad");
    params = {
      ciudadId: ciudadSeleccionada,
      categoriaId: categoriaSeleccionada
    }
  } else if (categoriaSeleccionada && dates && dates.length === 2) {
    // Filtro por categoría y fechas
    const checkIn = dates[0];
    const checkOut = dates[1];
    console.log("busco por categoria y fechas");
    url = API_URL + `/producto/disponibles/fechacategoria`;
    params = {
      categoriaId: ciudadSeleccionada,
      fechaInicial: checkIn,
      fechaFinal: checkOut,
    };
  } else if (categoriaSeleccionada) {
    // Filtro por categoría
    url = API_URL + `/producto/categoria/${categoriaSeleccionada}`;
    console.log("Busco por categoria");
  } else if (ciudadSeleccionada && dates && dates.length === 2) {
    // Filtro por ciudad y fechas
    const checkIn = dates[0];
    const checkOut = dates[1];
    console.log("busco por ciudad y fechas");
    url = API_URL + `/producto/disponibles/fechaciudad`;
    params = {
      ciudadId: ciudadSeleccionada,
      fechaInicial: checkIn,
      fechaFinal: checkOut,
    };
  } else if (ciudadSeleccionada) {
    // Filtro por ciudad
    url = API_URL + `/producto/ciudad/${ciudadSeleccionada}`;
    console.log("Busco por ciudad");
  } else if (dates && dates.length === 2) {
    // Filtro por fechas
    const checkIn = dates[0];
    const checkOut = dates[1];
    console.log("busco por fecha");
    url = API_URL + `/producto/disponibles/fecha`;
    params = {
      fechaInicial: checkIn,
      fechaFinal: checkOut,
    };
  }

  const urlParams = new URLSearchParams(params);
  const finalUrl = url + "?" + urlParams.toString();

  try {
    const response = await axios.get(finalUrl);
    return response.data;
  } catch (error) {
    console.log(error);
    throw error;
  }
};