import * as React from "react";
import data from "../Components/Utils/ciudades.json";

import TextField from "@mui/material/TextField";
import Box from "@mui/material/Box";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import Select from "@mui/material/Select";
import { LocalizationProvider } from "@mui/x-date-pickers-pro";
import { AdapterDayjs } from "@mui/x-date-pickers-pro/AdapterDayjs";
import { DateRangePicker } from "@mui/x-date-pickers-pro/DateRangePicker";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMapMarkerAlt } from "@fortawesome/free-solid-svg-icons";

const Buscador = () => {
  const [value, setValue] = React.useState([null, null]);
  const [ciudad, setCiudad] = React.useState("");

  const handleChange = (event) => {
    setCiudad(event.target.value);
  };

  return (
    <div className="buscador-container">
      <h2>Busca ofertas en hoteles, casas y mucho más</h2>
      <form action="" className="form-buscador" onSubmit={(e) => {e.preventDefault()}}>
        <Box
          sx={{
            minWidth: 120,
            bgcolor: "white",
            width: { sm: "100%", md: "100%", xl:600},
            marginBottom: "10px",
            borderRadius: "5px",
            
          }}
        >
          <FormControl fullWidth>
            <Select
              value={ciudad}
              onChange={handleChange}
              displayEmpty
              inputProps={{ "aria-label": "Without label" }}
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
                    className="icono blanco"
                  />
                  {ciudad.label} <br /> {ciudad.pais}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Box>

        <LocalizationProvider
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
                    width: { xs: "100%", sm: "100%", md: "100%", lg:"100%", xl:250},
                    marginBottom: "10px",
                    bgcolor: "white",
                    borderRadius: "5px 0 0 5px",
                  }}
                />
                <TextField
                  {...endProps}
                  sx={{
                    width: { xs: "100%", sm: "100%", md: "100%", lg:"100%", xl:250},
                    marginBottom: "10px",
                    bgcolor: "white",
                    borderRadius: "0 5px 5px 0",
                  }}
                />
              </React.Fragment>
            )}
          />
        </LocalizationProvider>
        <button className="ver-mas-btn" id="btn-buscar">
          Buscar
        </button>
      </form>
    </div>
  );
};

export default Buscador;
