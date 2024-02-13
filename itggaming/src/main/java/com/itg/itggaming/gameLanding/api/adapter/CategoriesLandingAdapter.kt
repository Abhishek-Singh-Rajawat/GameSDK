package com.itg.itggaming.gameLanding.api.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.itg.itggaming.gameLanding.api.model.Games
import com.itg.itggaming.R
import com.itg.itggaming.gameWebView.GameWebViewActivity
import com.itg.itggaming.util.GameConstants
import com.itg.itggaming.util.GamingCallback

class CategoriesLandingAdapter(list: ArrayList<Games>):
    RecyclerView.Adapter<CategoriesLandingAdapter.Viewholder>() {
    var dataset=list
    class Viewholder(view: View):RecyclerView.ViewHolder(view) {
        var categoryImg=view.findViewById<ImageView>(R.id.iv_category_image)
        var categoryTitle=view.findViewById<TextView>(R.id.tv_category_title)
        var cardLayout=view.findViewById<LinearLayoutCompat>(R.id.card_layout)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_landing_category, parent, false)
        return Viewholder(view)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        if(dataset.get(position).images!=null){
            Glide.with(holder.itemView.context)
                .load(dataset.get(position).images?.icon)
                .into(holder.categoryImg)
        }
        holder.categoryImg.clipToOutline=true
        holder.categoryTitle.text=dataset.get(position).gameName

        holder.cardLayout.setOnClickListener {
            logGameClicked(dataset.get(position).gameName)
            val intent= Intent(holder.itemView.context, GameWebViewActivity::class.java)
            intent.putExtra(GameConstants.GAME_DATA,dataset.get(position))
            holder.itemView.context.startActivity(intent)
        }
    }
    private fun logGameClicked(gameName: String?) {
        if (gameName != null) {
            GamingCallback.onGameSelected(gameName)
        }
    }
}