package com.example.practicumhomework_2.settings.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.practicumhomework_2.R
import com.example.practicumhomework_2.databinding.SettingsBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : Fragment() {
    private var _binding: SettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<SettingsViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isNightThemeState.collect {
                    binding.switcher.isChecked = it
                }
            }
        }
        binding.switcher.setOnCheckedChangeListener { _, isChecked ->
            viewModel.switchTheme(isChecked)
        }
        binding.shareButton.setOnClickListener {
            val intent = viewModel.getShareLinkIntent(
                getString(R.string.share_email),
                getString(R.string.text_pattern)
            )
            startActivity(intent)
        }

        binding.supportButton.setOnClickListener {
            val intent = viewModel.getSupportIntent(
                getString(R.string.message),
                getString(R.string.mailto),
                getString(R.string.email)
            )
            startActivity(intent)
        }
        binding.agreementButton.setOnClickListener {
            val intent = viewModel.getAgreementIntent(getString(R.string.agreement_site_email))
            startActivity(intent)
        }


    }
}