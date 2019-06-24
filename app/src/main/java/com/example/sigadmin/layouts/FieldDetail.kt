package com.example.sigadmin.layouts

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.sigadmin.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.field_detail.*

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class FieldDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.field_detail)

        val id = intent.extras.getString("id")
        val name = intent.extras.getString("name")
        val facility = intent.extras.getString("facility")
        val alamat = intent.extras.getString("alamat")
        val jamBuka = intent.extras.getString("jamBuka")
        val jamTutup = intent.extras.getString("jamTutup")
        val lat = intent.extras.getString("lat")
        val long = intent.extras.getString("long")
        val noTelp = intent.extras.getString("noTelp")

        val db = FirebaseFirestore.getInstance()
        val query = db.collection("Lapangan").document(id)
        val docId = query.id

        getData()
        btn_perbarui.setOnClickListener {
            val intent =  Intent(this, UpdateField::class.java)
            intent.putExtra("id", docId)
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

        name.text = intent.getStringExtra("name")
        facility.text = intent.getStringExtra("facility")
        alamat.text = intent.getStringExtra("alamat")
        jamBuka.text = intent.getStringExtra("jamBuka")
        jamTutup.text = intent.getStringExtra("jamTutup")
        lat.text = intent.getStringExtra("lat")
        long.text = intent.getStringExtra("long")
        noTelp.text = intent.getStringExtra("noTelp")

    }
}
