package com.example.sigadmin.layouts.field

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.sigadmin.R
import com.example.sigadmin.layouts.main.MainFragmentActivity
import com.example.sigadmin.services.db.GetDb
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

        spn_jenisLapangan.adapter = adapter
        spn_jenisLapangan.onItemSelectedListener = this

        btn_save_newField.setOnClickListener {
            saveData()
        }

        back_to_field_list.setOnClickListener {
            onBackPressed()
        }
    }

    private var countDownTimer = object : CountDownTimer(500, 100) {
        override fun onTick(millisUntilFinished: Long) {

        }

        override fun onFinish() {
            pb_create_field.visibility = View.GONE
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun saveData() {

        pb_create_field.visibility = View.VISIBLE


        val placeId = intent.getStringExtra("placeId")
        val docRef = GetDb().collection.document(placeId)
        val kodeLapangan = et_kodeLapangan.text.toString()
        val jenis = spn_jenisLapangan.selectedItem.toString()
        val hargaSiang = et_hargaSiang.text.toString()
        val hargaMalam = et_hargaMalam.text.toString()

        var dayPrice: Int? = null
        var nighPrice: Int? = null

        if (hargaSiang.isNotEmpty()) {
            dayPrice = hargaSiang.toInt()
        }

        if (hargaMalam.isNotEmpty()) {
            nighPrice = hargaMalam.toInt()
        }

        when {
            kodeLapangan.isEmpty() -> {
                Toast.makeText(this, "Kode Lapangan tidak boleh kosong", Toast.LENGTH_SHORT).show()
                et_kodeLapangan.setBackgroundResource(R.drawable.err_outline_stroke)
                et_kodeLapangan.setHintTextColor(getColor(R.color.errColor))
                countDownTimer.start()
                return
            }
            hargaSiang.isEmpty() -> {
                Toast.makeText(this, "Harga Siang tidak boleh kosong", Toast.LENGTH_SHORT).show()
                et_hargaSiang.setBackgroundResource(R.drawable.err_outline_stroke)
                et_hargaSiang.setHintTextColor(getColor(R.color.errColor))
                countDownTimer.start()
                return
            }
            dayPrice == null -> {
                Toast.makeText(this, "Harga Siang tidak boleh kosong", Toast.LENGTH_SHORT).show()
                et_hargaSiang.setBackgroundResource(R.drawable.err_outline_stroke)
                et_hargaSiang.setHintTextColor(getColor(R.color.errColor))
                return
            }
            hargaMalam.isEmpty() -> {
                Toast.makeText(this, "Harga Malam tidak boleh kosong", Toast.LENGTH_SHORT).show()
                et_hargaMalam.setBackgroundResource(R.drawable.err_outline_stroke)
                et_hargaMalam.setHintTextColor(getColor(R.color.errColor))
                countDownTimer.start()
                return
            }
            nighPrice == null -> {
                Toast.makeText(this, "Harga Malam tidak boleh kosong", Toast.LENGTH_SHORT).show()
                et_hargaMalam.setBackgroundResource(R.drawable.err_outline_stroke)
                et_hargaMalam.setHintTextColor(getColor(R.color.errColor))
                return
            }
            else -> {

                val result = HashMap<String, Any>()
                result["kodeLapangan"] = kodeLapangan
                result["jenis"] = jenis
                result["hargaSiang"] = dayPrice
                result["hargaMalam"] = nighPrice

                docRef.collection("listLapangan")
                    .add(result)
                    .addOnSuccessListener {
                        val intent = Intent(this, MainFragmentActivity::class.java)
                        startActivity(intent)
                        finish()
                        Toast.makeText(this, "Tambah Lapangan Berhasil", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}