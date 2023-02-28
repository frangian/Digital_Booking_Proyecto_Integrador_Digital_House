import { fireEvent, render, screen } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect'
import { ContextProvider } from './Components/Utils/globalContext';
import { BrowserRouter } from 'react-router-dom';
import Register from './Rutas/Register'
import Login from './Rutas/Login';
import Home from './Rutas/Home';

test('render register', () => {
    
    render(
      <ContextProvider>
        <BrowserRouter>
          <Register />
        </BrowserRouter>
      </ContextProvider>
    )

    const btn = screen.getByRole('button', {
      name: /crear cuenta/i
    })

    expect(btn).toHaveTextContent("Crear cuenta");

});

test('render login', () => {

    render(
      <ContextProvider>
        <BrowserRouter>
          <Login />
        </BrowserRouter>
      </ContextProvider>
    )

    const btn = screen.getByRole('button', {
      name: /ingresar/i
    })

    expect(btn).toBeInTheDocument();

})

test('render home', () => {

  render(
    <ContextProvider>
      <BrowserRouter>
        <Home />
      </BrowserRouter>
    </ContextProvider>
  )

  const btn = screen.getByRole('button', {
    name: /buscar/i
  })

  expect(btn).toBeInTheDocument();

})

describe('Registro', () => {

  beforeEach(() => {
      render(
        <ContextProvider>
          <BrowserRouter>
            <Register />
          </BrowserRouter>
        </ContextProvider>
      )
  })
  
  it('Submit incorrecto', async () => {
  
    const btn = screen.getByRole('button', {
      name: /crear cuenta/i
    })

    fireEvent.click(btn);

    const error = await screen.findAllByText(/este campo es obligatorio/i);
  
    expect(error[0]).toBeInTheDocument();

  })

  it('Formato mail incorrecto', async () => {

    const inputMail = screen.getByRole('textbox', {
      name: /correo electrónico/i
    })

    const btn = screen.getByRole('button', {
      name: /crear cuenta/i
    })

    fireEvent.change(inputMail, {
      target: { value: "bautigmail.com" }
    })  

    fireEvent.click(btn);

    const error = await screen.findByText(/el formato del mail no es válido/i);

    expect(error).toBeInTheDocument();

  })

})

describe('Login', () => {

  beforeEach(() => {
      render(
        <ContextProvider>
          <BrowserRouter>
            <Login />
          </BrowserRouter>
        </ContextProvider>
      )
  })

  it('Submit incorrecto', async () => {
  
    const btn = screen.getByRole('button', {
      name: /ingresar/i
    })

    fireEvent.click(btn);

    const error = await screen.findByText(/por favor ingrese un mail y una contraseña/i);
  
    expect(error).toBeInTheDocument();

  })

  it('Credenciales no coinciden', async () => {

    const inputMail = screen.getByRole('textbox', {
      name: /correo electrónico/i
    })
  
    const inputPass = screen.getByLabelText(/contraseña/i);

    const btn = screen.getByRole('button', {
      name: /ingresar/i
    })

    fireEvent.change(inputMail, {
      target: { value: "bauti@gmail.com" }
    }) 

    fireEvent.change(inputPass, {
      target: { value: "785496" }
    })

    fireEvent.click(btn);

    const error = await screen.findByText(/las credenciales ingresedas no coinciden/i);
  
    expect(error).toBeInTheDocument();

  })

})
