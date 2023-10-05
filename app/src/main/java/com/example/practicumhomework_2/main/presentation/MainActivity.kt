package com.example.practicumhomework_2.main.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.practicumhomework_2.R
import com.example.practicumhomework_2.media.presentation.MediaActivity
import com.example.practicumhomework_2.search.presentation.SearchActivity
import com.example.practicumhomework_2.settings.presentation.SettingsActivity

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        findViewById<Button>(R.id.button_search).setOnClickListener {
            val searchIntent = Intent(this, SearchActivity::class.java)
            startActivity(searchIntent)
        }
        findViewById<Button>(R.id.button_media).setOnClickListener {
            val mediaIntent = Intent(this, MediaActivity::class.java)
            startActivity(mediaIntent)
        }
        findViewById<Button>(R.id.button_settings).setOnClickListener {
            val settingsIntent = Intent(this, SettingsActivity::class.java)
            startActivity(settingsIntent)
        }

    }
}