package com.example.sigadmin.layouts.info.field

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.sigadmin.R
import com.example.sigadmin.layouts.info.main.MainFragmentActivity
import com.example.sigadmin.services.db.GetDb
import kotlinx.android.synthetic.main.activity_field_detail.*

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ReadFieldDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_field_detail)

        val placeId = intent.getStringExtra("placeId")
        val fieldId = intent.extras.getString("fieldId")

        val documentId = GetDb().collection.document(placeId)
        val subCollection = documentId.collection("listLapangan")
        val subDocument = subCollection.document(fieldId.toString())
        val subDocumentId = subDocument.id

        val name = intent.extras.getString("name")
        val jenis = intent.extras.getString("jenis")
        val hargaSiang = intent.extras.getString("hargaSiang")
        val hargaMalam = intent.extras.getString("hargaMalam")

        getData()
        btn_perbarui.setOnClickListener {
            val intent = Intent(this, UpdateFieldActivity::class.java)
            intent.putExtra("placeId", placeId)
            intent.putExtra("fieldId", subDocumentId)
            intent.putExtra("name", name)
            intent.putExtra("jenis", jenis)
            intent.putExtra("hargaSiang", hargaSiang)
            intent.putExtra("hargaMalam", hargaMalam)
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

    override fun onBackPressed() {

        val placeId = intent.getStringExtra("placeId")
        val docRef = GetDb().collection.document(placeId)

        docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {

                        val name = document.data?.get("name").toString()
                        val facility = document.data?.get("facility").toString()
                        val jamBuka = document.data?.get("jamBuka").toString()
                        val jamTutup = document.data?.get("jamTutup").toString()
                        val noTelp = document.data?.get("noTelp").toString()
                        val alamat = document.data?.get("alamat").toString()
                        val lat = document.data?.get("lat").toString()
                        val long = document.data?.get("long").toString()

                        val intent = Intent(this, MainFragmentActivity::class.java)
                        intent.putExtra("placeId", placeId)
                        intent.putExtra("name", name)
                        intent.putExtra("facility", facility)
                        intent.putExtra("jamBuka", jamBuka)
                        intent.putExtra("jamTutup", jamTutup)
                        intent.putExtra("noTelp", noTelp)
                        intent.putExtra("alamat", alamat)
                        intent.putExtra("lat", lat)
                        intent.putExtra("long", long)
                        startActivity(intent)
                    } else {

                    }
                }
                .addOnFailureListener {

                }
    }
}
