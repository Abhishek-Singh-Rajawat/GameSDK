package com.example.gamesdk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.itggaming.GameLanding.GameLandingActivity
import com.example.itggaming.GamingSdk
import com.example.itggaming.util.AppLanguage

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val url="https://abhishek-singh-rajawat.github.io/api2/includeEverything.json"

        var btn=findViewById<Button>(R.id.butn)
        btn.setOnClickListener {
            GamingSdk.launch(this,AppLanguage.HINDI,url)
        }

    }
}