package com.lorenzohamaoka.proyectoclimb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.lorenzohamaoka.proyectoclimb.ui.map.MapFragment
import com.lorenzohamaoka.proyectoclimb.ui.map.MapFragment.Companion.zonaEscalada
import com.lorenzohamaoka.proyectoclimb.ui.map.NOMBRE_ZONA
import dam.lorenzohamaoka.climbingapp.models.ZonasEscalada
import kotlinx.android.synthetic.main.activity_zonas.*
import java.util.*

class ZonasActivity : AppCompatActivity() {
    private var viewPager: ViewPager2? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zonas)
        viewPager = findViewById(R.id.viewpager)
        viewPager!!.adapter = MyAdapter(supportFragmentManager, lifecycle)
        viewPager!!.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        setBarChart()

        titulo_zonas_activity.text = zonaEscalada?.nombreZona
        localidad_zonas_activity.text = zonaEscalada?.localidad
        orientacion_zonas_activity.text = zonaEscalada?.orientacion
        tipo_zonas_activity.text = zonaEscalada?.tipoEscalada
        roca_zonas_activity.text = zonaEscalada?.tipoRoca
        restricciones_zonas_activity.text = zonaEscalada?.restricciones

        // Reference to an image file in Cloud Storage
        val storageReference = zonaEscalada?.referenciaPortada
        // Download directly from StorageReference using Glide
        // (See MyAppGlideModule for Loader registration)
        Glide.with(this)
            .load(storageReference)
            .into(this.imagen_zonas_activity)
        // [END storage_load_with_glide]

    }


    private fun setBarChart() {
        val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(8f, 0))
        entries.add(BarEntry(2f, 1))
        entries.add(BarEntry(5f, 2))
        entries.add(BarEntry(20f, 3))
        entries.add(BarEntry(15f, 4))
        entries.add(BarEntry(19f, 5))

        val barDataSet = BarDataSet(entries, "Nº de vías por grado de dificultad")

        val labels = ArrayList<String>()
        labels.add("3-4+")
        labels.add("5-5+")
        labels.add("6a-6c+")
        labels.add("7a-7c+")
        labels.add("8a-8c+")
        labels.add("9a-9b")
        val data = BarData(labels, barDataSet)
        barChart.data = data // set the data and list of lables into chart

        barChart.setDescription(null)  // set the description

//        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS)
        barDataSet.color = resources.getColor(R.color.colorAccent)

        barChart.animateY(3000)
    }

    private inner class MyAdapter(fm: FragmentManager?, lifecycle: Lifecycle) : FragmentStateAdapter(fm!!, lifecycle) {
        private val int_items = 5

        override fun createFragment(position: Int): Fragment {
            var fragment: Fragment? = null
            when (position) {
                0 -> fragment = ViewPagerFragment()
                1 -> fragment = MapFragment()
                2 -> fragment = ViewPagerFragment()
                3 -> fragment = ViewPagerFragment()
                4 -> fragment = ViewPagerFragment()
            }
            return fragment!!
        }

        override fun getItemCount(): Int {
            return int_items
        }

    }
}
