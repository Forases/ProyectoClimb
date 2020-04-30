package com.lorenzohamaoka.proyectoclimb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lorenzohamaoka.proyectoclimb.ZonasActivity.Companion.sectoresArray
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_sectores.*
import kotlinx.android.synthetic.main.activity_sectores.view.*
import kotlinx.android.synthetic.main.item_sectores_escalada.view.*
import kotlinx.android.synthetic.main.item_via_escalada.view.*

class SectoresActivity : AppCompatActivity() {

    private lateinit var adapter: ViasListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sectores)

        val manager = LinearLayoutManager(this)
        lista_vias.layoutManager = manager
        lista_vias.setHasFixedSize(true)

        adapter = ViasListAdapter()

        lista_vias.adapter = adapter

        Utils.setImage(
            MainActivity.sectorEscalada?.referenciaImagen,
            this,
            imagen_sector
        )

    }

    class ViasListAdapter: RecyclerView.Adapter<ViasListAdapter.ViewHolder>() {
        private var viasItems = ListaSectoresFragment.viasEscalada


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_via_escalada, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = viasItems?.get(position)

            holder.itemView.imagen_sector

            holder.itemView.numero_via.text = item!!.numero?.toString()
            holder.itemView.nombre_via.text = item!!.nombreVia
            holder.itemView.longitud_via.text = item!!.longitud
            holder.itemView.grado_via.text = item!!.gradoVia
        }

        override fun getItemCount(): Int {
            return viasItems!!.size
        }

        inner class ViewHolder(override val containerView: View) :
            RecyclerView.ViewHolder(containerView),
            LayoutContainer
    }
}
