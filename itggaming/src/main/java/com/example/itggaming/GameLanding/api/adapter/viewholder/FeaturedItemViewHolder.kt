package com.example.itggaming.GameLanding.api.adapter.viewholder

import android.content.res.Resources
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.itggaming.GameLanding.api.adapter.FeaturedItemViewpagerAdapter
import com.example.itggaming.GameLanding.api.model.Data
import com.example.itggaming.GameLanding.api.model.GameList
import com.example.itggaming.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.math.abs

class FeaturedItemViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
    private lateinit var viewpagerFeaturedItem:ViewPager2
    private lateinit var carousalTitle:TextView
    private lateinit var dots:TabLayout

    var adapter= FeaturedItemViewpagerAdapter()

    fun bind(list: GameList, position: Int) {
        viewpagerFeaturedItem=itemView.findViewById(R.id.vp_featured_carousal)
        carousalTitle=itemView.findViewById(R.id.tv_featured_heading)
        dots=itemView.findViewById(R.id.tl_vp_tabs)
        carousalTitle.text=list.category
        viewpagerFeaturedItem.adapter=adapter
        viewpagerFeaturedItem.offscreenPageLimit=3

        adapter.updateData(list.games, position)

        TabLayoutMediator(dots,viewpagerFeaturedItem){ tab: TabLayout.Tab, i: Int -> }.attach()

        val nextItemVisiblePx = itemView.context.resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx = itemView.context.resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            page.scaleY = 1 - (0.25f * abs(position))
//            page.alpha = 0.25f + (1 - abs(position))
        }
        viewpagerFeaturedItem.setPageTransformer(pageTransformer)


    }
}