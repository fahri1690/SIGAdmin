package com.example.sigadmin.layouts.info.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.sigadmin.R
import com.example.sigadmin.layouts.home.HomeAdminActivity
import com.google.android.material.tabs.TabLayout

class MainFragmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_main)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

    }

    fun getMyData(): Bundle {

        val placeId = intent.getStringExtra("placeId")
        val name = intent.getStringExtra("name")
        val facility = intent.getStringExtra("facility")
        val openHour = intent.getStringExtra("jamBuka")
        val closeHour = intent.getStringExtra("jamTutup")
        val phone = intent.getStringExtra("noTelp")
        val addr = intent.getStringExtra("alamat")
        val lat = intent.getStringExtra("lat")
        val lng = intent.getStringExtra("long")

        val bundle = Bundle()

        bundle.putString("placeId", placeId)
        bundle.putString("name", name)
        bundle.putString("facility", facility)
        bundle.putString("jamBuka", openHour)
        bundle.putString("jamTutup", closeHour)
        bundle.putString("noTelp", phone)
        bundle.putString("alamat", addr)
        bundle.putString("lat", lat)
        bundle.putString("long", lng)

        return bundle

    }

    fun getList():Bundle {
        val placeId = intent.getStringExtra("placeId")
        val bundle = Bundle()
        bundle.putString("placeId", placeId)

        return bundle
    }

    override fun onBackPressed() {
        startActivity(Intent(this, HomeAdminActivity::class.java))
    }
}