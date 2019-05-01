package com.example.sigadmin.layouts

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.sigadmin.R
import com.example.sigadmin.models.SubField
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.input_new_field.*
import kotlinx.android.synthetic.main.input_new_sub_field.*

class InputNewSubField : AppCompatActivity() {

    lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.input_new_sub_field)

        ref = FirebaseDatabase.getInstance().getReference("SubLapangan")
        btnSaveNewSubField.setOnClickListener {
            savedata()
        }
    }

    private fun savedata() {
        val namaSubLapangan = etNamaSubLapangan.text.toString()
        val jenis = etJenisLapangan.text.toString()
        val hargaSiang = etHargaSiang.text.toString()
        val hargaMalam = etHargaMalam.text.toString()
        val sublapangan = SubField(namaSubLapangan,jenis,hargaSiang,hargaMalam)
        val sublapanganId = ref.push().key.toString()

        ref.child(sublapanganId).setValue(sublapangan).addOnCompleteListener {
            Toast.makeText(this, "Successs", Toast.LENGTH_SHORT).show()
            etNamaSubLapangan.setText("")
            etJenisLapangan.setText("")
            etHargaSiang.setText("")
            etHargaMalam.setText("")
        }
    }
}
