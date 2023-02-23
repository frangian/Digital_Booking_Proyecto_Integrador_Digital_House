import React from "react";
import { useState } from "react";
import data from "../Components/Utils/ciudades.json";

import TextField from "@mui/material/TextField";
import Box from "@mui/material/Box";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import Select from "@mui/material/Select";


import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMapMarkerAlt } from "@fortawesome/free-solid-svg-icons";

import { DateRangePicker } from 'rsuite';


const Buscador = () => {
  const [ciudad, setCiudad] = useState("");
  const [dateRange, setDateRange] = useState([]);
  

  const handleChange = (event) => {
    setCiudad(event.target.value);
  };

  const handleDateChange = (value) => {
    setDateRange(value);
  };

  return (
    <div className="buscador-container">
      <h2>Busca ofertas en hoteles, casas y mucho más</h2>
      <form action="" className="form-buscador" onSubmit={(e) => {e.preventDefault()}}>
        <Box
          sx={{
            bgcolor: "white",
            minWidth: 200,
            width: {  xs: "100%", sm: 200, md: 400, lg: 500, xl: 500},
            height: "41px",
            marginRight: "1vw",
            borderRadius: "5px",
          }}
        >
          <FormControl fullWidth>
            <Select
              value={ciudad}
              onChange={handleChange}
              displayEmpty
              sx={{
                '& .MuiOutlinedInput-notchedOutline': {
                  borderColor: 'transparent',
                },
                height: "42px",
              }}
            >
              <MenuItem value="" sx={{ display: "none" }}>
                <FontAwesomeIcon icon={faMapMarkerAlt} className="icono" />{" "}
                <span>¿A donde vamos?</span>
              </MenuItem>
              {data.cities.map((ciudad, index) => (
                <MenuItem
                  key={ciudad.id}
                  value={ciudad.label}
                  sx={
                    index !== data.cities.length - 1
                      ? {
                          borderBottom: 1,
                          borderColor: "rgba(29, 190, 180, 1)",
                        }
                      : { borderBottom: 0 }
                  }
                >
                  <FontAwesomeIcon
                    icon={faMapMarkerAlt}
                    className="icono-select blanco"
                  />
                  {ciudad.label}, {ciudad.pais}
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
    />

        {/* <LocalizationProvider
          dateAdapter={AdapterDayjs}
          localeText={{ start: "Check-in", end: "Check-out" }}
        >
          <DateRangePicker
            value={value}
            onChange={(newValue) => {
              setValue(newValue);
            }}
            renderInput={(startProps, endProps) => (
              <React.Fragment >
                <TextField
                  {...startProps}
                  sx={{
                    width: { xs: "100%", sm: 100, md: 150, lg: 150, xl: 200},
                    marginBottom: "10px",
                    bgcolor: "white",
                    borderRadius: "5px 0 0 5px",
                  }}
                />
                <TextField
                  {...endProps}
                  sx={{
                    width: { xs: "100%", sm: 100, md: 150, lg: 150, xl: 200},
                    marginBottom: "10px",
                    marginRight: { xs: 0, sm: "1vw", md: "1vw", lg: "1vw", xl: "1vw"},
                    bgcolor: "white",
                    borderRadius: "0 5px 5px 0",
                  }}
                />
              </React.Fragment>
            )}
          />
        </LocalizationProvider> */}
        
        <button className="ver-mas-btn" id="btn-buscar">
          Buscar
        </button>
      </form>
    </div>
  );
};

export default Buscador;
