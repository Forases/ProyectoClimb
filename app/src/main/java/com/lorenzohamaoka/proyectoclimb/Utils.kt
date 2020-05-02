package com.lorenzohamaoka.proyectoclimb

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.lorenzohamaoka.proyectoclimb.models.Sectores
import com.lorenzohamaoka.proyectoclimb.models.Vias
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

        fun setImage(referencia: String?, context: Context, imageView: ImageView){
            Glide.with(context)
                .load(referencia)
                .into(imageView)
        }

        fun getGradoVias(arrayGrados: MutableList<Vias>?): Array<Int>{
            var arrayNuevoGrados = arrayOf(0, 0, 0, 0, 0, 0)
            if(arrayGrados!!.size > 0){
                for(via in arrayGrados){
                    when(via.gradoVia?.get(0)){
                        '5' -> arrayNuevoGrados[1] = arrayNuevoGrados[1] + 1
                        '6' -> arrayNuevoGrados[2] = arrayNuevoGrados[2] + 1
                        '7' -> arrayNuevoGrados[3] = arrayNuevoGrados[3] + 1
                        '8' -> arrayNuevoGrados[4] = arrayNuevoGrados[4] + 1
                        '9' -> arrayNuevoGrados[5] = arrayNuevoGrados[5] + 1
                        else -> arrayNuevoGrados[0] = arrayNuevoGrados[0] + 1
                    }
                }
            }
            return arrayNuevoGrados
        }

        fun getDatosGrafica(arraySector: MutableList<Sectores>?): Array<Int>{
            var arrayTotal = arrayOf(0,0,0,0,0,0)

            for(sector in arraySector!!){
                for(num in 0..5){
                    arrayTotal[num]  += sector.arrayGradosPorVia[num]
                }
            }
            return arrayTotal
        }
    }



}