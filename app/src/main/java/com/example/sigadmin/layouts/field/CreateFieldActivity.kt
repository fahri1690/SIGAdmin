package com.example.sigadmin.layouts.field

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.sigadmin.R
import com.example.sigadmin.layouts.main.MainFragmentActivity
import com.example.sigadmin.services.db.GetDb
import kotlinx.android.synthetic.main.activity_create_place.*
import kotlinx.android.synthetic.main.activity_create_field.*
import java.util.*

class CreateFieldActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    internal var id: String = ""


    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {

    }

    override fun onNothingSelected(parent: AdapterView<*>) {

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_field)

        pb_create_field.visibility = View.GONE

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.jenis_lapangan, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spn_jenis_lapangan.adapter = adapter

        spn_jenis_lapangan.onItemSelectedListener = this



        btn_save_new_sub_field.setOnClickListener {
            saveData()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun saveData() {

        pb_create_field.visibility = View.VISIBLE

        val placeId = intent.getStringExtra("placeId")
        val docRef = GetDb().collection.document(placeId)
        val namaSubLapangan = et_sub_field_name.text.toString()
        val jenis = spn_jenis_lapangan.selectedItem.toString()
        val hargaSiang = et_harga_siang.text.toString()
        val hargaMalam = et_harga_malam.text.toString()

        val dayPrice = hargaSiang.toInt()
        val nighPrice = hargaMalam.toInt()

        when {
            namaSubLapangan.isEmpty() -> {
                Toast.makeText(this, "Nama wajib diisi", Toast.LENGTH_SHORT).show()
                et_field_name.setBackgroundResource(R.drawable.err_outline_stroke)
                et_field_name.setHintTextColor(getColor(R.color.errColor))
                return
            }
            dayPrice == null -> {
                Toast.makeText(this, "Harga Siang wajib diisi", Toast.LENGTH_SHORT).show()
                et_harga_siang.setBackgroundResource(R.drawable.err_outline_stroke)
                et_harga_siang.setHintTextColor(getColor(R.color.errColor))
                return
            }
            nighPrice == null -> {
                Toast.makeText(this, "Harga Malam wajib diisi", Toast.LENGTH_SHORT).show()
                et_harga_malam.setBackgroundResource(R.drawable.err_outline_stroke)
                et_harga_malam.setHintTextColor(getColor(R.color.errColor))
                return
            }
            else -> {

                val result = HashMap<String, Any>()
                result["name"] = namaSubLapangan
                result["jenis"] = jenis
                result["hargaSiang"] = dayPrice
                result["hargaMalam"] = nighPrice

                docRef.collection("listLapangan")
                    .add(result)
                    .addOnSuccessListener {
                        val intent = Intent(this, MainFragmentActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    .addOnFailureListener {

                    }
            }
        }
    }

    override fun onBackPressed() {
        val placeId = intent.getStringExtra("placeId")
        val docRef = GetDb().collection.document(placeId)

        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {

                    val intent = Intent(this, MainFragmentActivity::class.java)

                    val name = document.data?.get("name").toString()
                    val facility = document.data?.get("facility").toString()
                    val jamBuka = document.data?.get("jamBuka").toString()
                    val jamTutup = document.data?.get("jamTutup").toString()
                    val noTelp = document.data?.get("noTelp").toString()
                    val alamat = document.data?.get("alamat").toString()
                    val lat = document.data?.get("lat").toString()
                    val long = document.data?.get("long").toString()
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
            .addOnFailureListener { exception ->

            }
    }
}