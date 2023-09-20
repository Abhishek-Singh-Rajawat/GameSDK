package com.example.itggaming.GameLanding.api.adapter.viewholder

import android.content.Context
import android.content.res.Configuration
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.itggaming.R
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.admanager.AdManagerAdRequest

class AdsViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
    lateinit var mAdView:AdView
    var isLoaded=false
    fun bind(adUnitId: String) {
        if (!isLoaded){
            mAdView = AdView(itemView.context)
            mAdView?.setAdSize(AdSize.MEDIUM_RECTANGLE)
            mAdView.adUnitId="ca-app-pub-3940256099942544/6300978111"
            if(adUnitId!=null && adUnitId!=""){
                mAdView.adUnitId=adUnitId
            }
            var adContainer=itemView.findViewById<LinearLayout>(R.id.ad_container)
            val shimmerLayout=itemView.findViewById<ShimmerFrameLayout>(R.id.shimmer)
            adContainer.removeAllViews()
            adContainer.addView(mAdView)
            val adRequest =
                AdManagerAdRequest.Builder()
                    .build()

            mAdView!!.adListener = object : AdListener() {
                override fun onAdLoaded() {
                    // Code to be executed when an ad finishes loading.
                    itemView.visibility = View.VISIBLE
                    adContainer.visibility=View.VISIBLE
                    shimmerLayout.visibility=View.GONE
                    isLoaded=true

                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    // Code to be executed when an ad request fails.
                    itemView.visibility = View.GONE
                    shimmerLayout.visibility=View.GONE
                }

                override fun onAdOpened() {
                    // Code to be executed when an ad opens an overlay that
                    // covers the screen.
                }

                override fun onAdClicked() {
                    // Code to be executed when the user clicks on an ad.
                }

                override fun onAdClosed() {
                    // Code to be executed when the user is about to return
                    // to the app after tapping on an ad.
                }
            }
            mAdView?.loadAd(adRequest)
        }


//        if(isTablet(itemView.context)){
//        }
//        else{
//            mAdView?.setAdSize(AdSize(AdSize.FULL_WIDTH,AdSize.AUTO_HEIGHT))
//        }
    }
    fun isTablet(context: Context?): Boolean {
        var isTablet = false
        if (null != context && context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK
            >= Configuration.SCREENLAYOUT_SIZE_LARGE
        ) {
            isTablet = true
        }
        return isTablet
    }
}