export const validarMail = (mail) => {
    const regex = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    if (mail.match(regex)) {
        return true;
    } else return false;
}

export const validarPassword = (pass) => {
    if (pass.length >= 6) {
        return true;
    } else return false;
}

export const confirmarPassword = (password, repeat) => {
    if (password === repeat) {
        return true;
    } else return false;
}

export const campoRequerido = (text) => {
    console.log(text);
    if (text.length === 0) {
        return false;
    } else return true;
}

export const normalizarInput = (input) => {
    return input.toLowerCase().trim();
}
