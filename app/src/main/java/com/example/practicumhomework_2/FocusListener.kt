package com.example.practicumhomework_2

import android.view.View
import android.view.View.OnFocusChangeListener

class FocusListener: OnFocusChangeListener {

    override fun onFocusChange(view: View, hasFocus: Boolean) {
        if (hasFocus) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }
}