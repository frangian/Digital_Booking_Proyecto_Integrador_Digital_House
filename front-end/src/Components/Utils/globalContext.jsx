import { createContext, useReducer } from "react";

export const initialState = {
  user: {
    nombre: "Juan",
    apellido: "Gomez",
    reservas: [],
    favoritos: [],
    usuarioRole: false
  },
  logged: false,
  map: true,
  location: "",
};



export const ContextGlobal = createContext();

const reducerFunction = (state, action) => {
  switch (action.type) {
    case "register":
      return action.payload;
    case "ADD_FAVORITE":
      const newUser = {
        ...state.user,
        favoritos: [...state.user.favoritos, action.payload]
      }
      return {
        ...state,
        user: newUser
      };
    case "REMOVE_FAVORITE":
      const newFavorites = state.user.favoritos.filter(
        fav => fav.id !== action.payload
      )
      const updatedUser = {
        ...state.user,
        favoritos: newFavorites
      }
      return {
        ...state,
        user: updatedUser
      }
    case "REMOVE_RESERVA":
      const id = action.payload.id;
      console.log(id);
      const newReservas = state.user.reservas.filter(reserva => reserva.id !== id);
      console.log(newReservas);
      return { ...state, user: { ...state.user, reservas: newReservas } }
    default:
      return state;
  }
}

export const ContextProvider = ({ children }) => {

    const [state, dispatch] = useReducer(reducerFunction, initialState);

    const removeReserva = (id) => {
      dispatch({ type: "REMOVE_RESERVA", payload: { id } });
    }
  
    const store = {
      state, 
      dispatch, 
      removeReserva
    }
  
    return (
      <ContextGlobal.Provider value={store}>
        {children}
      </ContextGlobal.Provider>
    );
  };