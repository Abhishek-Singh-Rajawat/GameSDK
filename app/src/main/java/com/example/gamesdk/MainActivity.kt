package com.example.gamesdk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.itggaming.GameLanding.GameLandingActivity
import com.example.itggaming.GamingSdk
import com.example.itggaming.util.AppLanguage
import com.example.itggaming.util.GamingLogCallbacks

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val url="https://abhishek-singh-rajawat.github.io/api2/includeEverything.json"

        var btn=findViewById<Button>(R.id.butn)
        btn.setOnClickListener {
            GamingSdk.launch(this,AppLanguage.HINDI,url, object :GamingLogCallbacks{
                override fun onCategoryClicked(name: String) {
                    Log.v("GamingSDK","Category Clicked "+name)
                }

                override fun onGameClicked(name: String) {
                    Log.v("GamingSDK","Game Clicked "+name)
                }
            })
        }

    }
}