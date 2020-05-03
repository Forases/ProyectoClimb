package com.lorenzohamaoka.proyectoclimb.ui.filtro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.lorenzohamaoka.proyectoclimb.R
import com.lorenzohamaoka.proyectoclimb.SharedApp
import kotlinx.android.synthetic.main.fragment_filtro.*

class FiltroFragment : Fragment(), SeekBar.OnSeekBarChangeListener {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_filtro, container, false)
        activity?.title = "Opciones de filtrado"



        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        distanceBar.setOnSeekBarChangeListener(this)
        distanceBar.progress = SharedApp.preferences.distance


        prefButton.setOnClickListener {
            SharedApp.preferences.distance = distanceBar.progress
        }
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        distanceText.text = "$progress km"
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        distanceText.text = "${seekBar!!.progress} km"
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        distanceText.text = "${seekBar!!.progress} km"
    }
}
