package com.lorenzohamaoka.proyectoclimb

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.lorenzohamaoka.proyectoclimb.ui.buscar.BuscarFragment
import com.lorenzohamaoka.proyectoclimb.ui.filtro.FiltroFragment
import com.lorenzohamaoka.proyectoclimb.ui.lista.ListaFragment
import com.lorenzohamaoka.proyectoclimb.ui.map.MapFragment
import dam.lorenzohamaoka.climbingapp.models.ZonasEscalada
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        var zonasArray: MutableList<ZonasEscalada> = arrayListOf()
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
                    loadFragment(ListaFragment())
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
