package com.lorenzohamaoka.proyectoclimb.ui.buscar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lorenzohamaoka.proyectoclimb.LoginActivity
import com.lorenzohamaoka.proyectoclimb.R
import dam.lorenzohamaoka.climbingapp.models.ZonasEscalada
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_buscar.*
import kotlinx.android.synthetic.main.search_item.view.*
import java.util.*


class BuscarFragment : Fragment() {

    private lateinit var adapter: MySearchAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_buscar, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(text: String?): Boolean {
                /*Call filter Method Created in Custom Adapter
                    This Method Filter ListView According to Search Keyword
                 */
                adapter.filter(text, LoginActivity.zonasArray)
                return false
            }
        })

        adapter = MySearchAdapter()

        val mDividerItemDecoration = DividerItemDecoration(
            search_list.context,
            LinearLayoutManager.VERTICAL
        )
        search_list.addItemDecoration(mDividerItemDecoration);
        search_list.adapter = adapter
    }

    class MySearchAdapter: RecyclerView.Adapter<MySearchAdapter.ViewHolder>() {
        private var quoteItems: MutableList<ZonasEscalada> = arrayListOf()
        //Store image and arraylist in Temp Array List we Required it later
        var tempListZonas: MutableList<ZonasEscalada> = arrayListOf()


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.search_item, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = quoteItems[position]

            holder.itemView.search_nombre_zona.text = item.nombreZona
            holder.itemView.search_localidad.text = item.localidad
            holder.itemView.search_distancia.text = "(" + item.distancia + " km)"
        }

        //Function to set data according to Search Keyword in ListView
        fun filter(text: String?, items: MutableList<ZonasEscalada>) {
            //Our Search text
            val text = text!!.toLowerCase(Locale.getDefault())

            tempListZonas.clear()
            quoteItems.clear()
            tempListZonas.addAll(items)

            for (i in 0 until tempListZonas.size) {
                /*
                If our Search query is not empty thEn we Check Our search keyword in Temp ArrayList.
                if our Search Keyword in Temp ArrayList than we add to our Main ArrayList
                */
                if (tempListZonas[i].nombreZona!!.toLowerCase(Locale.getDefault()).contains(text)) {
                    quoteItems.add(tempListZonas[i])
                }
            }
            //This is to notify that data change in Adapter and Reflect the changes.
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int = quoteItems.size

        inner class ViewHolder(override val containerView: View) :
            RecyclerView.ViewHolder(containerView),
            LayoutContainer
    }
}
