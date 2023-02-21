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

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faMapMarkerAlt } from '@fortawesome/free-solid-svg-icons';

const Buscador = () => {
  const [value, setValue] = React.useState([null, null]);
  const [ciudad, setCiudad] = React.useState("");

  const handleChange = (event) => {
    setCiudad(event.target.value);
  };

  return (
    <div className="buscador-container">
      <h2>Busca ofertas en hoteles, casas y mucho más</h2>
      <form action="" className="form-buscador">
        <Box sx={{ minWidth: 120, width: "30vw", bgcolor: "white" }} >
          <FormControl fullWidth >
            <InputLabel id="demo-simple-select-label" ><FontAwesomeIcon icon={faMapMarkerAlt} className="icono" />¿A donde vamos?</InputLabel>
            <Select
              labelId="demo-simple-select-label"
              id="demo-simple-select"
              value={ciudad}
              label="ciudad"
              onChange={handleChange}
            >
              {data.cities.map((ciudad) => (
                <MenuItem key={ciudad.id} value={ciudad.label}>
                  {ciudad.label}
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
              <React.Fragment>
                <TextField {...startProps} sx={{width: "15vw", bgcolor: "white"}}/>
                <TextField {...endProps} sx={{width: "15vw", bgcolor: "white"}}/>
              </React.Fragment>
            )}
          />
        </LocalizationProvider>
        <button className="ver-mas-btn" id="btn-buscar">Buscar</button>
      </form>
    </div>
  );
};

export default Buscador;
