package com.example.sigadmin.layouts.place

import android.content.Intent
import android.os.Bundle
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

        btn_save_updt_place.setOnClickListener {
            updateData()
        }

        back_from_updt_place.setOnClickListener {
            onBackPressed()
        }
    }

    private fun updateData() {

        val placeId: String = intent.getStringExtra("placeId")
//        val images = intent.getStringArrayListExtra("gambar")

        val query = GetDb().collection.document(placeId)

        val name = et_updt_nama_tempat.text.toString()
        val facility = et_updt_fasilitas.text.toString()
        val alamat = et_updt_alamat.text.toString()

        val hBuka = spn_updt_jam_buka.selectedItem as String
        val mBuka = spn_updt_menit_buka.selectedItem as String
        val jamBuka = "$hBuka:$mBuka"

        val hTutup = spn_updt_jam_tutup.selectedItem as String
        val mTutup = spn_updt_menit_tutup.selectedItem as String
        val jamTutup = "$hTutup:$mTutup"

        val lat = et_updt_latitude.text.toString()
        val long = et_updt_longitude.text.toString()
        val noTelp = et_updt_noTelp.text.toString()

        var jnsLapangan = ""

        if (cb_updt_sintetis.isChecked && !cb_updt_vinyl.isChecked) {
            jnsLapangan = "Sintetis"
        } else if (cb_updt_vinyl.isChecked && !cb_updt_sintetis.isChecked) {
            jnsLapangan = "Vinyl"
        } else if (cb_updt_sintetis.isChecked && cb_updt_vinyl.isChecked) {
            jnsLapangan = "Vinyl & Sintetis"
        }

        val jenisLapangan = jnsLapangan
        val hargaTerendah = et_updt_harga_terendah.text.toString()
        val hargaTertinggi = et_updt_harga_tertinggi.text.toString()

        var latToDouble: Double? = null
        var longToDouble: Double? = null

        if (lat.isNotEmpty())
            latToDouble = lat.toDouble()
        if (long.isNotEmpty())
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
            et_updt_nama_tempat.setBackgroundResource(R.drawable.err_outline_stroke)
            return
        }  else if (noTelp.isEmpty()) {
            Toast.makeText(this, "Nomor Telepon tidak boleh kosong", Toast.LENGTH_SHORT).show()
            et_updt_noTelp.setBackgroundResource(R.drawable.err_outline_stroke)
            return
        } else if (alamat.isEmpty()) {
            Toast.makeText(this, "Alamat tidak boleh kosong", Toast.LENGTH_SHORT).show()
            et_updt_alamat.setBackgroundResource(R.drawable.err_outline_stroke)
            return
        } else if (lat.isEmpty()) {
            Toast.makeText(this, "Latitude tidak boleh kosong", Toast.LENGTH_SHORT).show()
            et_updt_latitude.setBackgroundResource(R.drawable.err_outline_stroke)
            return
        } else if (latToDouble!! < -90 || latToDouble > 90) {
            Toast.makeText(this, "Latitude harus diantara -90 sampai 90", Toast.LENGTH_SHORT).show()
            et_updt_latitude.setBackgroundResource(R.drawable.err_outline_stroke)
            return
        } else if (long.isEmpty()) {
            Toast.makeText(this, "Longitude tidak boleh kosong", Toast.LENGTH_SHORT).show()
            et_updt_longitude.setBackgroundResource(R.drawable.err_outline_stroke)
            return
        } else if (longToDouble!! < -180 || longToDouble > 180) {
            Toast.makeText(this, "Longitude harus diantara -180 sampai 180", Toast.LENGTH_SHORT)
                .show()
            et_updt_longitude.setBackgroundResource(R.drawable.err_outline_stroke)
            return
        } else if (jenisLapangan.isEmpty()) {
            Toast.makeText(this, "Wajib pilih minimal 1 jenis lapangan", Toast.LENGTH_SHORT).show()
            return
        } else if (hargaTerendah.isEmpty()) {
            Toast.makeText(this, "Harga Terendah tidak boleh kosong", Toast.LENGTH_SHORT).show()
            et_updt_harga_terendah.setBackgroundResource(R.drawable.err_outline_stroke)
            return
        } else if (lowestPrice == null) {
            Toast.makeText(this, "Harga Terendah tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return
        } else if (hargaTertinggi.isEmpty()) {
            Toast.makeText(this, "Harga Tertinggi tidak boleh kosong", Toast.LENGTH_SHORT).show()
            et_updt_harga_tertinggi.setBackgroundResource(R.drawable.err_outline_stroke)
            return
        } else if (highestPrice == null) {
            Toast.makeText(this, "Harga Tertinggi tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return
        } else if (lowestPrice >= highestPrice) {
            Toast.makeText(
                this, "Harga Terendah harus lebih kecil dari Harga Tertinggi",
                Toast.LENGTH_SHORT
            ).show()
            et_updt_harga_terendah.setBackgroundResource(R.drawable.err_outline_stroke)
            return
        }else if (facility.isEmpty()) {
            Toast.makeText(this, "Fasilitas tidak boleh kosong", Toast.LENGTH_SHORT).show()
            et_updt_fasilitas.setBackgroundResource(R.drawable.err_outline_stroke)
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
//            result["gambar"] = images

            query.update(result)
                .addOnSuccessListener {
                    val intent = Intent(this, MainFragmentActivity::class.java)
                    pb_update_place.visibility = View.GONE
                    finish()
                    Toast.makeText(this, "Update Tempat Futsal berhasil", Toast.LENGTH_SHORT).show()
                    finish()
                    startActivity(intent)
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Update Gagal", Toast.LENGTH_SHORT).show()
                    pb_update_place.visibility = View.GONE
                    finish()
                    startActivity(intent)
                }
        }
    }

    private fun getData() {

        val placeId = intent.getStringExtra("placeId")
        val query = GetDb().collection.document(placeId)

        query.get().addOnSuccessListener {
            val name = it.data?.get("namaTempat").toString()
            val facility = it.data?.get("fasilitas").toString()
            val noTelp = it.data?.get("noTelp").toString()
            val lat = it.data?.get("latitude").toString()
            val long = it.data?.get("longitude").toString()
            val alamat = it.data?.get("alamat").toString()
            val hargaTerendah = it.data?.get("hargaTerendah").toString()
            val hargaTertinggi = it.data?.get("hargaTertinggi").toString()

            et_updt_nama_tempat.setText(name)
            et_updt_fasilitas.setText(facility)
            et_updt_alamat.setText(alamat)
            et_updt_latitude.setText(lat)
            et_updt_longitude.setText(long)
            et_updt_noTelp.setText(noTelp)
            et_updt_harga_terendah.setText(hargaTerendah)
            et_updt_harga_tertinggi.setText(hargaTertinggi)
        }

    }
}