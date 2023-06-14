package com.example.practicumhomework_2

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search)

        findViewById<Button>(R.id.return_button).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

}

