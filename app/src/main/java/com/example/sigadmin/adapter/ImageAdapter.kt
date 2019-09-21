package com.example.sigadmin.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.sigadmin.layouts.place.ImageFragment

class ImageAdapter(fragmentManager: FragmentManager,
                   private val images: ArrayList<String>) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(pos: Int): Fragment {
        return ImageFragment.newInstance(images[pos])
    }

    override fun getCount(): Int = images.size

}