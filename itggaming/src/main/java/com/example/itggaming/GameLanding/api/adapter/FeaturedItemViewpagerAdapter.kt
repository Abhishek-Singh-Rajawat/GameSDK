package com.example.itggaming.GameLanding.api.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.itggaming.GameLanding.api.model.Games
import com.example.itggaming.R
import com.example.itggaming.gameWebView.GameWebViewActivity
import com.example.itggaming.util.GameConstants

class FeaturedItemViewpagerAdapter: RecyclerView.Adapter<FeaturedItemViewpagerAdapter.ViewHolder>(){

    private var dataset= arrayListOf<Games>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_landing_featured, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.featuredItem.clipToOutline=true
        if(dataset.get(position).images!=null){
            Glide.with(holder.itemView.context)
                .load(dataset.get(position).images?.horizontalBanner)
                .into(holder.featuredItem)
        }
        holder.featuredItem.setOnClickListener {
            val intent= Intent(holder.itemView.context, GameWebViewActivity::class.java)
            intent.putExtra(GameConstants.GAME_DATA,dataset.get(position))
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var featuredItem=view.findViewById<ImageView>(R.id.iv_featured_item)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(dataset: ArrayList<Games>, position: Int){
        this.dataset=dataset

        if (position<0){
            notifyDataSetChanged()
        }
        else{
            notifyItemChanged(position)
        }
    }
}