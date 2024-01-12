package com.example.practicumhomework_2.createPlaylist.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.practicumhomework_2.databinding.FragmentCreatePlaylistBinding

class PlaylistCreateFragment : Fragment() {
    private var _binding: FragmentCreatePlaylistBinding? = null
    private val binding get() = _binding!!
    private val permissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if(it) pickImages() else Toast.makeText(requireContext(), "The permission is needed", Toast.LENGTH_LONG).show()
        }
    private val pickMediaLauncher =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                Log.d("AAA", "Selected URI: $uri")
            } else {
                Log.d("AAA", "No media selected")
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreatePlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.editText.doOnTextChanged { text, _, _, _ ->
            binding.createPlaylistButton.isEnabled = !text.isNullOrBlank()
        }
        binding.returnButton.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.playlistCover.setOnClickListener {
            checkPermissions()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun pickImages() {
        pickMediaLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    fun checkPermissions() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S_V2) {
            if (isGranted(Manifest.permission.READ_MEDIA_IMAGES)) {
                pickImages()
            } else {
                permissionsLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
            }
        } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            if (isGranted(Manifest.permission.READ_EXTERNAL_STORAGE) ){
                pickImages()
            } else {
                permissionsLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        } else {
            pickImages()
        }
    }

    fun isGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_GRANTED
    }
}