package com.example.sigadmin.layouts

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.sigadmin.R
import com.example.sigadmin.models.DataField
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.field_detail.*
import kotlinx.android.synthetic.main.update_field.*

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class FieldDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.field_detail)

        getData()
        btn_perbarui.setOnClickListener {
            val intent =  Intent(this, UpdateField::class.java)
            startActivity(intent)
        }

    }

    private fun getData() {

        val name = findViewById<TextView>(R.id.tv_nama_lapangan)
        val facility = findViewById<TextView>(R.id.tv_fasilitas)
        val alamat = findViewById<TextView>(R.id.tv_alamat)
        val jamBuka = findViewById<TextView>(R.id.tv_jam_buka)
        val jamTutup = findViewById<TextView>(R.id.tv_jam_tutup)
        val lat = findViewById<TextView>(R.id.tv_latitude)
        val long = findViewById<TextView>(R.id.tv_longitude)
        val noTelp = findViewById<TextView>(R.id.tv_no_telepon)

        name.text = intent.extras.getString("name")
        facility.text = intent.extras.getString("facility")
        alamat.text = intent.extras.getString("alamat")
        jamBuka.text = intent.extras.getString("jamBuka")
        jamTutup.text = intent.extras.getString("jamTutup")
        lat.text = intent.extras.getString("lat")
        long.text = intent.extras.getString("long")
        noTelp.text = intent.extras.getString("noTelp")

    }
}
