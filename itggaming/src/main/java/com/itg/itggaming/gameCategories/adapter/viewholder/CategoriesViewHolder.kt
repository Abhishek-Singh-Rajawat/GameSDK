package com.itg.itggaming.gameCategories.adapter.viewholder

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.itg.itggaming.gameLanding.api.model.Games
import com.itg.itggaming.R
import com.itg.itggaming.gameWebView.GameWebViewActivity
import com.itg.itggaming.util.GameConstants
import com.itg.itggaming.util.GamingCallback

class CategoriesViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
    fun bind(game: Games, position: Int) {
        var catImg=itemView.findViewById<ImageView>(R.id.iv_category_image)
        var catTitle=itemView.findViewById<TextView>(R.id.tv_category_title)

        catImg.clipToOutline=true
        Glide.with(itemView.context)
            .load(game.images?.icon)
            .into(catImg)

        catTitle.text=game.gameName

        catImg.setOnClickListener {
            logGameClicked(game.gameName)
            var intent= Intent(itemView.context, GameWebViewActivity::class.java)
            intent.putExtra(GameConstants.GAME_DATA,game)
            itemView.context.startActivity(intent)
        }
    }
    private fun logGameClicked(gameName: String?) {
        if (gameName != null) {
            GamingCallback.onGameSelected(gameName)
        }
    }
}