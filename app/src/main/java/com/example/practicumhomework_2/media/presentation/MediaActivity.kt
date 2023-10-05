package com.example.practicumhomework_2.media.presentation

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.practicumhomework_2.R

class MediaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.media)

        findViewById<FrameLayout>(R.id.return_button).setOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }
    }
}