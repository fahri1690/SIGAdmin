package com.example.sigadmin.layouts

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sigadmin.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.update_field.*

class UpdateField : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_field)

        getData()

        btn_update_field.setOnClickListener {
            updateData()
        }
    }

    private fun updateData() {

        val ids: String = intent.getStringExtra("id")

        val db = FirebaseFirestore.getInstance()
        val query = db.collection("Lapangan").document(ids)

        val name = et_update_field_name.text.toString()
        val facility = et_update_facility.text.toString()
        val alamat = et_update_alamat.text.toString()
        val jamBuka = et_update_jam_buka.text.toString()
        val jamTutup = et_update_jam_tutup.text.toString()
        val lat = et_update_latitude.text.toString()
        val long = et_update_longitude.text.toString()
        val noTelp = et_update_no_telp.text.toString()

        if (name.isEmpty()) {
            Toast.makeText(this, "Nama wajib diisi", Toast.LENGTH_SHORT).show()
        } else if (facility.isEmpty()) {
            Toast.makeText(this, "Fasilitas wajib diisi", Toast.LENGTH_SHORT).show()
        } else if (jamBuka.isEmpty()) {
            Toast.makeText(this, "Jam Buka wajib diisi", Toast.LENGTH_SHORT).show()
        } else if (jamTutup.isEmpty()) {
            Toast.makeText(this, "Jam Tutup wajib diisi", Toast.LENGTH_SHORT).show()
        } else if (noTelp.isEmpty()) {
            Toast.makeText(this, "Nomor Telepon wajib diisi", Toast.LENGTH_SHORT).show()
        } else if (alamat.isEmpty()) {
            Toast.makeText(this, "Alamat wajib diisi", Toast.LENGTH_SHORT).show()
        } else if (lat.isEmpty()) {
            Toast.makeText(this, "Latitude wajib diisi", Toast.LENGTH_SHORT).show()
        } else if (long.isEmpty()) {
            Toast.makeText(this, "Longitude wajib diisi", Toast.LENGTH_SHORT).show()
        }

        val result = HashMap<String, Any>()
        result["name"] = name
        result["facility"] = facility
        result["jamBuka"] = jamBuka
        result["jamTutup"] = jamTutup
        result["noTelp"] = noTelp
        result["alamat"] = alamat
        result["lat"] = lat
        result["long"] = long

        query.update(result)
            .addOnSuccessListener {
                val intent = Intent(this, FieldDetail::class.java)
                intent.putExtra("name", name)
                intent.putExtra("facility", facility)
                intent.putExtra("alamat", alamat)
                intent.putExtra("jamBuka", jamBuka)
                intent.putExtra("jamTutup", jamTutup)
                intent.putExtra("lat", lat)
                intent.putExtra("long", long)
                intent.putExtra("noTelp", noTelp)
                startActivity(intent)
            }
            .addOnFailureListener { e -> Log.w("Messages", "Error updating document", e) }
    }

    private fun getData() {

        val names = intent.getStringExtra("name")
        val facilitys = intent.getStringExtra("facility")
        val alamats = intent.getStringExtra("alamat")
        val jamBukas = intent.getStringExtra("jamBuka")
        val jamTutups = intent.getStringExtra("jamTutup")
        val lats = intent.getStringExtra("lat")
        val longs = intent.getStringExtra("long")
        val noTelps = intent.getStringExtra("noTelp")

        val name = et_update_field_name
        val facility = et_update_facility
        val alamat = et_update_alamat
        val jamBuka = et_update_jam_buka
        val jamTutup = et_update_jam_tutup
        val lat = et_update_latitude
        val long = et_update_longitude
        val noTelp = et_update_no_telp

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
