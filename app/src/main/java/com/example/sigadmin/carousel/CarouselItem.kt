package com.example.sigadmin.carousel

import androidx.fragment.app.FragmentManager
import com.example.sigadmin.R
import com.example.sigadmin.adapter.ImageAdapter
import com.example.sigadmin.models.PlaceImages
import com.example.sigadmin.models.PlaceModel
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_carousel_images.view.*

class BannerCarouselItem(private val banners: ArrayList<String>,
                         private val supportFragmentManager: FragmentManager
) : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        val viewPagerAdapter = ImageAdapter(supportFragmentManager, banners)
        viewHolder.itemView.viewPagerImages.adapter = viewPagerAdapter
        viewHolder.itemView.indicator.setViewPager(viewHolder.itemView.viewPagerImages)
    }

    override fun getLayout(): Int = R.layout.item_carousel_images
}