package com.example.sigadmin.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.sigadmin.layouts.info.place.ImageFragment
import com.example.sigadmin.models.PlaceImages

class ImageAdapter(fragmentManager: FragmentManager,
                   private val images: List<PlaceImages>) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(pos: Int): Fragment {
        return ImageFragment.newInstance(images[pos].images)
    }

    override fun getCount(): Int = images.size

}