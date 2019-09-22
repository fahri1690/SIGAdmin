package com.example.sigadmin.layouts.field

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sigadmin.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_update_field.*

class UpdateFieldActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented")
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_field)

        pb_update_field.visibility = View.GONE

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.jenis_lapangan, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        spn_updt_jenisLapangan.adapter = adapter

        spn_updt_jenisLapangan.onItemSelectedListener = this

        getData()

        btn_save_updateField.setOnClickListener {
            updateData()
        }
    }

    private var countDownTimer = object : CountDownTimer(1000, 800) {
        override fun onTick(millisUntilFinished: Long) {

        }

        override fun onFinish() {
            pb_update_field.visibility = View.GONE
        }

    }

    private fun updateData() {

        pb_update_field.visibility = View.VISIBLE

        countDownTimer.start()

        val fieldId: String = intent.getStringExtra("fieldId")
        val placeId = intent.getStringExtra("placeId")

        val db = FirebaseFirestore.getInstance()
        val query =
            db.collection("Lapangan").document(placeId).collection("listLapangan").document(fieldId)

        val kodeLapangan = et_updt_kodeLapangan.text.toString()
        val jenis = spn_updt_jenisLapangan.selectedItem.toString()
        val hargaSiang = et_updt_hargaSiang.text.toString()
        val hargaMalam = et_updt_hargaMalam.text.toString()

        var dayPrice: Int? = null
        var nightPrice: Int? = null

        if (hargaSiang.isNotEmpty()) {
            dayPrice = hargaSiang.toInt()
        }

        if (hargaMalam.isNotEmpty()) {
            nightPrice = hargaMalam.toInt()
        }

        when {
            kodeLapangan.isEmpty() -> {
                Toast.makeText(this, "Nama wajib diisi", Toast.LENGTH_SHORT).show()
                et_updt_kodeLapangan.setBackgroundResource(R.drawable.err_outline_stroke)
                et_updt_kodeLapangan.setHintTextColor(getColor(R.color.errColor))
                countDownTimer.start()
                return
            }
            hargaSiang.isEmpty() -> {
                Toast.makeText(this, "Harga Siang wajib diisi", Toast.LENGTH_SHORT).show()
                et_updt_hargaSiang.setBackgroundResource(R.drawable.err_outline_stroke)
                et_updt_hargaSiang.setHintTextColor(getColor(R.color.errColor))
                countDownTimer.start()
            }
            dayPrice == null -> {
                Toast.makeText(this, "Harga Siang wajib diisi", Toast.LENGTH_SHORT).show()
                et_updt_hargaSiang.setBackgroundResource(R.drawable.err_outline_stroke)
                et_updt_hargaSiang.setHintTextColor(getColor(R.color.errColor))
                return
            }
            dayPrice <= 0 -> {
                Toast.makeText(this, "Harga Siang harus lebih besar dari 0", Toast.LENGTH_SHORT).show()
                et_updt_hargaSiang.setBackgroundResource(R.drawable.err_outline_stroke)
                et_updt_hargaSiang.setHintTextColor(getColor(R.color.errColor))
                return
            }
            hargaMalam.isEmpty() -> {
                Toast.makeText(this, "Harga Malam wajib diisi", Toast.LENGTH_SHORT).show()
                et_updt_hargaMalam.setBackgroundResource(R.drawable.err_outline_stroke)
                et_updt_hargaMalam.setHintTextColor(getColor(R.color.errColor))
                countDownTimer.start()
                return
            }
            nightPrice == null -> {
                Toast.makeText(this, "Harga Malam wajib diisi", Toast.LENGTH_SHORT).show()
                et_updt_hargaMalam.setBackgroundResource(R.drawable.err_outline_stroke)
                et_updt_hargaMalam.setHintTextColor(getColor(R.color.errColor))
                return
            }
            nightPrice <= 0 -> {
                Toast.makeText(this, "Harga malam harus lebih besar dari 0", Toast.LENGTH_SHORT).show()
                et_updt_hargaMalam.setBackgroundResource(R.drawable.err_outline_stroke)
                et_updt_hargaMalam.setHintTextColor(getColor(R.color.errColor))
                return
            }
            else -> {

                val result = HashMap<String, Any>()
                result["kodeLapangan"] = kodeLapangan
                result["jenis"] = jenis
                result["hargaSiang"] = dayPrice
                result["hargaMalam"] = nightPrice

                query.update(result)
                    .addOnSuccessListener {
                        val intent = Intent(this, FieldDetailActivity::class.java)
                        intent.putExtra("fieldId", fieldId)
                        intent.putExtra("placeId", placeId)
                        intent.putExtra("kodeLapangan", kodeLapangan)
                        intent.putExtra("jenis", jenis)
                        intent.putExtra("hargaSiang", dayPrice)
                        intent.putExtra("hargaMalam", nightPrice)
                        finish()
                        startActivity(intent)
                    }
                    .addOnFailureListener { e -> Log.w("Messages", "Error updating document", e) }

                Log.d("PLACEID", placeId)
                Log.d("FIELDID", fieldId)
            }
        }

    }

    private fun getData() {

        val kodeLapangans = intent.extras.getString("kodeLapangan")
        val hargaSiangs = intent.extras.getInt("hargaSiang")
        val hargaMalams = intent.extras.getInt("hargaMalam")

        et_updt_kodeLapangan.setText(kodeLapangans)
        et_updt_hargaSiang.setText(hargaSiangs.toString())
        et_updt_hargaMalam.setText(hargaMalams.toString())

    }
}