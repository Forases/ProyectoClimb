package com.lorenzohamaoka.proyectoclimb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.lorenzohamaoka.proyectoclimb.ui.buscar.BuscarFragment
import com.lorenzohamaoka.proyectoclimb.ui.filtro.FiltroFragment
import com.lorenzohamaoka.proyectoclimb.ui.lista.ListaZonasFragment
import com.lorenzohamaoka.proyectoclimb.ui.map.MapFragment
import dam.lorenzohamaoka.climbingapp.models.ZonasEscalada
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        var zonaEscalada: ZonasEscalada? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragment(MapFragment())

        bottom_nav_view.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.navigation_map-> {
                    loadFragment(MapFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.navigation_buscar-> {
                    loadFragment(BuscarFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.navigation_filtro-> {
                    loadFragment(FiltroFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.navigation_lista-> {
                    loadFragment(ListaZonasFragment())
                    return@setOnNavigationItemSelectedListener true
                }

            }
            false

        }
    }

    private fun loadFragment(fragment: Fragment) {
        // load fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
