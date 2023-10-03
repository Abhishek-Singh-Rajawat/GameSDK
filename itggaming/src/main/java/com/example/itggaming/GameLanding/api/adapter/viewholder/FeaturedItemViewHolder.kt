package com.example.itggaming.GameLanding.api.adapter.viewholder

import android.os.Handler
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.itggaming.GameLanding.api.adapter.FeaturedItemViewpagerAdapter
import com.example.itggaming.GameLanding.api.model.GameList
import com.example.itggaming.GameLanding.api.model.Games
import com.example.itggaming.R
import com.google.android.material.tabs.TabLayout


class FeaturedItemViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
    private lateinit var viewpagerFeaturedItem: ViewPager
    private lateinit var carousalTitle:TextView
    private lateinit var dots:TabLayout
    private lateinit var recyclerView:RecyclerView
    private lateinit var  autoScrollRunnable: Runnable
    private val handler=Handler()
    var currentPosition=0
    var isHandlerSet=false

    var adapter= FeaturedItemViewpagerAdapter()

    fun bind(list: GameList, position: Int) {
        recyclerView=itemView.findViewById(R.id.rv_featured_carousal)
        carousalTitle=itemView.findViewById(R.id.tv_featured_heading)
        dots=itemView.findViewById(R.id.tl_vp_tabs)
        carousalTitle.text=list.category
        currentPosition=0

        val layoutManager=ProminentLayoutManager(itemView.context)
        recyclerView.layoutManager=layoutManager
        recyclerView.adapter=adapter
        recyclerView.setOnFlingListener(null);
        recyclerView.setItemViewCacheSize(3)
        val snapHelper=PagerSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)


        adapter.updateData(list.games, position)
        dots.selectTab(dots.getTabAt(0))
        if(!isHandlerSet){
            addTabs(list)
//            setAutoScroll()
        }
        recyclerView.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val itemCounter=adapter.itemCount/2


                val firstVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition()

                if (firstVisibleItem != RecyclerView.NO_POSITION) {
                    dots.selectTab(dots.getTabAt(firstVisibleItem%itemCounter))
                }


                val firstItemVisible: Int = layoutManager.findFirstVisibleItemPosition()
                if (firstItemVisible != 1 && (firstItemVisible % itemCounter == 1)) {
                    layoutManager.scrollToPosition(1)
                } else if (firstItemVisible != 1 && firstItemVisible > itemCounter && (firstItemVisible % itemCounter > 1)) {
                    layoutManager.scrollToPosition(firstItemVisible % itemCounter)
                } else if (firstItemVisible == 0) {
                    layoutManager.scrollToPositionWithOffset(itemCounter, -recyclerView.computeHorizontalScrollOffset())
                }

            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                when(newState){
                    RecyclerView.SCROLL_STATE_IDLE->{
//                        currentPosition=layoutManager.findLastCompletelyVisibleItemPosition()

                    }
                }
                super.onScrollStateChanged(recyclerView, newState)
            }

        })


//        TabLayoutMediator(dots,viewpagerFeaturedItem){ tab: TabLayout.Tab, i: Int -> }.attach()

//        val nextItemVisiblePx = itemView.context.resources.getDimension(R.dimen.viewpager_next_item_visible)
//        val currentItemHorizontalMarginPx = itemView.context.resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
//        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
//        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
//            page.translationX = -pageTranslationX * position
//            page.scaleY = 1 - (0.25f * abs(position))
////            page.alpha = 0.25f + (1 - abs(position))
//        }
//        viewpagerFeaturedItem.setPageTransformer(pageTransformer)


    }

    private fun setAutoScroll() {
        val handler = Handler()
        var isAutoScrolling = false

         autoScrollRunnable = object : Runnable {
            override fun run() {
                if (!isAutoScrolling) {
                    currentPosition++
                    recyclerView.smoothScrollToPosition(currentPosition)
                }
                handler.postDelayed(this, 4000)
            }
        }
        handler.postDelayed(autoScrollRunnable, 4000)
        isHandlerSet=true
    }

    private fun addTabs(list: GameList) {
        var i=0
        if(dots.tabCount!=list.games.size){
            while(i<list.games.size){
                dots.addTab(dots.newTab())
                i++
            }
        }

    }
}