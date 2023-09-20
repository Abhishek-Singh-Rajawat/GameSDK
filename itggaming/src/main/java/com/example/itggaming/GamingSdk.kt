package com.example.itggaming

import android.app.Activity
import android.content.Intent
import com.example.itggaming.GameLanding.GameLandingActivity
import com.example.itggaming.util.AppLanguage
import com.example.itggaming.util.GameConstants

object GamingSdk {
    fun launch(activity: Activity, appLanguage: AppLanguage, baseUrl:String){
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
        activity.startActivity(intent)
    }
}