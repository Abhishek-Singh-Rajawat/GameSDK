package com.example.itggaming.GameLanding.api.adapter.viewholder

import com.example.itggaming.GameCategories.GameCategoryActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itggaming.GameLanding.api.adapter.CategoriesLandingAdapter
import com.example.itggaming.GameLanding.api.model.GameList
import com.example.itggaming.R
import com.example.itggaming.util.GameConstants

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
            val intent=Intent(itemView.context, GameCategoryActivity::class.java)
            intent.putExtra(GameConstants.AD_BUNDLE,adsBundle)
            intent.putExtra(GameConstants.CATEGORY_DATA,list)
            itemView.context.startActivity(intent)
        }
    }
}