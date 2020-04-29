package com.lorenzohamaoka.proyectoclimb

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.lorenzohamaoka.proyectoclimb.MainActivity.Companion.zonaEscalada
import com.lorenzohamaoka.proyectoclimb.Utils.Companion.setImage
import kotlinx.android.synthetic.main.activity_zonas.*
import java.util.*

class ZonasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zonas)
        title = zonaEscalada?.nombreZona

        setBarChart()

        localidad_zonas_activity.text = zonaEscalada?.localidad
        orientacion_zonas_activity.text = zonaEscalada?.orientacion
        tipo_zonas_activity.text = zonaEscalada?.tipoEscalada
        roca_zonas_activity.text = zonaEscalada?.tipoRoca
        restricciones_zonas_activity.text = zonaEscalada?.restricciones

        setImage(zonaEscalada?.referenciaPortada, this, this.imagen_zonas_activity)

        boton_ruta.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?&daddr=" + zonaEscalada?.latitud +
                        "," + zonaEscalada?.longitud)
            )
            startActivity(intent)
        }

//        val adapter = ViewPagerAdapter(zonaEscalada!!.sectores)
//        viewpager.adapter = adapter

    }

    private fun setBarChart() {
        val values = ArrayList<BarEntry>()
        values.add(BarEntry(4f, 18f))
        values.add(BarEntry(5f, 32f))
        values.add(BarEntry(6f, 64f))
        values.add(BarEntry(7f, 26f))
        values.add(BarEntry(8f, 15f))
        values.add(BarEntry(9f, 3f))

        val barDataSet = BarDataSet(values, "Nº de vías por grado de dificultad")
        barDataSet.setDrawIcons(false)
        barDataSet.setDrawValues(true)

        val startColor1 = resources.getColor(android.R.color.holo_blue_light)
        val startColor2 = resources.getColor(android.R.color.holo_green_light)
        val startColor3 = resources.getColor(android.R.color.holo_green_dark)
        val startColor4 = resources.getColor( android.R.color.holo_orange_light)
        val startColor5 = resources.getColor( android.R.color.holo_orange_dark)
        val startColor6 = resources.getColor( android.R.color.holo_red_light)

        val colorsArray: MutableList<Int> = ArrayList<Int>()
        colorsArray.add(startColor1)
        colorsArray.add(startColor2)
        colorsArray.add(startColor3)
        colorsArray.add(startColor4)
        colorsArray.add(startColor5)
        colorsArray.add(startColor6)

        barDataSet.colors = colorsArray

//        val labels = ArrayList<String>()
//        labels.add("3-4+")
//        labels.add("5-5+")
//        labels.add("6a-6c+")
//        labels.add("7a-7c+")
//        labels.add("8a-8c+")
//        labels.add("9a-9b")

        val xAxis: XAxis = barChart.xAxis
        xAxis.position = XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
//        xAxis.setDrawLabels(false)

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
