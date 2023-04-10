import React from "react";
import { useState, useEffect } from "react";
import axios from "axios";
import Box from "@mui/material/Box";
import MenuItem from "@mui/material/MenuItem";
import Paper from "@mui/material";
import FormControl from "@mui/material/FormControl";
import Select from "@mui/material/Select";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMapMarkerAlt } from "@fortawesome/free-solid-svg-icons";
import { DateRangePicker } from "rsuite";
import { CircularProgress } from "@mui/material";
import { API_URL } from "./Utils/api";
import moment from 'moment';
import 'moment/locale/es';

const Buscador = ({ onCiudadSeleccionada, isLoading, recomendadosRef }) => {
  const [ciudad, setCiudad] = useState("");
  const [dateRange, setDateRange] = useState([]);
  const [selectedDates, setSelectedDates] = useState(null);
  const [ciudades, setCiudades] = useState([]);
  const [ciudadId, setCiudadId] = useState(null);
  const [width, setWidth] = useState(window.innerWidth);

  moment.locale('es');

  const handleChange = (event) => {
    setCiudad(event.target.value);
  };

  const handleBuscarClick = () => {
    onCiudadSeleccionada(ciudadId, selectedDates);
  };

  const handleDateChange = (value) => {
    setDateRange(value);
    setSelectedDates(null);
    if(value !== null) {
      
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
    }
    
    
  };

  /* ----------------------------------- COMPLETAR CON URL DE LA API PARA OBTENER LISTADO DE CIUDADES ---------------------------------- */
  useEffect(() => {
    window.addEventListener("resize", resize);
    axios
      .get(`${API_URL}/ciudad`)
      .then((response) => {
        const data = response.data;
        setCiudades(data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  const disabledDate = (date) => {
    return date < new Date();
  };


  const resize = () => {
    setWidth(window.innerWidth);
  }

  const renderCalendar = () => {
    if (width <= 960 && width > 790) {
      return (
        <DateRangePicker
          value={dateRange}
          onClean={()=> setDateRange([])}
          onChange={(e)=> handleDateChange(e.target.value)}
          placeholder="Check-in Check-out"
          className="date-picker"
          size="lg"
          disabledDate={disabledDate}
          placement="bottomEnd"
          ranges={[]}
          locale="es"
          renderValue={(value, format) => {
            return (
              <div className="fechas-input">
                {`${moment(value[0]).format("D [de] MMMM [de] YYYY")} - ${moment(value[1]).format("D [de] MMMM [de] YYYY")}`}
              </div>
            );
          }}
        />
      )
    } else if (width <= 790) {
      return (
        <DateRangePicker
          value={dateRange}
          onClean={()=> setDateRange([])}
          onChange={(e)=> handleDateChange(e.target.value)}
          placeholder="Check-in Check-out"
          className="date-picker"
          size="lg"
          disabledDate={disabledDate}
          showOneCalendar
          ranges={[]}
          locale="es"
          renderValue={(value, format) => {
            return (
              <div className="fechas-input">
                {`${moment(value[0]).format("D [de] MMMM [de] YYYY")} - ${moment(value[1]).format("D [de] MMMM [de] YYYY")}`}
              </div>
            );
          }}
        />
      )
    } else {
      return (
        <DateRangePicker
          value={dateRange}
          onClean={()=> setDateRange([])}
          onChange={(e)=> handleDateChange(e)}
          placeholder="Check-in Check-out"
          className="date-picker"
          size="lg"
          disabledDate={disabledDate}
          ranges={[]}
          locale="es"
          renderValue={(value, format) => {
            return (
              <div className="fechas-input">
                {`${moment(value[0]).format("D [de] MMMM [de] YYYY")} - ${moment(value[1]).format("D [de] MMMM [de] YYYY")}`}
              </div>
            );
          }}
        />
      )
    }
  }

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
        className="form-buscador-box"
          
        >
          <FormControl fullWidth>
            <Select
              value={ciudad}
              onChange={handleChange}
              displayEmpty
              sx={{
                "& .MuiOutlinedInput-notchedOutline": {
                  borderColor: "transparent",
                },
                height: "42px",
              }}          
            >
              <MenuItem value=""  onClick={() => setCiudadId(0)}>
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
        

        
       {renderCalendar()}
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
