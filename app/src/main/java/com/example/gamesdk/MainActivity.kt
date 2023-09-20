package com.example.gamesdk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.itggaming.GameLanding.GameLandingActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btn=findViewById<Button>(R.id.butn)
        btn.setOnClickListener {
            var intent= Intent(this, GameLandingActivity::class.java)
            startActivity(intent)
        }

    }
}