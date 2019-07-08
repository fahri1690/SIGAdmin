package com.example.sigadmin

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.sigadmin.ui.main.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

        val listId = intent.getStringExtra("listId")

        Log.d("ListID", "$listId")

    }

    fun getMyData(): Bundle {

        val name = intent.getStringExtra("name")
        val facility = intent.getStringExtra("facility")
        val openHour = intent.getStringExtra("jamBuka")
        val closeHour = intent.getStringExtra("jamTutup")
        val phone = intent.getStringExtra("noTelp")
        val addr = intent.getStringExtra("alamat")
        val lat = intent.getStringExtra("lat")
        val lng = intent.getStringExtra("long")

        val b = Bundle()
        b.putString("name", name)
        b.putString("facility", facility)
        b.putString("jamBuka", openHour)
        b.putString("jamTutup", closeHour)
        b.putString("noTelp", phone)
        b.putString("alamat", addr)
        b.putString("lat", lat)
        b.putString("long", lng)

        return b

    }
}