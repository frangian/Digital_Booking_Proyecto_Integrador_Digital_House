import { createContext, useReducer } from "react";

export const initialState = {
  user: {
    nombre: "Juan",
    apellido: "Gomez",
    reservas: [],
    favoritos: [],
  },
  logged: false,
  map: true,
  location: "",
  admin: true
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
    default:
      return state;
  }
}

export const ContextProvider = ({ children }) => {

    const [state, dispatch] = useReducer(reducerFunction, initialState);
  
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