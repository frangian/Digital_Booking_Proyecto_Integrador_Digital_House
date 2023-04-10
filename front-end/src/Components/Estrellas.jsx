import React from 'react'
import StarOutlineIcon from '@mui/icons-material/StarOutline';
import StarRateIcon from '@mui/icons-material/StarRate';

const Estrellas = ({ stars, colorStar, sizeStar }) => {
  return (
    <div>
        {
            [ ...new Array(5)].map((star, index) =>{
                return index < stars ? <StarRateIcon key={index} sx={{color : colorStar, fontSize: sizeStar}}/> : <StarOutlineIcon key={index} sx={{fontSize:sizeStar}}/> 
            })
        }
    </div>
  )
}

export default Estrellas