package com.example.practicumhomework_2

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SearchActivity : AppCompatActivity() {
    companion object {
        const val PRODUCT_AMOUNT = "PRODUCT_AMOUNT"
    }
    var value = R.id.EditText
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(PRODUCT_AMOUNT, value)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search)
        if (savedInstanceState != null) {
            value = savedInstanceState.getInt(PRODUCT_AMOUNT,0)
        }

        findViewById<Button>(R.id.back_button).setOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }

    }

}

