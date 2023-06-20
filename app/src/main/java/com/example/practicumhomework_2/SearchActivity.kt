package com.example.practicumhomework_2

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible

class SearchActivity : AppCompatActivity() {

    private val editText by lazy { findViewById<EditText>(R.id.EditText) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search)

        findViewById<ImageView>(R.id.return_button).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        val clearButton = findViewById<ImageView>(R.id.clear_button)
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                clearButton.isVisible = p0?.length != 0
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        }
        editText.addTextChangedListener(textWatcher)
        clearButton.setOnClickListener {
            editText.text.clear()
            inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("search_key", editText.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val restore = savedInstanceState.getString("search_key")
        editText.setText(restore)
    }
}

