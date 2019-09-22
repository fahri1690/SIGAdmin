package com.example.sigadmin.layouts.place

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sigadmin.layouts.main.MainFragmentActivity
import com.example.sigadmin.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_update_place.*

class UpdatePlaceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_place)

        getData()

        pb_update_place.visibility = View.GONE

        btn_save_newField.setOnClickListener {
            updateData()
        }
    }

    private fun updateData() {

        pb_update_place.visibility = View.VISIBLE

        val placeId: String = intent.getStringExtra("placeId")
        val images = intent.getStringArrayListExtra("gambar")

        val db = FirebaseFirestore.getInstance()
        val query = db.collection("Lapangan").document(placeId)

        val name = et_updt_nama_tempat.text.toString()
        val facility = et_updt_fasilitas.text.toString()
        val alamat = et_updt_alamat.text.toString()
        val jamBuka = et_updt_jamBuka.text.toString()
        val jamTutup = et_updt_jamTutup.text.toString()
        val lat = et_updt_latitude.text.toString()
        val long = et_updt_longitude.text.toString()
        val noTelp = et_updt_noTelp.text.toString()

        var latToDouble:Double? = null
        var longToDouble: Double? = null

        if(lat.isNotEmpty())
            latToDouble = lat.toDouble()
        if(long.isNotEmpty())
            longToDouble = long.toDouble()

        if (name.isEmpty()) {
            Toast.makeText(this, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return
        } else if (facility.isEmpty()) {
            Toast.makeText(this, "Fasilitas tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return
        } else if (jamBuka.isEmpty()) {
            Toast.makeText(this, "Jam Buka tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return
        } else if (jamTutup.isEmpty()) {
            Toast.makeText(this, "Jam Tutup tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return
        } else if (noTelp.isEmpty()) {
            Toast.makeText(this, "Nomor Telepon tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return
        } else if (alamat.isEmpty()) {
            Toast.makeText(this, "Alamat tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return
        } else if (lat.isEmpty()) {
            Toast.makeText(this, "Latitude tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return
        } else if (long.isEmpty()) {
            Toast.makeText(this, "Longitude tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return
        } else if (latToDouble!! < -90 || latToDouble > 90) {
            Toast.makeText(this, "Latitude harus diantara -90 sampai 90", Toast.LENGTH_SHORT).show()
            return
        } else if (longToDouble!! < -180 || longToDouble > 180) {
            Toast.makeText(this,"Longitude harus diantara -180 sampai 180",Toast.LENGTH_SHORT).show()
        } else {

            val result = HashMap<String, Any?>()
            result["namaTempat"] = name
            result["fasilitas"] = facility
            result["jamBuka"] = jamBuka
            result["jamTutup"] = jamTutup
            result["noTelp"] = noTelp
            result["alamat"] = alamat
            result["latitude"] = latToDouble
            result["longitude"] = longToDouble
            result["gambar"] = images

            query.update(result)
                .addOnSuccessListener {
                    val intent = Intent(this, MainFragmentActivity::class.java)
                    intent.putExtra("placeId", placeId)
                    intent.putExtra("namaTempat", name)
                    intent.putExtra("fasilitas", facility)
                    intent.putExtra("alamat", alamat)
                    intent.putExtra("jamBuka", jamBuka)
                    intent.putExtra("jamTutup", jamTutup)
                    intent.putExtra("latitude", latToDouble)
                    intent.putExtra("longitude", longToDouble)
                    intent.putExtra("noTelp", noTelp)
                    intent.putStringArrayListExtra("gambar", images)
                    pb_update_place.visibility = View.GONE
                    finish()
                    startActivity(intent)
                }
                .addOnFailureListener { e ->
                    Log.w("Messages", "Error updating document", e)
                }
        }
    }

    private fun getData() {

        val names = intent.getStringExtra("namaTempat")
        val facilitys = intent.getStringExtra("fasilitas")
        val alamats = intent.getStringExtra("alamat")
        val jamBukas = intent.getStringExtra("jamBuka")
        val jamTutups = intent.getStringExtra("jamTutup")
        val lats = intent.getStringExtra("latitude")
        val longs = intent.getStringExtra("longitude")
        val noTelps = intent.getStringExtra("noTelp")

        val name = et_updt_nama_tempat
        val facility = et_updt_fasilitas
        val alamat = et_updt_alamat
        val jamBuka = et_updt_jamBuka
        val jamTutup = et_updt_jamTutup
        val lat = et_updt_latitude
        val long = et_updt_longitude
        val noTelp = et_updt_noTelp

        name.setText(names)
        facility.setText(facilitys)
        alamat.setText(alamats)
        jamBuka.setText(jamBukas)
        jamTutup.setText(jamTutups)
        lat.setText(lats)
        long.setText(longs)
        noTelp.setText(noTelps)
    }
}