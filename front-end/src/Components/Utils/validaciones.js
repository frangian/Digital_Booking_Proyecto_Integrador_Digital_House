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
    if (text.length === 0) {
        return false;
    } else return true;
}

export const normalizarMail = (mail) => {
    return mail.toLowerCase().trim();
}

export const normalizarNombre = (nombre) => {
    const nombreNormalizado = nombre.charAt(0).toUpperCase() + nombre.slice(1)
    return nombreNormalizado.trim();
}

export const validarUrl = (url, texto) => {
    return url.includes(texto);
}