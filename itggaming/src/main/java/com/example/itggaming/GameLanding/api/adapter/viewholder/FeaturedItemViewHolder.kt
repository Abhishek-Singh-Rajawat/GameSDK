package com.example.itggaming.GameLanding.api.adapter.viewholder

import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.itggaming.GameLanding.api.adapter.FeaturedItemViewpagerAdapter
import com.example.itggaming.GameLanding.api.model.GameList
import com.example.itggaming.R
import com.google.android.material.tabs.TabLayout


class FeaturedItemViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
    private lateinit var carousalTitle:TextView
    private lateinit var dots:TabLayout
    private lateinit var recyclerView:RecyclerView
    private lateinit var  layoutManager: LinearLayoutManager
    private lateinit var timer: CountDownTimer
    private val scrollInterval:Long=2000
    var currentPosition=0
    var areTabSet=false

    var adapter= FeaturedItemViewpagerAdapter()
    private var itemCounter=0



    fun bind(list: GameList, position: Int) {
        recyclerView=itemView.findViewById(R.id.rv_featured_carousal)
        carousalTitle=itemView.findViewById(R.id.tv_featured_heading)
        dots=itemView.findViewById(R.id.tl_vp_tabs)
        carousalTitle.text=list.category
        currentPosition=0

        layoutManager=ProminentLayoutManager(itemView.context)
        recyclerView.layoutManager=layoutManager
        recyclerView.adapter=adapter
        recyclerView.setOnFlingListener(null);
        recyclerView.setItemViewCacheSize(3)
        val snapHelper=PagerSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)


        adapter.updateData(list.games, position)
        recyclerView.post {
            scrollToNextPosition()
        }

        itemCounter=adapter.itemCount/2
        dots.selectTab(dots.getTabAt(1))
        if(!areTabSet){
            addTabs(list)
        }
        timer=object :CountDownTimer(scrollInterval,1000){
            override fun onTick(millisUntilFinished: Long) {
                Log.v("PopularGames", millisUntilFinished.toString())
            }
            override fun onFinish() {
                scrollToNextPosition()
            }
        }
//        recyclerView.postDelayed(
//            Runnable {
//                scrollToNextPosition()
//            },4000
//        )

        recyclerView.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

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
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // User is manually scrolling, so remove auto-scroll callbacks
                    timer.start()
                    Log.v("PopularGames","Timer Start")
                } else {
                    // User has stopped scrolling, resume auto-scrolling
                    timer.cancel()
                    Log.v("PopularGames","Timer Stopped")
                }
                super.onScrollStateChanged(recyclerView, newState)
            }

        })


    }
    private fun addTabs(list: GameList) {
        var i=0
        if(dots.tabCount!=list.games.size){
            while(i<list.games.size){
                dots.addTab(dots.newTab())
                i++
            }
        }
        areTabSet=true
    }


    private fun scrollToNextPosition(){
        Log.v("PopularGames","Scrolling now")
        val currentPosition =
            (layoutManager ).findFirstCompletelyVisibleItemPosition()
        val nextPosition = (currentPosition +1)
        recyclerView.smoothScrollToPosition(nextPosition)
    }

    fun stopTimer(){
        timer.cancel()
    }

}