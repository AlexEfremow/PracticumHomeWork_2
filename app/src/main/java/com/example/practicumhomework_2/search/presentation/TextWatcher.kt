package com.example.practicumhomework_2.search.presentation

import android.text.Editable
import android.text.TextWatcher

class TextWatcher(private val onChanged: (String) -> Unit) : TextWatcher {

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        onChanged.invoke(p0.toString())
    }

    override fun afterTextChanged(p0: Editable?) {}
}