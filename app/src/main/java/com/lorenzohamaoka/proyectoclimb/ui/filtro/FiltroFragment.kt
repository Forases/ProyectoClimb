package com.lorenzohamaoka.proyectoclimb.ui.filtro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lorenzohamaoka.proyectoclimb.R

class FiltroFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_filtro, container, false)

        return root
    }


}
