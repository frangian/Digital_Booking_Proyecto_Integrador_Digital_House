import React, { useEffect, useState } from 'react'
import ShareOutlinedIcon from '@mui/icons-material/ShareOutlined';
import FavoriteBorderOutlinedIcon from '@mui/icons-material/FavoriteBorderOutlined';
import FavoriteIcon from '@mui/icons-material/Favorite';
import { IconButton } from '@mui/material';

const ImgMobileCarousel = ({ imgList, shareObj, shareAcross }) => {

    const [like, setLike] = useState(false);
    const [selectedIndex, setSelectedIndex] = useState(0);
    const [load, setLoad] = useState(false);
    const [mainImg, setMainImg] = useState(0);

    useEffect(() => {
        const interval = setInterval(() => {
            selectNewImage(selectedIndex, imgList)
        }, 5000);
        return () => clearInterval(interval);
    })

    const selectNewImage = (index, images, next = true) => {
        setLoad(false)
        setTimeout(() => {
            const condition = next ? index < images.length - 1 : index > 0;
            const nextIndex = next ? condition ? index + 1 : 0 : condition ? index - 1 : images.length - 1;
            setMainImg(nextIndex);
            setSelectedIndex(nextIndex);
        }, 500) 
    }

    const previous = () => {
        selectNewImage(selectedIndex, imgList, false);
    }
    
    const next = () => {
        selectNewImage(selectedIndex, imgList);
    }

    return (
        <div className='mobile-carousel'>
            <div className='interacciones-mobile'>
                <IconButton 
                sx={{color: "white", position: "absolute", top: "3%", zIndex: "1", left: "1%"}}
                onClick={() => shareAcross(shareObj)}
                >
                    <ShareOutlinedIcon />
                </IconButton>
                <IconButton onClick={() => setLike(!like)} 
                sx={{color: "white", position: "absolute", top: "3%", zIndex: "1", left: "10%"}}>
                    {like ? <FavoriteIcon sx={{color: "red"}}/> : <FavoriteBorderOutlinedIcon />}
                </IconButton>
            </div>
            <div className="slider">
                <img src={imgList[mainImg]?.url_imagen} alt=""
                className={`${load ? "loaded" : ""}`}
                onClick={(e) => {
                    if (e.clientX < (window.innerWidth / 2) / 2) {
                        previous()
                    } else if (e.clientX > (window.innerWidth / 2) + (window.innerWidth / 2) / 2) {
                        next()
                    }
                }}
                onLoad={() => setLoad(true)}
                />
            </div>
            <p>{mainImg + 1}/{imgList.length}</p>
        </div>
    )
}

export default ImgMobileCarousel