package com.example.practicumhomework_2.settings.presentation

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.practicumhomework_2.App
import com.example.practicumhomework_2.R
import com.google.android.material.switchmaterial.SwitchMaterial
class SettingsActivity : AppCompatActivity() {


    @RequiresApi(Build.VERSION_CODES.R)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)
        val preferences = (application as App).settingsPreferences

        findViewById<FrameLayout>(R.id.return_button).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        findViewById<SwitchMaterial>(R.id.switcher).isChecked = preferences.getCurrentTheme()
        findViewById<SwitchMaterial>(R.id.switcher).setOnCheckedChangeListener { _, isChecked ->
            (applicationContext as App).switchTheme(isChecked)
            preferences.saveTheme(isChecked)
        }
        findViewById<LinearLayout>(R.id.share_button).setOnClickListener {
            val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, getString(R.string.share_email))
            type = getString(R.string.text_pattern)
        }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent) }

        findViewById<LinearLayout>(R.id.support_button).setOnClickListener {
            val message = getString(R.string.message)
            val shareIntent = Intent(Intent.ACTION_SENDTO)
            shareIntent.data = Uri.parse(getString(R.string.mailto))
            shareIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.email)))
            shareIntent.putExtra(Intent.EXTRA_TEXT, message)
            startActivity(shareIntent)
        }
        findViewById<LinearLayout>(R.id.agreement_button).setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.agreement_site_email)))
            startActivity(browserIntent)
        }


    }
}