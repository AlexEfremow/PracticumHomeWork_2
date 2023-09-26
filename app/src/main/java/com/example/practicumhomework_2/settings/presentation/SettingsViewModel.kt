package com.example.practicumhomework_2.settings.presentation

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.practicumhomework_2.settings.data.SettingsPreferences

class SettingsViewModel(private val settingsPreferences: SettingsPreferences): ViewModel() {

    private val _isNightThemeState = MutableLiveData<Boolean>()
    val isNightThemeState: LiveData<Boolean> = _isNightThemeState

    init {
        val currentTheme = settingsPreferences.getCurrentTheme()
        _isNightThemeState.value = currentTheme
    }

    fun switchTheme(darkThemeEnabled: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
        settingsPreferences.saveTheme(darkThemeEnabled)
    }
    fun getShareLinkIntent(email: String, type: String): Intent {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, email)
            this.type = type
        }

        return Intent.createChooser(sendIntent, null)
    }
    fun getSupportIntent(message: String, uri: String, email: String): Intent {
        val shareIntent = Intent(Intent.ACTION_SENDTO)
        shareIntent.data = Uri.parse(uri)
        shareIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        shareIntent.putExtra(Intent.EXTRA_TEXT, message)
        return shareIntent
    }
    fun getAgreementIntent(link: String): Intent {
        return Intent(Intent.ACTION_VIEW, Uri.parse(link))
    }
}