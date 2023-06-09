import { createContext, useReducer } from "react";

export const initialState = {nombre: "", apellido: "", mail: "", pass: "", logged: false};

export const ContextGlobal = createContext();

const reducerFunction = (state, action) => {
  switch (action.type) {
    case "register":
      return action.payload;
    default:
      return state;
  }
}

export const ContextProvider = ({ children }) => {

    const [state, dispatch] = useReducer(reducerFunction, initialState)
  
    const store = {
      state, 
      dispatch, 
    }
  
    return (
      <ContextGlobal.Provider value={store}>
        {children}
      </ContextGlobal.Provider>
    );
  };