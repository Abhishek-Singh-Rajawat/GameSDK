package com.itg.itggaming.gameLanding.api.adapter.viewholder

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itg.itggaming.gameLanding.api.adapter.CategoriesLandingAdapter
import com.itg.itggaming.gameLanding.api.model.GameList
import com.itg.itggaming.R
import com.itg.itggaming.util.GameConstants
import com.itg.itggaming.util.GamingCallback

class CategoriesLandingViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
    fun bind(list: GameList, adsBundle: Bundle) {
        val categoryRecyclerView=itemView.findViewById<RecyclerView>(R.id.rv_category)
        val categoryTitle=itemView.findViewById<TextView>(R.id.tv_category_heading)
        val categoryDetailButton=itemView.findViewById<ImageView>(R.id.iv_category_forward)
        categoryTitle.text=list.category
        categoryRecyclerView.layoutManager=LinearLayoutManager(itemView.context,LinearLayoutManager.HORIZONTAL,false)
        categoryRecyclerView.adapter= CategoriesLandingAdapter(list.games)
        categoryRecyclerView.onFlingListener=null
        categoryDetailButton.setOnClickListener {
            logGameCategory(list.category)
            val intent=Intent(itemView.context, com.itg.itggaming.gameCategories.GameCategoryActivity::class.java)
            intent.putExtra(GameConstants.AD_BUNDLE,adsBundle)
            intent.putExtra(GameConstants.CATEGORY_DATA,list)
            itemView.context.startActivity(intent)
        }
    }

    private fun logGameCategory(category: String?) {
        if (category != null) {
            GamingCallback.onCategorySelected(category)
        }
    }
}