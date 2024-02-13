package com.example.gamesdk

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.itg.itggaming.GamingSdk
import com.itg.itggaming.util.GamingLogCallbacks

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val url = "https://akm-img-a-in.tosshub.com/app/at-app/app_config_imp/gamelist/ITG_gameList_data.json?v=1.0"

        var btn = findViewById<Button>(R.id.butn)
        btn.setOnClickListener {
            GamingSdk.launch(this, com.itg.itggaming.util.AppLanguage.HINDI, url,"" ,object :
                GamingLogCallbacks {
                override fun onCategoryClicked(name: String) {
                    Log.v("GamingSDK", "Category Clicked " + name)
                }

                override fun onGameClicked(name: String) {
                    Log.v("GamingSDK", "Game Clicked " + name)
                }
            })
        }

    }
}