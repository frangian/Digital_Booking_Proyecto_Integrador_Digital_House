import axios from "axios";

const API_URL = "http://localhost:8080";

export const getProductosRecomendados = async (categoriaSeleccionada, ciudadSeleccionada, dates) => {
  let url = API_URL + "/producto/random";
  let params = {};

  if (categoriaSeleccionada) {
    url = API_URL + `/producto/categoria/${categoriaSeleccionada}`;
  }
  if (ciudadSeleccionada && dates && dates.length === 2) {
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
    url = API_URL + `/producto/ciudad/${ciudadSeleccionada}`;
  } else if (dates && dates.length === 2) {
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