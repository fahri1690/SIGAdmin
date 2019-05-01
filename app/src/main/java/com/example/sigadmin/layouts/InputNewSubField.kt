package com.example.sigadmin.layouts

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import com.example.sigadmin.R
import com.example.sigadmin.models.Field
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
        btnSaveNewField.setOnClickListener {
            savedata()
        }
    }

    private fun savedata() {
        val namaSubLapangan = etNamaSubLapangan.text.toString()
        val jenis = etJenisLapangan.text.toString()
        val hargasiang = etHargaSiang.text.toString()
        val hargamalam = etHargaMalam.text.toString()
        val sublapangan = SubField(namaSubLapangan,jenis,hargasiang,hargamalam)
        val sublapanganId = ref.push()

        ref.child(sublapanganId.toString()).setValue(sublapangan).addOnCompleteListener {
            Toast.makeText(this, "Successs", Toast.LENGTH_SHORT).show()
            etNamaSubLapangan.setText("")
            etJenisLapangan.setText("")
            etHargaSiang.setText("")
            etHargaMalam.setText("")
        }
    }
}
