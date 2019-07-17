package com.example.sigadmin.layouts.info.place

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.sigadmin.R
import kotlinx.android.synthetic.main.activity_fragment_first.view.*
import android.content.Intent
import android.util.Log
import com.example.sigadmin.layouts.info.main.MainFragmentActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.*


class ReadPlaceDetailActivity : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.activity_fragment_first, container, false)
        val tvName = root.findViewById<TextView>(R.id.tv_fnama_lapangan)
        val tvFacility = root.findViewById<TextView>(R.id.tv_ffasilitas)
        val tvJamBuka = root.findViewById<TextView>(R.id.tv_fjam_buka)
        val tvJamTutup = root.findViewById<TextView>(R.id.tv_fjam_tutup)
        val tvNoTelp = root.findViewById<TextView>(R.id.tv_fno_telepon)
        val tvLat = root.findViewById<TextView>(R.id.tv_flatitude)
        val tvLong = root.findViewById<TextView>(R.id.tv_flongitude)
        val tvAlamat = root.findViewById<TextView>(R.id.tv_falamat)

        val activity = activity as MainFragmentActivity

        val results = activity.getMyData()
        val placeId  = results.getString("placeId")
        val name = results.getString("name")
        val facility = results.getString("facility")
        val jamBuka = results.getString("jamBuka")
        val jamTutup = results.getString("jamTutup")
        val noTelp = results.getString("noTelp")
        val lat = results.getString("lat")
        val long = results.getString("long")
        val alamat = results.getString("alamat")

        tvName.text = name
        tvFacility.text = facility
        tvJamBuka.text = jamBuka
        tvJamTutup.text = jamTutup
        tvNoTelp.text = noTelp
        tvLat.text = lat
        tvLong.text = long
        tvAlamat.text = alamat

        val db = FirebaseFirestore.getInstance()
        val query = db.collection("Lapangan").document(placeId)
        val documentId = query.id

        root.btn_perbarui.setOnClickListener {
            val intent = Intent(getActivity(), UpdatePlaceActivity::class.java)
            intent.putExtra("placeId", documentId)
            intent.putExtra("name", name)
            intent.putExtra("facility", facility)
            intent.putExtra("alamat", alamat)
            intent.putExtra("jamBuka", jamBuka)
            intent.putExtra("jamTutup", jamTutup)
            intent.putExtra("lat", lat)
            intent.putExtra("long", long)
            intent.putExtra("noTelp", noTelp)
            clearFindViewByIdCache()
            startActivity(intent)
            Log.d("Meesss", documentId)
        }

        return root
    }
}