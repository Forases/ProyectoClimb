package com.lorenzohamaoka.proyectoclimb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dam.lorenzohamaoka.climbingapp.models.Sectores
import kotlinx.android.synthetic.main.item_view_pager.view.*

class ViewPagerAdapter(var sectores: MutableList<Sectores>?)
    : RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>(){
    inner class ViewPagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view_pager, parent, false)
        return ViewPagerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return sectores!!.size
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        val currentImage = sectores?.get(position)?.referenciaImagen
        Glide.with(holder.itemView)
            .load(currentImage)
            .into(holder.itemView.view_pager_image)
    }

}