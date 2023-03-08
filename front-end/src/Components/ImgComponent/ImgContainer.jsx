import React, { useState } from 'react'
import ImgDesktopCarousel from './ImgDesktopCarousel'
import ImgMobileCarousel from './ImgMobileCarousel';

const ImgContainer = ({ imgList }) => {

    const [openCarousel, setOpenCarousel] = useState(false);

    const handlerCloseCarousel = () => {
        setOpenCarousel(false);
    }

    return (
        <div className='images-container'>
            <div className={`product-images ${!openCarousel ? "" : "oculto"}`}>
                {imgList?.map((imagen, i) => {
                    if (i < 4) {
                        return (
                            <div className="imagenes">
                                <img src={imagen.url_imagen} alt="" />
                            </div>
                        )
                    } else if (i === 4) {
                        return (
                            <div className="imagenes">
                                <img src={imagen.url_imagen} alt="" />
                                <p onClick={() => setOpenCarousel(true)}>Ver más</p>
                            </div>
                        )
                    }
                })}
            </div>
            <ImgDesktopCarousel 
            imgList={imgList} 
            open={openCarousel} 
            closer={handlerCloseCarousel}
            />
            <ImgMobileCarousel 
            imgList={imgList}
            />
        </div>
    )
}

export default ImgContainer