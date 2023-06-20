package com.example.practicumhomework_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class MediaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.media)

        findViewById<ImageView>(R.id.return_button).setOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }
    }
}