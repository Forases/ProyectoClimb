package com.lorenzohamaoka.proyectoclimb

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lorenzohamaoka.proyectoclimb.MainActivity.Companion.zonaEscalada
import com.lorenzohamaoka.proyectoclimb.Utils.Companion.getDatosGrafica
import com.lorenzohamaoka.proyectoclimb.Utils.Companion.getGradoVias
import com.lorenzohamaoka.proyectoclimb.Utils.Companion.setBarChart
import kotlinx.android.synthetic.main.fragment_info_zonas.*


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



        setBarChart(arrayContadorVias, barChart, context)

        boton_ruta.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?&daddr=" + zonaEscalada?.latitud +
                        "," + zonaEscalada?.longitud)
            )
            startActivity(intent)
        }
    }
}
