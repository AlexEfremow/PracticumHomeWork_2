package com.example.practicumhomework_2.settings.presentation

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import com.example.practicumhomework_2.settings.domain.SettingsInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsViewModel(private val interactor: SettingsInteractor): ViewModel() {

    private val _isNightThemeState = MutableStateFlow(interactor.currentTheme)
    val isNightThemeState: StateFlow<Boolean> = _isNightThemeState.asStateFlow()

    fun switchTheme(darkThemeEnabled: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
        interactor.saveTheme(darkThemeEnabled)
        _isNightThemeState.value = darkThemeEnabled
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