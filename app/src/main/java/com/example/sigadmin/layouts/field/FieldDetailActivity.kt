package com.example.sigadmin.layouts.field

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sigadmin.R
import com.example.sigadmin.layouts.main.MainFragmentActivity
import com.example.sigadmin.services.db.GetDb
import kotlinx.android.synthetic.main.activity_field_detail.*

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class FieldDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_field_detail)

        val placeId = intent.getStringExtra("placeId")
        val fieldId = intent.extras.getString("fieldId")

        val documentId = GetDb().collection.document(placeId)
        val subCollection = documentId.collection("listLapangan")
        val subDocument = subCollection.document(fieldId.toString())
        val subDocumentId = subDocument.id

        val kodeLapangan = intent.extras.getString("kodeLapangan")
        val jenis = intent.extras.getString("jenis")
        val hargaSiang = intent.extras.getInt("hargaSiang")
        val hargaMalam = intent.extras.getInt("hargaMalam")

        tv_kode_lapangan.text = intent.getStringExtra("kodeLapangan")
        tv_jenis_lapangan.text = intent.getStringExtra("jenis")
        tv_hargaSiang.text = hargaSiang.toString()
        tv_hargaMalam.text = hargaMalam.toString()

        btn_update_field.setOnClickListener {
            val intent = Intent(this, UpdateFieldActivity::class.java)
            intent.putExtra("placeId", placeId)
            intent.putExtra("fieldId", subDocumentId)
            intent.putExtra("kodeLapangan", kodeLapangan)
            intent.putExtra("jenis", jenis)
            intent.putExtra("hargaSiang", hargaSiang)
            intent.putExtra("hargaMalam", hargaMalam)
            startActivity(intent)
        }

        iv_backMainFragment.setOnClickListener {
            onBackPressed()
        }

    }

    override fun onBackPressed() {

        val placeId = intent.getStringExtra("placeId")
        val gambar = intent.getStringArrayListExtra("gambar")
        val docRef = GetDb().collection.document(placeId)

        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {

                    val name = document.data?.get("namaTempat").toString()
                    val facility = document.data?.get("fasilitas").toString()
                    val jamBuka = document.data?.get("jamBuka").toString()
                    val jamTutup = document.data?.get("jamTutup").toString()
                    val noTelp = document.data?.get("noTelp").toString()
                    val alamat = document.data?.get("alamat").toString()
                    val lat = document.data?.get("latitude").toString()
                    val long = document.data?.get("longitude").toString()

                    val intent = Intent(this, MainFragmentActivity::class.java)
                    intent.putExtra("placeId", placeId)
                    intent.putExtra("namaTempat", name)
                    intent.putExtra("fasilitas", facility)
                    intent.putExtra("jamBuka", jamBuka)
                    intent.putExtra("jamTutup", jamTutup)
                    intent.putExtra("noTelp", noTelp)
                    intent.putExtra("alamat", alamat)
                    intent.putExtra("latitude", lat)
                    intent.putExtra("longitude", long)
                    intent.putStringArrayListExtra("gambar", gambar)
                    startActivity(intent)
                }
            }
            .addOnFailureListener {

            }
    }
}
