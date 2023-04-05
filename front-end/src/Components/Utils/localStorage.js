export const getFavFromStorage = () => {
    const localData = localStorage.getItem("favs");
    return localData ? JSON.parse(localData) : [];
};

export const setFavInStorage = async (alojamiento) => {
    const storageFavs = getFavFromStorage();
    const isFavOnList = storageFavs.filter(fav => {
        return fav.id === alojamiento.id
    });
    if (isFavOnList.length === 0) {
        // await new Promise(resolve => setTimeout(resolve, 1000));
        storageFavs.push(alojamiento)
        localStorage.setItem("favs", JSON.stringify(storageFavs));
        // alert("Alojamiento added successfully");
        return true;
    }
    else {
        // alert("Alojamiento already on the list");
        removeFavInStorage(alojamiento.id);
        return false;
    }
}

export const removeFavInStorage = async (identifier) => {
    const storageFavs = getFavFromStorage();
    const index = storageFavs.findIndex(fav => fav.id === Number(identifier));
    console.log(index, identifier);
    if (index !== -1) {
        // await new Promise(resolve => setTimeout(resolve, 1000));
        storageFavs.splice(index, 1);
        localStorage.setItem("favs", JSON.stringify(storageFavs));
        // alert("Alojamiento removed successfully");
    }
    else {
        // alert("An Error has ocurred");
    }
}
