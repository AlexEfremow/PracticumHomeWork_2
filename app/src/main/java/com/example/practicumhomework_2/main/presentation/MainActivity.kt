package com.example.practicumhomework_2.main.presentation

import android.content.res.ColorStateList
import android.content.res.XmlResourceParser
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.practicumhomework_2.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val bottomNavigationView by lazy { findViewById<BottomNavigationView>(R.id.bottomNavigationView) }
    private val separator by lazy { findViewById<View>(R.id.separator) }
    private val fragmentList = listOf(R.id.playerFragment, R.id.playlistCreateFragment, R.id.playlistFragment)

    private val destinationListener = NavController.OnDestinationChangedListener { _, destination, _ ->
        if(destination.id in fragmentList) {
            bottomNavigationView.visibility = View.GONE
            separator.visibility = View.GONE
        } else {
            bottomNavigationView.visibility = View.VISIBLE
            separator.visibility = View.VISIBLE
        }
    }

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener(destinationListener)

        bottomNavigationView.setupWithNavController(navController)
    }

    override fun onDestroy() {
        super.onDestroy()
        navController.removeOnDestinationChangedListener(destinationListener)
    }
}