package com.example.practicumhomework_2.settings.presentation

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.practicumhomework_2.App
import com.example.practicumhomework_2.R
import com.example.practicumhomework_2.databinding.SettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: SettingsBinding
    private lateinit var viewModel: SettingsViewModel


    @RequiresApi(Build.VERSION_CODES.R)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, SettingsViewModelFactory((application as App).settingsPreferences))[SettingsViewModel::class.java]
        binding = SettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.returnButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        viewModel.isNightThemeState.observe(this){
            binding.switcher.isChecked = it
        }
        binding.switcher.setOnCheckedChangeListener { _, isChecked ->
            viewModel.switchTheme(isChecked)
        }
        binding.shareButton.setOnClickListener {
            val intent = viewModel.getShareLinkIntent(getString(R.string.share_email), getString(R.string.text_pattern))
            startActivity(intent)
        }

        binding.supportButton.setOnClickListener {
            val intent = viewModel.getSupportIntent(getString(R.string.message), getString(R.string.mailto), getString(R.string.email))
            startActivity(intent)
        }
        binding.agreementButton.setOnClickListener {
            val intent = viewModel.getAgreementIntent(getString(R.string.agreement_site_email))
            startActivity(intent)
        }


    }
}