package com.example.practicumhomework_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MediaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.media)

        findViewById<Button>(R.id.return_button).setOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }
    }
}