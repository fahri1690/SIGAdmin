package com.example.sigadmin.layouts.place

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.request.RequestOptions
import com.example.sigadmin.R
import com.example.sigadmin.layouts.main.MainFragmentActivity
import com.example.sigadmin.services.db.GetDb
import com.glide.slider.library.SliderLayout
import com.glide.slider.library.SliderTypes.BaseSliderView
import com.glide.slider.library.SliderTypes.TextSliderView
import kotlinx.android.synthetic.main.activity_place_detail.view.*
import java.util.*


class PlaceDetailActivity : Fragment(), BaseSliderView.OnSliderClickListener{
    override fun onSliderClick(slider: BaseSliderView?) {

    }

    @SuppressLint("CheckResult")
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
        val tvJenisLapangan = root.findViewById<TextView>(R.id.tv_jenisLapangan)
        val tvHargaTerendah = root.findViewById<TextView>(R.id.tv_harga_terendah)
        val tvHargaTertinggi = root.findViewById<TextView>(R.id.tv_harga_tertinggi)


        val activity = activity as MainFragmentActivity

        val results = activity.getMyData()
        val placeId  = results.getString("placeId")

        val imageList = results.getStringArrayList("gambar")


        val query = GetDb().collection.document(placeId!!.toString())
        val documentId = query.id

        query.get().addOnSuccessListener {
            tvName.text = it.data?.get("namaTempat").toString()
            tvFacility.text = it.data?.get("fasilitas").toString()
            tvJamBuka.text = it.data?.get("jamBuka").toString()
            tvJamTutup.text = it.data?.get("jamTutup").toString()
            tvNoTelp.text = it.data?.get("noTelp").toString()
            tvLat.text = it.data?.get("latitude").toString()
            tvLong.text = it.data?.get("longitude").toString()
            tvAlamat.text = it.data?.get("alamat").toString()
            tvJenisLapangan.text = it.data?.get("jenisLapangan").toString()
            tvHargaTerendah.text = it.data?.get("hargaTerendah").toString()
            tvHargaTertinggi.text = it.data?.get("hargaTertinggi").toString()
        }

        root.btn_update_place.setOnClickListener {
            val intent = Intent(getActivity(), UpdatePlaceActivity::class.java)
            intent.putExtra("placeId", documentId)
            intent.putStringArrayListExtra("gambar", imageList)
            startActivity(intent)
            Log.d("Meesss", documentId)
        }

        val name = results.getString("namaTempat")
        val lat = results.getString("latitude")
        val long = results.getString("longitude")

        root.ic_maps.setOnClickListener {
            val intent = Intent(getActivity(), MapsActivity::class.java)
            intent.putExtra(MapsActivity(). extraName, name)
            intent.putExtra(MapsActivity(). extraLat, lat)
            intent.putExtra(MapsActivity(). extraLong, long)

            println("LATITIDE = $lat")
            println("LONGITUDE = $long")

            startActivity(intent)
        }

        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS"
        ) val listImages: ArrayList<String> = imageList

        val requestOptions = RequestOptions()

        val slide = root.findViewById<SliderLayout>(R.id.carousels)

        requestOptions.centerCrop()

        // Background image
        for (i in 0 until listImages.size) {
            val textSliderView =
                TextSliderView(context)
            // initialize a SliderLayout

            textSliderView
                .image(listImages[i])
                .setRequestOption(requestOptions)
                .setProgressBarVisible(false)
            //add your extra information

            textSliderView.bundle(Bundle())
            slide.addSlider(textSliderView)
        }
        slide.setPresetTransformer(SliderLayout.Transformer.Fade)
        slide.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom)
        slide.setDuration(2500)

        return root
    }
}