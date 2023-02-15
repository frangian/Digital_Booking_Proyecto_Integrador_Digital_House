let mainGreen = "#1DBEB4";
let mainDarkGray = "#383B58";
let mainLightGray = "#545776";
let mainWhite = "#F3F1ED";

export const navbarContainer = {
    backgroundColor: mainWhite,
    display: "flex",
    height: "12vh",
    justifyContent: "center"
} 

export const navbar = {
    display: "flex",
    width: "100%",
    alignItems: "center",
    justifyContent: "space-between"
} 

export const logoContainer = {
    display: "flex",
    alignItems: "end",
    color: mainLightGray,
    fontStyle: "italic",
    width: "30%",
}

export const btnContainer = {
    display: { xs: 'none', sm: 'flex' },
    width: "70%",
    justifyContent: "end",

}

export const btnNavbar = {
    color: mainGreen,
    border: `1px solid ${mainGreen}`,
    marginRight: "20px",
    maxHeight: "40px",
    minWidth: {md: "164px", lg: "206px"},
    "&:hover": {backgroundColor: mainGreen, color: mainWhite, borderColor: mainGreen}
}

export const menuTop = {
    backgroundColor: mainGreen,
    color: mainWhite,
    height: "25%",
    display: "flex",
    flexDirection: "column",
    width: "100%",
    justifyContent: "space-between",
    textAlign: "right"
}

export const menuBottom = {
    display: "flex",
    justifyContent: "end",
}

export const socialNetworkLogo = {
    width: "24px",
    margin: "0 10px",
    cursor: "pointer",
}



