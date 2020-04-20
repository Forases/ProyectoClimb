package com.lorenzohamaoka.proyectoclimb.ui.buscar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.lorenzohamaoka.proyectoclimb.R
import com.lorenzohamaoka.proyectoclimb.models.Quote
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_buscar.*
import kotlinx.android.synthetic.main.search_item.view.*
import java.util.*
import kotlin.collections.ArrayList

class BuscarFragment : Fragment() {

    private lateinit var adapter: MyQuoteAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_buscar, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                val text = newText
                /*Call filter Method Created in Custom Adapter
                    This Method Filter ListView According to Search Keyword
                 */
                adapter.filter(text)
                return false
            }
        })

        val items = mutableListOf(
            Quote("optimization is the root of all evil", null),
            Quote("Anytechnology is indistinguishable from magic.", "Arthur C. Clarke"),
            Quote("Content 01", "Source"),
            Quote("Content 02", "Source"),
            Quote("Content 03", "Source"),
            Quote("Content 04", "Source"),
            Quote("Content 05", "Source")
        )

        adapter = MyQuoteAdapter(items)
        adapter.replaceItems(items)
        list.adapter = adapter
    }

    class MyQuoteAdapter (items: MutableList<Quote>) : RecyclerView.Adapter<MyQuoteAdapter.ViewHolder>() {
        private var quoteItems = items
        //Store image and arraylist in Temp Array List we Required it later
        var tempListZonas = ArrayList(quoteItems)


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.search_item, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = quoteItems[position]

            holder.itemView.search_nombre_zona.text = item.content
            holder.itemView.search_localidad.text = item.source
        }

        fun replaceItems(items: MutableList<Quote>) {
            this.quoteItems = items
            notifyDataSetChanged()
        }

        //Function to set data according to Search Keyword in ListView
        fun filter(text: String?) {
            //Our Search text
            val text = text!!.toLowerCase(Locale.getDefault())

            quoteItems.clear()

            if (text.length == 0) {

                /*If Search query is Empty than we add all temp data into our main ArrayList
                We store Value in temp in Starting of Program.
                */
                quoteItems.addAll(tempListZonas)
            } else {


                for (i in 0..tempListZonas.size - 1) {
                    /*
                    If our Search query is not empty thEn we Check Our search keyword in Temp ArrayList.
                    if our Search Keyword in Temp ArrayList than we add to our Main ArrayList
                    */
                    if (tempListZonas.get(i).content.toLowerCase(Locale.getDefault()).contains(text)) {
                        quoteItems.add(tempListZonas.get(i))
                    }

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
