package com.lorenzohamaoka.proyectoclimb

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lorenzohamaoka.proyectoclimb.ZonasActivity.Companion.sectoresArray
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_lista_sectores.*
import kotlinx.android.synthetic.main.item_sectores_escalada.view.*


/**
 * A simple [Fragment] subclass.
 * Use the [ListaSectoresFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListaSectoresFragment : Fragment() {

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
        }

        override fun getItemCount(): Int {
            return sectoresItems.size
        }

        inner class ViewHolder(override val containerView: View) :
            RecyclerView.ViewHolder(containerView),
            LayoutContainer
    }

}