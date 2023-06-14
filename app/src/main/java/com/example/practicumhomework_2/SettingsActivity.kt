package com.example.practicumhomework_2

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat

class SettingsActivity : AppCompatActivity() {


    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)

        findViewById<Button>(R.id.return_button).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        findViewById<SwitchCompat>(R.id.switcher).isChecked = resources.configuration.isNightModeActive
        findViewById<SwitchCompat>(R.id.switcher).setOnCheckedChangeListener { _, isChecked ->
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) {
                    AppCompatDelegate.MODE_NIGHT_YES
                }else{
                    AppCompatDelegate.MODE_NIGHT_NO
                })
        }
        findViewById<Button>(R.id.share_button).setOnClickListener {
            val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "https://practicum.yandex.ru/profile/android-developer/")
            type = "text/plain"
        }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent) }

        findViewById<Button>(R.id.support_button).setOnClickListener {
            val message = "Яндекс Практикум"
            val shareIntent = Intent(Intent.ACTION_SENDTO)
            shareIntent.data = Uri.parse("mailto:")
            shareIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("yourEmail@ya.ru"))
            shareIntent.putExtra(Intent.EXTRA_TEXT, message)
            startActivity(shareIntent)
        }
        findViewById<Button>(R.id.agreement_button).setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://yandex.ru/legal/practicum_offer/"))
            startActivity(browserIntent)
        }


    }
}