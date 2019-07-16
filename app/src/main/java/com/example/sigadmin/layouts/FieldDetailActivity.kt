package com.example.sigadmin.layouts

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.sigadmin.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_field_detail.*

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class FieldDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_field_detail)

        val id = intent.extras.getString("id")
        val name = intent.extras.getString("name")
        val jenis = intent.extras.getString("jenis")
        val hargaSiang = intent.extras.getString("hargaSiang")
        val hargaMalam = intent.extras.getString("hargaMalam")

        val db = FirebaseFirestore.getInstance().collection("Lapangan").document()
        val query = db.collection("listLapangan").document(id)
        val docId = query.id

        getData()
        btn_perbarui.setOnClickListener {
            val intent = Intent(this, UpdateFieldActivity::class.java)
            intent.putExtra("id", docId)
            intent.putExtra("name", name)
            intent.putExtra("jenis", jenis)
            intent.putExtra("hargaSiang", hargaSiang)
            intent.putExtra("hargaMalam", hargaMalam)
            Log.d("Mess", "$docId")
            startActivity(intent)
        }

    }

    private fun getData() {

        val name = findViewById<TextView>(R.id.tv_kode_lapangan)
        val jenis = findViewById<TextView>(R.id.tv_jenis_lapangan)
        val hargaSiang = findViewById<TextView>(R.id.tv_harga_siang)
        val hargaMalam = findViewById<TextView>(R.id.tv_harga_malam)

        name.text = intent.getStringExtra("name")
        jenis.text = intent.getStringExtra("jenis")
        hargaSiang.text = intent.getStringExtra("hargaSiang")
        hargaMalam.text = intent.getStringExtra("hargaMalam")

    }
}