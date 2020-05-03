package com.lorenzohamaoka.proyectoclimb

import android.content.Context
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.lorenzohamaoka.proyectoclimb.models.Sectores
import com.lorenzohamaoka.proyectoclimb.models.Vias
import java.util.ArrayList
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
            val arrayNuevoGrados = arrayOf(0, 0, 0, 0, 0, 0)
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
            val arrayTotal = arrayOf(0,0,0,0,0,0)

            for(sector in arraySector!!){
                for(num in 0..5){
                    arrayTotal[num]  += sector.arrayGradosPorVia[num]
                }
            }
            return arrayTotal
        }

        fun setBarChart(arrayGrados: Array<Int>, barChart: BarChart, context: Context?) {
            val values = ArrayList<BarEntry>()
            values.add(BarEntry(4f, arrayGrados[0].toFloat()))
            values.add(BarEntry(5f, arrayGrados[1].toFloat()))
            values.add(BarEntry(6f, arrayGrados[2].toFloat()))
            values.add(BarEntry(7f, arrayGrados[3].toFloat()))
            values.add(BarEntry(8f, arrayGrados[4].toFloat()))
            values.add(BarEntry(9f, arrayGrados[5].toFloat()))

            val barDataSet = BarDataSet(values, "Nº de vías por grado de dificultad")
            barDataSet.setDrawIcons(false)
            barDataSet.setDrawValues(true)

            barChart.setViewPortOffsets(0f,50f,0f,0f)

            val startColor1 = ContextCompat.getColor(context!!,android.R.color.holo_blue_light)
            val startColor2 = ContextCompat.getColor(context,android.R.color.holo_green_dark)
            val startColor3 = ContextCompat.getColor(context,android.R.color.holo_green_light)
            val startColor4 = ContextCompat.getColor(context,android.R.color.holo_orange_light)
            val startColor5 = ContextCompat.getColor(context,android.R.color.holo_orange_dark)
            val startColor6 = ContextCompat.getColor(context,android.R.color.holo_red_light)

            val colorsArray: MutableList<Int> = ArrayList<Int>()
            colorsArray.add(startColor1)
            colorsArray.add(startColor2)
            colorsArray.add(startColor3)
            colorsArray.add(startColor4)
            colorsArray.add(startColor5)
            colorsArray.add(startColor6)

            barDataSet.colors = colorsArray

            val xAxis: XAxis = barChart.xAxis
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)
            xAxis.setDrawLabels(false)

            val rightAxis: YAxis = barChart.axisRight
            rightAxis.setDrawAxisLine(false)
            rightAxis.setDrawLabels(false)

            val leftAxis: YAxis = barChart.axisLeft
            leftAxis.setDrawAxisLine(false)
            leftAxis.setDrawLabels(false)

            barChart.axisLeft.setDrawGridLines(false)
            barChart.axisRight.setDrawGridLines(false)

            val data = BarData(barDataSet)
            barChart.data = data // set the data and list of lables into chart

            barChart.description = null  // set the description


            barChart.animateY(1500)
            barChart.legend.isEnabled = false
        }
    }
}