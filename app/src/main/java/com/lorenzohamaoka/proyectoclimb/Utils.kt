package com.lorenzohamaoka.proyectoclimb

import android.widget.ImageView
import com.bumptech.glide.Glide
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

class Utils {

    companion object{
        fun distance(
            lat1: Double,
            lon1: Double,
            lat2: Double,
            lon2: Double
        ): Int {
            val theta = lon1 - lon2
            var dist = (sin(deg2rad(lat1))
                    * sin(deg2rad(lat2))
                    + (cos(deg2rad(lat1))
                    * cos(deg2rad(lat2))
                    * cos(deg2rad(theta))))
            dist = acos(dist)
            dist = rad2deg(dist)
            dist *= 60 * 1.1515 * 1.609344
            return dist.toInt()
        }

        private fun deg2rad(deg: Double): Double {
            return deg * Math.PI / 180.0
        }

        private fun rad2deg(rad: Double): Double {
            return rad * 180.0 / Math.PI
        }

        fun setImage(referencia: String?, context: InfoZonasFragment, imageView: ImageView){

            Glide.with(context)
                .load(referencia)
                .into(imageView)
            // [END storage_load_with_glide]
        }
    }



}