package com.example.itggaming.GameLanding.api.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.itggaming.GameLanding.api.adapter.viewholder.AdsViewHolder
import com.example.itggaming.GameLanding.api.adapter.viewholder.BlankViewHolder
import com.example.itggaming.GameLanding.api.adapter.viewholder.CategoriesLandingViewHolder
import com.example.itggaming.GameLanding.api.adapter.viewholder.FeaturedItemViewHolder
import com.example.itggaming.GameLanding.api.model.GameList
import com.example.itggaming.R
import com.example.itggaming.util.GameConstants

class GameLandingAdapter(adsData: Bundle, var dataset: ArrayList<GameList>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val adsBundle=adsData
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        when(viewType){
            0->{
                val view: View =inflater.inflate(R.layout.layout_landing_featured,parent,false)
                return FeaturedItemViewHolder(view)
            }
            1->{
                val view: View =inflater.inflate(R.layout.layout_landing_category,parent,false)
                return CategoriesLandingViewHolder(view)
            }
            2->{
                val view: View =inflater.inflate(R.layout.layout_landing_ads,parent,false)
                return AdsViewHolder(view)
            }
            else->{
                val view: View =inflater.inflate(R.layout.layout_blank,parent,false)
                return BlankViewHolder(view)
            }
        }
    }

    override fun getItemCount(): Int {
       return dataset.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)){
            0->{
                (holder as FeaturedItemViewHolder).bind(dataset.get(position),position)
            }
            1->{
                (holder as CategoriesLandingViewHolder).bind(dataset.get(position), adsBundle)
            }
            2->{

                adsBundle.getString(GameConstants.AD_UNIT_ID)?.let { (holder as AdsViewHolder).bind(it) }
            }
            else->{
                (holder as BlankViewHolder).bind()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(dataset.get(position).type){
            "carousal"->{
                0
            }
            "list"->{
                1
            }
            "ads"->{
                2
            }
            else->{
                4
            }
        }
    }
}