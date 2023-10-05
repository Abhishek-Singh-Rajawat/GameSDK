package com.example.itggaming

import android.app.Activity
import android.content.Intent
import com.example.itggaming.GameLanding.GameLandingActivity
import com.example.itggaming.util.AppLanguage
import com.example.itggaming.util.GameConstants
import com.example.itggaming.util.GamingLogCallbacks

object GamingSdk {
    fun launch(activity: Activity, appLanguage: AppLanguage, baseUrl:String, gamingLogCallbacks: GamingLogCallbacks){
        val intent= Intent(activity, GameLandingActivity::class.java)
        var lang = when (appLanguage){
            AppLanguage.HINDI-> {
                "hi"
            }

            else->{
                "en"
            }
        }
        intent.putExtra(GameConstants.BASE_URL,baseUrl)
        intent.putExtra(GameConstants.LANGUAGE,lang)
        intent.putExtra(GameConstants.GamingLogCallbacks,gamingLogCallbacks)
        activity.startActivity(intent)
    }
}