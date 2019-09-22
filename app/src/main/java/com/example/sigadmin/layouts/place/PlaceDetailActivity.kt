package com.example.sigadmin.layouts.place

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sigadmin.R
import com.example.sigadmin.carousel.BannerCarouselItem
import com.example.sigadmin.layouts.main.MainFragmentActivity
import com.example.sigadmin.services.db.GetDb
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import kotlinx.android.synthetic.main.activity_place_detail.view.*
import java.util.ArrayList


class PlaceDetailActivity : Fragment(){

    private var groupAdapter = GroupAdapter<com.xwray.groupie.kotlinandroidextensions.ViewHolder>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.activity_place_detail, container, false)
        val tvName = root.findViewById<TextView>(R.id.tv_nama_tempat)
        val tvFacility = root.findViewById<TextView>(R.id.tv_fasilitas)
        val tvJamBuka = root.findViewById<TextView>(R.id.tv_jam_buka)
        val tvJamTutup = root.findViewById<TextView>(R.id.tv_jam_tutup)
        val tvNoTelp = root.findViewById<TextView>(R.id.tv_no_telepon)
        val tvLat = root.findViewById<TextView>(R.id.tv_latitude)
        val tvLong = root.findViewById<TextView>(R.id.tv_longitude)
        val tvAlamat = root.findViewById<TextView>(R.id.tv_alamat)

        val activity = activity as MainFragmentActivity

        val results = activity.getMyData()
        val placeId  = results.getString("placeId")
        val name = results.getString("namaTempat")
        val facility = results.getString("fasilitas")
        val jamBuka = results.getString("jamBuka")
        val jamTutup = results.getString("jamTutup")
        val noTelp = results.getString("noTelp")
        val lat = results.getString("latitude")
        val long = results.getString("longitude")
        val alamat = results.getString("alamat")
        val imageList = results.getStringArrayList("gambar")

        tvName.text = name
        tvFacility.text = facility
        tvJamBuka.text = jamBuka
        tvJamTutup.text = jamTutup
        tvNoTelp.text = noTelp
        tvLat.text = lat
        tvLong.text = long
        tvAlamat.text = alamat

        val query = GetDb().collection.document(placeId!!.toString())
        val documentId = query.id

        root.btn_update_place.setOnClickListener {
            val intent = Intent(getActivity(), UpdatePlaceActivity::class.java)
            intent.putExtra("placeId", documentId)
            intent.putExtra("namaTempat", name)
            intent.putExtra("fasilitas", facility)
            intent.putExtra("alamat", alamat)
            intent.putExtra("jamBuka", jamBuka)
            intent.putExtra("jamTutup", jamTutup)
            intent.putExtra("latitude", lat)
            intent.putExtra("longitude", long)
            intent.putExtra("noTelp", noTelp)
            intent.putStringArrayListExtra("gambar", imageList)
            startActivity(intent)
            Log.d("Meesss", documentId)
        }

        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS"
        ) val listImages: ArrayList<String> = imageList

        root.rvImagesMain.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = groupAdapter
        }

        // declare banner carousel
        val bannerCarouselItem = BannerCarouselItem(listImages, activity.supportFragmentManager)
        groupAdapter.add(bannerCarouselItem)

        Section().apply {
            groupAdapter.add(this)
        }

        return root
    }
}