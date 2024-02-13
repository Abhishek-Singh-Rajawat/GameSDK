package com.itg.itggaming

import android.app.Activity
import android.content.Intent
import com.itg.itggaming.gameLanding.GameLandingActivity
import com.itg.itggaming.util.GameConstants
import com.itg.itggaming.util.GamingCallback
import com.itg.itggaming.util.GamingLogCallbacks

object GamingSdk {
    fun launch(activity: Activity, appLanguage: com.itg.itggaming.util.AppLanguage, baseUrl:String, sharedID:String?, gamingLogCallbacks: GamingLogCallbacks){
        val intent= Intent(activity, GameLandingActivity::class.java)
        val lang = when (appLanguage){
            com.itg.itggaming.util.AppLanguage.HINDI-> {
                "hi"
            }

            else->{
                "en"
            }
        }
        intent.putExtra(GameConstants.SHARED_ID,sharedID)
        intent.putExtra(GameConstants.BASE_URL,baseUrl)
        intent.putExtra(GameConstants.LANGUAGE,lang)
        GamingCallback.logCallbacks=gamingLogCallbacks
//        intent.putExtra(GameConstants.GamingLogCallbacks,gamingLogCallbacks)
        activity.startActivity(intent)
    }
}