import React, { useState } from 'react'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faX, faChevronRight, faChevronLeft } from '@fortawesome/free-solid-svg-icons'

const ImgDesktopCarousel = ({ imgList, open, closer }) => {

  const [mainImg, setMainImg] = useState(0);

  return (
    <div className={`desktop-carousel ${open ? "" : "oculto"}`}>
        <div className='carousel-container'>
          <div className="main-image">
            <FontAwesomeIcon icon={faX} className="icon icon-x" onClick={() => closer(false)}/>
            <div className={`left-arrow noselect ${mainImg === 0 ? "oculto" : ""} icon`}
            onClick={() => setMainImg(mainImg - 1)}
            >
              <FontAwesomeIcon icon={faChevronLeft}/>
            </div>
            <div className={`right-arrow noselect ${mainImg === imgList.length - 1 ? "oculto" : ""} icon`}
            onClick={() => setMainImg(mainImg + 1)}
            >
              <FontAwesomeIcon icon={faChevronRight}/>
            </div>
            <img src={imgList[mainImg]?.url_imagen} alt="" />
          </div>
          <div className="all-images-container">
            <p>{mainImg + 1}/{imgList.length}</p>
            <div className="all-images"
            style={{gridTemplateColumns: `repeat(${imgList?.length}, 1fr)`}}
            >
              {imgList?.map((url, i) => (
                <div key={i} className="carousel-bottom-img"
                style={i === mainImg ? {outline: "2px solid #000"} : {}}
                onClick={() => setMainImg(i)}
                >
                  <img src={url.url_imagen} alt=""/>
                </div>
              ))}
            </div>
          </div>
        </div>
    </div>
  )
}

export default ImgDesktopCarousel