package com.example.itggaming.GameLanding.api.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.itggaming.GameLanding.api.model.Games
import com.example.itggaming.R
import com.example.itggaming.gameWebView.GameWebViewActivity
import com.example.itggaming.util.GameConstants
import com.example.itggaming.util.GamingCallback
import com.example.itggaming.util.GamingLogCallbacks
import com.example.itggaming.util.SizeUtils

class FeaturedItemViewpagerAdapter(): RecyclerView.Adapter<FeaturedItemViewpagerAdapter.ViewHolder>(){

    private var dataset= arrayListOf<Games>()


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_landing_featured, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var itemCounter=itemCount/2
        holder.featuredItem.clipToOutline=true

        val displayWidth = Resources.getSystem().displayMetrics.widthPixels
        holder.featuredLayout.getLayoutParams().width = displayWidth - SizeUtils.dpToPx(16) * 4

        if(dataset.get(position%itemCounter).images!=null){
            Glide.with(holder.itemView.context)
                .load(dataset.get(position%itemCounter).images?.horizontalBanner)
                .into(holder.featuredItem)

        }
        holder.featuredItem.setOnClickListener {
            logGameClicked(dataset.get(position%itemCounter).gameName)
            val intent= Intent(holder.itemView.context, GameWebViewActivity::class.java)
            intent.putExtra(GameConstants.GAME_DATA,dataset.get(position%itemCounter))
            holder.itemView.context.startActivity(intent)
        }
    }

    private fun logGameClicked(gameName: String?) {
        if (gameName != null) {
            GamingCallback.onGameSelected(gameName)
        }
    }

    override fun getItemCount(): Int {
        return dataset.size*2
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var featuredItem=view.findViewById<ImageView>(R.id.iv_featured_item)
        var featuredLayout=view.findViewById<ConstraintLayout>(R.id.layout)
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