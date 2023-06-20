package com.example.practicumhomework_2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        findViewById<Button>(R.id.button_search).setOnClickListener{
            val searchIntent = Intent(this, SearchActivity::class.java)
            startActivity(searchIntent)
        }
        findViewById<Button>(R.id.button_media).setOnClickListener{
            val mediaIntent = Intent(this, MediaActivity::class.java)
            startActivity(mediaIntent)
        }
        findViewById<Button>(R.id.button_settings).setOnClickListener{
            val settingsIntent = Intent(this, SettingsActivity::class.java)
            startActivity(settingsIntent)
        }

    }
}