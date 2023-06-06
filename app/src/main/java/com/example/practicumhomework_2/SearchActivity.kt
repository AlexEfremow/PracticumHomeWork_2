package com.example.practicumhomework_2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search)

        findViewById<Button>(R.id.back_button).setOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }

    }


}