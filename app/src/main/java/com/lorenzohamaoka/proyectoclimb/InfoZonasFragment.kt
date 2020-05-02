package com.lorenzohamaoka.proyectoclimb

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.lorenzohamaoka.proyectoclimb.MainActivity.Companion.zonaEscalada
import com.lorenzohamaoka.proyectoclimb.Utils.Companion.getDatosGrafica
import com.lorenzohamaoka.proyectoclimb.Utils.Companion.getGradoVias
import kotlinx.android.synthetic.main.fragment_info_zonas.*
import java.util.ArrayList


/**
 * A simple [Fragment] subclass.
 * Use the [InfoZonasFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InfoZonasFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_info_zonas, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var arraySectores = zonaEscalada?.sectores
        var arrayContadorVias = arrayOf(0,0,0,0,0,0)

        if (arraySectores != null) {
            for(sector in arraySectores){
                var arrayGrados = getGradoVias(sector.vias)
                sector.arrayGradosPorVia = arrayGrados
                arrayContadorVias += arrayGrados
            }
            arrayContadorVias = getDatosGrafica(arraySectores)
                localidad_zonas_activity.text = zonaEscalada?.localidad
        }
        orientacion_zonas_activity.text = zonaEscalada?.orientacion
        tipo_zonas_activity.text = zonaEscalada?.tipoEscalada
        roca_zonas_activity.text = zonaEscalada?.tipoRoca
        restricciones_zonas_activity.text = zonaEscalada?.restricciones



        setBarChart(arrayContadorVias)

        boton_ruta.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?&daddr=" + zonaEscalada?.latitud +
                        "," + zonaEscalada?.longitud)
            )
            startActivity(intent)
        }
    }

    private fun setBarChart(arrayTotalVias: Array<Int>) {
        val values = ArrayList<BarEntry>()
        values.add(BarEntry(4f, arrayTotalVias[0].toFloat()))
        values.add(BarEntry(5f, arrayTotalVias[1].toFloat()))
        values.add(BarEntry(6f, arrayTotalVias[2].toFloat()))
        values.add(BarEntry(7f, arrayTotalVias[3].toFloat()))
        values.add(BarEntry(8f, arrayTotalVias[4].toFloat()))
        values.add(BarEntry(9f, arrayTotalVias[5].toFloat()))

        val barDataSet = BarDataSet(values, "Nº de vías por grado de dificultad")
        barDataSet.setDrawIcons(false)
        barDataSet.setDrawValues(true)

        val startColor1 = resources.getColor(android.R.color.holo_blue_light)
        val startColor2 = resources.getColor(android.R.color.holo_green_dark)
        val startColor3 = resources.getColor(android.R.color.holo_green_light)
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
