package com.example.practicumhomework_2.media.presentation

import android.os.Bundle
import android.provider.MediaStore.Audio.Media
import android.widget.FrameLayout
import android.widget.TableLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.practicumhomework_2.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MediaActivity : AppCompatActivity() {
    lateinit var tabMediator: TabLayoutMediator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.media)
        val mediaAdapter = MediaAdapter(this)
        val viewPager = findViewById<ViewPager2>(R.id.view_pager)
        viewPager.adapter = mediaAdapter
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        tabMediator = TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when(position) {
                0 -> tab.text = "Избранные треки"
                1 -> tab.text = "Плейлисты"
            }
        }
        tabMediator.attach()

        findViewById<FrameLayout>(R.id.return_button).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onDestroy() {
        tabMediator.detach()
        super.onDestroy()
    }

}