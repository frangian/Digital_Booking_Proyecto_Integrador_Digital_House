import React, { useState } from 'react'
import Skeleton from 'react-loading-skeleton';
import ImgDesktopCarousel from './ImgDesktopCarousel'
import ImgMobileCarousel from './ImgMobileCarousel';

const ImgContainer = ({ imgList, isLoading, shareObj, shareAcross }) => {

    const [openCarousel, setOpenCarousel] = useState(false);

    const handlerCloseCarousel = () => {
        setOpenCarousel(false);
    }

    return (
        <div className='images-container'>
            <div className={`product-images`}>
                {
                isLoading ? (imgList?.map((imagen, i) => {
                    if (i < 4) {
                        return (
                            <div className="imagenes" key={imagen.url_imagen + i}>
                                <img src={imagen.url_imagen} alt={imagen.titulo} />
                            </div>
                        )
                    } else if (i === 4) {
                        return (
                            <div className="imagenes" key={imagen.url_imagen + i}>
                                <img src={imagen.url_imagen} alt={imagen.titulo} />
                                <p onClick={() => setOpenCarousel(true)}>Ver más</p>
                            </div>
                        )
                    }
                })) :
                <Skeleton className={`product-images`}/>
                }
            </div>
            <ImgDesktopCarousel 
            imgList={imgList} 
            open={openCarousel} 
            closer={handlerCloseCarousel}
            />
            <ImgMobileCarousel 
            imgList={imgList}
            shareObj={shareObj} shareAcross={shareAcross}
            />
        </div>
    )
}

export default ImgContainer