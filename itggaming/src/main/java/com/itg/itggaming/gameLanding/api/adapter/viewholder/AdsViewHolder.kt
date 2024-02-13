package com.itg.itggaming.gameLanding.api.adapter.viewholder

import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.itg.itggaming.R
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
//            mAdView.adUnitId="ca-app-pub-3940256099942544/6300978111"
            if(adUnitId!=null && adUnitId!=""){
                mAdView.adUnitId=adUnitId
            }
            val adContainer=itemView.findViewById<LinearLayout>(R.id.ad_container)
            val shimmerLayout=itemView.findViewById<ShimmerFrameLayout>(R.id.shimmer)
            adContainer.removeAllViews()
            adContainer.addView(mAdView)
            val adRequest =
                AdManagerAdRequest.Builder()
                    .build()

            mAdView!!.adListener = object : AdListener() {
                override fun onAdLoaded() {
                    // Code to be executed when an ad finishes loading.
                    shimmerLayout.visibility=View.GONE
                    isLoaded=true

                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    shimmerLayout.visibility=View.GONE
                }
            }
            mAdView?.loadAd(adRequest)
        }
    }
}