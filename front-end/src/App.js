import React from 'react';
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Layout from './Components/Layout';
import { routes } from './Components/Utils/routes';
import Producto from './Rutas/Producto';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route element={<Layout/>}>
          {routes.map(({ id, path, Element }) => (<Route key={id} path={path} element={<Element/>}/>))}
          <Route key="4" path="/product" element={<Producto/>}/>
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
