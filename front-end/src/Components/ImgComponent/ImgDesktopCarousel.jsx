import React, { useState } from 'react'
import { Swiper, SwiperSlide } from "swiper/react";
import { Navigation, Thumbs } from "swiper";
import "swiper/css";
import "swiper/css/free-mode";
import "swiper/css/navigation";
import "swiper/css/thumbs";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faX } from '@fortawesome/free-solid-svg-icons'

const ImgDesktopCarousel = ({ imgList, open, closer }) => {

  const [thumbsSwiper, setThumbsSwiper] = useState()

  return (
    <div className={`desktop-carousel ${open ? "" : "oculto"}`}>
      <div className="carousel-container">
        <FontAwesomeIcon icon={faX} onClick={() => closer()} className="icono-x-carousel"/>
        <Swiper
        loop={true}
        spaceBetween={10}
        navigation={true}
        modules={[Navigation, Thumbs]}
        grabCursor={true}
        thumbs={{ swiper: thumbsSwiper && !thumbsSwiper.destroyed ? thumbsSwiper : null }}
        className='product-images-slider'
        >
          {
            imgList?.map((item) => (
              <SwiperSlide key={item.id}>
                <img src={item.url_imagen} alt={item.titulo} />
              </SwiperSlide>
            ))
          }
        </Swiper>
        <Swiper
        onSwiper={setThumbsSwiper}
        loop={true}
        spaceBetween={10}
        slidesPerView={4}
        modules={[Navigation, Thumbs]}
        className='product-images-slider-thumbs'
        >
          {
            imgList?.map((item) => (
              <SwiperSlide key={item.id}>
                <div>
                  <img src={item.url_imagen} alt={item.titulo} />
                </div>
              </SwiperSlide>
            ))
          }
        </Swiper>
      </div>
    </div>
  )
}

export default ImgDesktopCarousel