import React from "react";
import { useState, useEffect } from "react";
import axios from "axios";
import Box from "@mui/material/Box";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import Select from "@mui/material/Select";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMapMarkerAlt } from "@fortawesome/free-solid-svg-icons";
import { DateRangePicker } from "rsuite";
import { CircularProgress } from "@mui/material";
import { API_URL } from "./Utils/api";

const Buscador = ({ onCiudadSeleccionada, isLoading }) => {
  const [ciudad, setCiudad] = useState("");
  const [dateRange, setDateRange] = useState([]);
  const [selectedDates, setSelectedDates] = useState(null);
  const [ciudades, setCiudades] = useState([]);
  const [ciudadId, setCiudadId] = useState(null);
  // const useStyles = makeStyles((theme) => ({
  //   select: {
  //     width: "100%"
  //   }
  // }))
  // const classes = useStyles();

  const handleChange = (event) => {
    setCiudad(event.target.value);
  };

  const handleBuscarClick = () => {
    onCiudadSeleccionada(ciudadId, selectedDates);
  };

  const handleDateChange = (value) => {
    setDateRange(value);
    const fechaInicial = value[0]
      .toLocaleDateString("en-CA")
      .split("/")
      .reverse()
      .join("-");
    const fechaFinal = value[1]
      .toLocaleDateString("en-CA")
      .split("/")
      .reverse()
      .join("-");
    setSelectedDates([fechaInicial, fechaFinal]);
  };

  /* ----------------------------------- COMPLETAR CON URL DE LA API PARA OBTENER LISTADO DE CIUDADES ---------------------------------- */
  useEffect(() => {
    axios
      .get(`${API_URL}/ciudad`)
      .then((response) => {
        const data = response.data;
        setCiudades(data);
        // console.log(data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  const disabledDate = (date) => {
    return date < new Date();
  };

  return (
    <div className="buscador-container">
      <h2>Busca ofertas en hoteles, casas y mucho más</h2>
      <form
        action=""
        className="form-buscador"
        onSubmit={(e) => {
          e.preventDefault();
        }}
      >
        <Box
          sx={{
            bgcolor: "white",
            minWidth: 200,
            width: { xs: "100%", sm: 200, md: 400, lg: 500, xl: 500 },
            height: "41px",
            marginRight: "1vw",
            borderRadius: "5px",
          }}
        >
          <FormControl fullWidth>
            <Select
              value={ciudad}
              // className={classes.select}
              onChange={handleChange}
              displayEmpty
              sx={{
                "& .MuiOutlinedInput-notchedOutline": {
                  borderColor: "transparent",
                },
                height: "42px",
              }}
              
            >
              <MenuItem value="" sx={{ display: "none" }}>
                <FontAwesomeIcon icon={faMapMarkerAlt} className="icono" />{" "}
                <span>¿A donde vamos?</span>
              </MenuItem>
              {ciudades.map((ciudad, index) => (
                <MenuItem
                  key={ciudad.id}
                  value={ciudad.nombre}
                  sx={
                    index !== ciudades.length - 1
                      ? {
                          borderBottom: 1,
                          borderColor: "rgba(29, 190, 180, 1)",
                        }
                      : { borderBottom: 0 }
                  }
                  onClick={() => setCiudadId(ciudad.id)}
                >
                  <FontAwesomeIcon
                    icon={faMapMarkerAlt}
                    className="icono-select blanco"
                  />
                  {ciudad.nombre}, {ciudad.pais}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Box>

        <DateRangePicker
          value={dateRange}
          onChange={handleDateChange}
          placeholder="Check-in Check-out"
          className="date-picker"
          size="lg"
          disabledDate={disabledDate}
        />
        <button
          className="ver-mas-btn"
          id="btn-buscar"
          onClick={handleBuscarClick}
        >
          {isLoading ? <CircularProgress sx={{ color: "white" }} size="1rem" /> : "Buscar"}
        </button>
      </form>
    </div>
  );
};

export default Buscador;
