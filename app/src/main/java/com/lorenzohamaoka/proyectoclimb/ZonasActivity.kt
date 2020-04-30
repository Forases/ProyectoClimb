package com.lorenzohamaoka.proyectoclimb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.lorenzohamaoka.proyectoclimb.MainActivity.Companion.zonaEscalada
import dam.lorenzohamaoka.climbingapp.models.Sectores
import kotlinx.android.synthetic.main.activity_zonas.*

class ZonasActivity : AppCompatActivity() {
    companion object{
        var sectoresArray: MutableList<Sectores> = arrayListOf()
    }
    private var viewPager: ViewPager2? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zonas)

        viewPager = findViewById(R.id.zonas_view_pager)
        viewPager!!.adapter = MyAdapter(supportFragmentManager, lifecycle)
        viewPager!!.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        title = zonaEscalada?.nombreZona
        sectoresArray = zonaEscalada?.sectores!!

        TabLayoutMediator(zonasTabLayout, viewPager!!) { tab, position ->
            when (position) {
                0 -> tab.text = "INFORMACIÓN"
                1 -> tab.text = "SECTORES"
            }
        }.attach()
    }

    private inner class MyAdapter(fm: FragmentManager?, lifecycle: Lifecycle) : FragmentStateAdapter(fm!!, lifecycle) {
        private val int_items = 2

        override fun createFragment(position: Int): Fragment {
            var fragment: Fragment? = null
            when (position) {
                0 -> fragment = InfoZonasFragment()
                1 -> fragment = ListaSectoresFragment()
            }
            return fragment!!
        }

        override fun getItemCount(): Int {
            return int_items
        }
    }
}
