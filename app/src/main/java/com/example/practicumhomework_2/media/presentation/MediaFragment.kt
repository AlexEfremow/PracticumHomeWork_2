package com.example.practicumhomework_2.media.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.practicumhomework_2.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MediaFragment: Fragment() {
    lateinit var tabMediator: TabLayoutMediator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.media, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mediaAdapter = MediaAdapter(requireActivity())
        val viewPager = view.findViewById<ViewPager2>(R.id.view_pager)
        viewPager.adapter = mediaAdapter
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)
        tabMediator = TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when(position) {
                0 -> tab.text = "Избранные треки"
                1 -> tab.text = "Плейлисты"
            }
        }
        tabMediator.attach()

    }

    override fun onDestroyView() {
        tabMediator.detach()
        super.onDestroyView()
    }
}