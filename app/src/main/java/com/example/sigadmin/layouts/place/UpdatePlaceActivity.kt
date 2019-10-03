package com.example.sigadmin.layouts.place

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sigadmin.R
import com.example.sigadmin.layouts.main.MainFragmentActivity
import com.example.sigadmin.services.db.GetDb
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

        val placeId: String = intent.getStringExtra("placeId")
        val images = intent.getStringArrayListExtra("gambar")

        val query = GetDb().collection.document(placeId)

        val name = et_updt_nama_tempat.text.toString()
        val facility = et_updt_fasilitas.text.toString()
        val alamat = et_updt_alamat.text.toString()
        val jamBuka = et_updt_jamBuka.text.toString()
        val jamTutup = et_updt_jamTutup.text.toString()
        val lat = et_updt_latitude.text.toString()
        val long = et_updt_longitude.text.toString()
        val noTelp = et_updt_noTelp.text.toString()
        val jenisLapangan = et_updt_jenisLapangan.text.toString()
        val hargaTerendah = et_updt_harga_terendah.text.toString()
        val hargaTertinggi = et_updt_harga_tertinggi.text.toString()

        var latToDouble:Double? = null
        var longToDouble: Double? = null

        if(lat.isNotEmpty())
            latToDouble = lat.toDouble()
        if(long.isNotEmpty())
            longToDouble = long.toDouble()

        var lowestPrice: Int? = null
        var highestPrice: Int? = null

        if (hargaTerendah.isNotEmpty()) {
            lowestPrice = hargaTerendah.toInt()
        }

        if (hargaTertinggi.isNotEmpty()) {
            highestPrice = hargaTertinggi.toInt()
        }

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
        }else if (latToDouble!! < -90 || latToDouble > 90) {
            Toast.makeText(this, "Latitude harus diantara -90 sampai 90", Toast.LENGTH_SHORT).show()
            return
        } else if (long.isEmpty()) {
            Toast.makeText(this, "Longitude tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return
        } else if (longToDouble!! < -180 || longToDouble > 180) {
            Toast.makeText(this,"Longitude harus diantara -180 sampai 180",Toast.LENGTH_SHORT).show()
            return
        } else if (jenisLapangan.isEmpty()) {
            Toast.makeText(this, "Jenis Lapangan tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return
        } else if (hargaTerendah.isEmpty()) {
            Toast.makeText(this, "Harga Terendah tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return
        } else if (lowestPrice == null) {
            Toast.makeText(this, "Harga Terendah tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return
        } else if (hargaTertinggi.isEmpty()) {
            Toast.makeText(this, "Harga Tertinggi tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return
        } else if (highestPrice == null) {
            Toast.makeText(this, "Harga Tertinggi tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return
        } else {

            pb_update_place.visibility = View.VISIBLE

            val result = HashMap<String, Any?>()
            result["namaTempat"] = name
            result["fasilitas"] = facility
            result["jamBuka"] = jamBuka
            result["jamTutup"] = jamTutup
            result["noTelp"] = noTelp
            result["alamat"] = alamat
            result["latitude"] = latToDouble
            result["longitude"] = longToDouble
            result["jenisLapangan"] = jenisLapangan
            result["hargaTerendah"] = lowestPrice
            result["hargaTertinggi"] = highestPrice
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
                    intent.putExtra("jenisLapangan", jenisLapangan)
                    intent.putExtra("hargaTerendah", lowestPrice)
                    intent.putExtra("hargaTertinggi", highestPrice)
                    intent.putStringArrayListExtra("gambar", images)
                    pb_update_place.visibility = View.GONE
                    finish()
                    startActivity(intent)
                }
                .addOnFailureListener { e ->
                    Log.w("Messages", "Error updating document", e)
                    pb_update_place.visibility = View.GONE
                }
        }
    }

    private fun getData() {

        val placeId = intent.getStringExtra("placeId")

        val query = GetDb().collection.document(placeId)

        query.get().addOnSuccessListener {
            val name = it.data?.get("namaTempat").toString()
            val facility = it.data?.get("fasilitas").toString()
            val jamBuka = it.data?.get("jamBuka").toString()
            val jamTutup = it.data?.get("jamTutup").toString()
            val noTelp = it.data?.get("noTelp").toString()
            val lat = it.data?.get("latitude").toString()
            val long = it.data?.get("longitude").toString()
            val alamat = it.data?.get("alamat").toString()
            val jenisLapangan = it.data?.get("jenisLapangan").toString()
            val hargaTerendah = it.data?.get("hargaTerendah").toString()
            val hargaTertinggi = it.data?.get("hargaTertinggi").toString()

            et_updt_nama_tempat.setText(name)
            et_updt_fasilitas.setText(facility)
            et_updt_alamat.setText(alamat)
            et_updt_jamBuka.setText(jamBuka)
            et_updt_jamTutup.setText(jamTutup)
            et_updt_latitude.setText(lat)
            et_updt_longitude.setText(long)
            et_updt_noTelp.setText(noTelp)
            et_updt_jenisLapangan.setText(jenisLapangan)
            et_updt_harga_terendah.setText(hargaTerendah)
            et_updt_harga_tertinggi.setText(hargaTertinggi)
        }

    }
}