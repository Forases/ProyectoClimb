package com.lorenzohamaoka.proyectoclimb.ui.lista

import android.app.PendingIntent.getActivity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lorenzohamaoka.proyectoclimb.LoginActivity
import com.lorenzohamaoka.proyectoclimb.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_lista.*
import kotlinx.android.synthetic.main.item_zonas_escalada.view.*


class ListaFragment : Fragment() {

    private lateinit var adapter: ZonasListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_lista, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val manager = LinearLayoutManager(context)
        rv_zonas_escalada.layoutManager = manager
        rv_zonas_escalada.setHasFixedSize(true)
        adapter = ZonasListAdapter()

        rv_zonas_escalada.adapter = adapter
    }

    class ZonasListAdapter: RecyclerView.Adapter<ZonasListAdapter.ViewHolder>() {
        private var zonasItems = LoginActivity.zonasArray


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_zonas_escalada, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = zonasItems[position]

            // Reference to an image file in Cloud Storage
            val storageReference = item.referenciaPortada
            // Download directly from StorageReference using Glide
            // (See MyAppGlideModule for Loader registration)
            Glide.with(holder.containerView)
                .load(storageReference)
                .into(holder.itemView.imagen_zona)
            // [END storage_load_with_glide]

            holder.itemView.tv_nombreZona.text = item.nombreZona
        }

        override fun getItemCount(): Int = zonasItems.size

        inner class ViewHolder(override val containerView: View) :
            RecyclerView.ViewHolder(containerView),
            LayoutContainer
    }
}
