package com.example.sigadmin.layouts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sigadmin.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.update_field.*

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class UpdateField : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_field)

        btn_update_field.setOnClickListener {
//            updateData()
            println(intent.extras.get("name"))
        }
    }

    private fun updateData() {

        val ref = FirebaseFirestore.getInstance()
        val query = ref.collection("Lapangan")
        val data = query.document()

//        val name = et_update_field_name
//        val facility = findViewById<EditText>(R.id.et_update_facility)
//        val alamat = findViewById<EditText>(R.id.et_update_alamat)
//        val jamBuka = findViewById<EditText>(R.id.et_update_jam_buka)
//        val jamTutup = findViewById<EditText>(R.id.et_update_jam_tutup)
//        val lat = findViewById<EditText>(R.id.et_update_latitude)
//        val long = findViewById<EditText>(R.id.et_update_longitude)
//        val noTelp = et_update_no_telp

//        name.text.insert(0, intent.extras.getString("name"))
//
//        intent.extras.getString("name")
//        intent.extras.getString("facility")
//        intent.extras.getString("alamat")
//        intent.extras.getString("jamBuka")
//        intent.extras.getString("jamTutup")
//        intent.extras.getString("lat")
//        intent.extras.getString("long")
//        intent.extras.getString("noTelp")

//        name.hint = intent.extras.getString("name")
//
//        if (name.text.isEmpty()) {
//            name.hint = intent.extras.getString("name")
//        } else if (facility.text.isEmpty()) {
//            Toast.makeText(this, "Fasilitas wajib diisi", Toast.LENGTH_SHORT).show()
//        } else if (jamBuka.text.isEmpty()) {
//            Toast.makeText(this, "Jam Buka wajib diisi", Toast.LENGTH_SHORT).show()
//        } else if (jamTutup.text.isEmpty()) {
//            Toast.makeText(this, "Jam Tutup wajib diisi", Toast.LENGTH_SHORT).show()
//        } else if (noTelp.text.isEmpty()) {
//            Toast.makeText(this, "Nomor Telepon wajib diisi", Toast.LENGTH_SHORT).show()
//        } else if (alamat.text.isEmpty()) {
//            Toast.makeText(this, "Alamat wajib diisi", Toast.LENGTH_SHORT).show()
//        } else if (lat.text.isEmpty()) {
//            Toast.makeText(this, "Latitude wajib diisi", Toast.LENGTH_SHORT).show()
//        } else if (long.text.isEmpty()) {
//            Toast.makeText(this, "Longitude wajib diisi", Toast.LENGTH_SHORT).show()
//        }

    }
}
