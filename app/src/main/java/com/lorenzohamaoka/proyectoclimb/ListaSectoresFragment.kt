package com.lorenzohamaoka.proyectoclimb

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.google.android.material.color.MaterialColors.getColor
import com.google.common.io.Resources.getResource
import com.lorenzohamaoka.proyectoclimb.ZonasActivity.Companion.sectoresArray
import com.lorenzohamaoka.proyectoclimb.models.Sectores
import com.lorenzohamaoka.proyectoclimb.models.Vias
import dam.lorenzohamaoka.climbingapp.models.ZonasEscalada
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_info_zonas.*
import kotlinx.android.synthetic.main.fragment_lista_sectores.*
import kotlinx.android.synthetic.main.item_sectores_escalada.view.*
import java.util.ArrayList


/**
 * A simple [Fragment] subclass.
 * Use the [ListaSectoresFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListaSectoresFragment : Fragment() {
    companion object{
        var viasEscalada: MutableList<Vias>? = arrayListOf()
    }

    private lateinit var adapter: SectoresListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_lista_sectores, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val manager = LinearLayoutManager(context)
        lista_sectores.layoutManager = manager
        lista_sectores.setHasFixedSize(true)

        adapter = SectoresListAdapter()

        lista_sectores.adapter = adapter
    }

    class SectoresListAdapter: RecyclerView.Adapter<SectoresListAdapter.ViewHolder>() {
        private var sectoresItems = sectoresArray


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_sectores_escalada, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = sectoresItems[position]

            holder.itemView.nombre_sector.text = item.nombreSector

            holder.setBarChart(item.arrayGradosPorVia,holder.itemView.sectoresChart, holder.containerView.context)
            holder.bind(item, holder.containerView.context)
        }

        override fun getItemCount(): Int {
            return sectoresItems.size
        }

        inner class ViewHolder(override val containerView: View) :
            RecyclerView.ViewHolder(containerView),
            LayoutContainer{

            fun bind(sector: Sectores, context: Context) {
                itemView.setOnClickListener {
                    MainActivity.sectorEscalada = sector
                    viasEscalada = sector.vias
                    val myIntent = Intent(context, SectoresActivity::class.java)
                    context.startActivity(myIntent)
                }
            }

            fun setBarChart(arrayGrados: Array<Int>, barChart: BarChart, context: Context) {
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

                val startColor1 = ContextCompat.getColor(context,android.R.color.holo_blue_light)
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



}
