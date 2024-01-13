package com.example.practicumhomework_2.createPlaylist.presentation

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.graphics.decodeBitmap
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.core.view.setPadding
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.practicumhomework_2.R
import com.example.practicumhomework_2.createPlaylist.data.ImageSaver
import com.example.practicumhomework_2.databinding.FragmentCreatePlaylistBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.android.ext.android.inject
import java.io.File
import java.io.FileOutputStream

class PlaylistCreateFragment : Fragment() {
    private var _binding: FragmentCreatePlaylistBinding? = null
    private val binding get() = _binding!!
    private val imageSaver: ImageSaver by inject()
    private var imageUri: Uri = Uri.EMPTY
    private var isImageChosen = false
    private val permissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) pickImages() else Toast.makeText(
                requireContext(),
                "The permission is needed",
                Toast.LENGTH_LONG
            ).show()
        }
    private val pickMediaLauncher =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                imageUri = uri
                isImageChosen = true
                binding.playlistCover.setPadding(0)
                Glide.with(this)
                    .load(uri)
                    .transform(
                        CenterCrop(),
                        RoundedCorners(resources.getDimensionPixelSize(R.dimen.track_poster_corner_radius))
                    )
                    .into(binding.playlistCover)
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
            if (binding.editText.text.isNullOrBlank() && binding.descriptionEditText.text.isNullOrBlank() && !isImageChosen) {
                findNavController().popBackStack()
            } else {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Завершить создание плейлиста?") // Заголовок диалога
                    .setMessage("Все несохраненные данные будут потеряны") // Описание диалога
                    .setNegativeButton("Отмена") { _, _ -> }
                    .setPositiveButton("Завершить") { _, _ -> findNavController().popBackStack() }
                    .show()
            }
        }
        binding.playlistCover.setOnClickListener {
            checkPermissions()
        }

        binding.createPlaylistButton.setOnClickListener {
            imageSaver.saveToInternal(imageUri, binding.editText.text.toString())
            findNavController().previousBackStackEntry?.savedStateHandle?.set(
                CREATE_RESULT,
                bundleOf("isSuccess" to true, "playlistName" to binding.editText.text.toString())
            )
            findNavController().popBackStack()
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
            if (isGranted(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                pickImages()
            } else {
                permissionsLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        } else {
            pickImages()
        }
    }

    fun isGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        const val CREATE_RESULT = "CREATE_RESULT"
    }
}