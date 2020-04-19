package com.lorenzohamaoka.proyectoclimb.ui.buscar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.lorenzohamaoka.proyectoclimb.R
import com.lorenzohamaoka.proyectoclimb.models.Quote
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.myquote_list.*
import kotlinx.android.synthetic.main.myquote_list_item.view.*

class BuscarFragment : Fragment() {

    companion object {

        @JvmStatic
        fun newInstance() =
            BuscarFragment().apply {
                arguments = Bundle().apply {
                    // putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }

    private lateinit var adapter: MyQuoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            // columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.myquote_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val items = listOf(
            Quote("Premature optimization is the root of all evil", null),
            Quote("Any sufficiently advanced technology is indistinguishable from magic.", "Arthur C. Clarke"),
            Quote("Content 01", "Source"),
            Quote("Content 02", "Source"),
            Quote("Content 03", "Source"),
            Quote("Content 04", "Source"),
            Quote("Content 05", "Source")
        )

        adapter = MyQuoteAdapter()
        adapter.replaceItems(items)
        list.adapter = adapter
    }

    class MyQuoteAdapter : RecyclerView.Adapter<MyQuoteAdapter.ViewHolder>() {
        private var items = listOf<Quote>()


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.myquote_list_item, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = items[position]

            holder.itemView.contentTextView.text = item.content
            holder.itemView.sourceTextView.text = item.source
        }

        fun replaceItems(items: List<Quote>) {
            this.items = items
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int = items.size

        inner class ViewHolder(override val containerView: View) :
            RecyclerView.ViewHolder(containerView),
            LayoutContainer
    }
}
