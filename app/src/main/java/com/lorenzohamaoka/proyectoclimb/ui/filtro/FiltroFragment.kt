package com.lorenzohamaoka.proyectoclimb.ui.filtro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.lorenzohamaoka.proyectoclimb.R

class FiltroFragment : Fragment() {

    private lateinit var filtroViewModel: FiltroViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        filtroViewModel =
                ViewModelProviders.of(this).get(FiltroViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        return root
    }


}
