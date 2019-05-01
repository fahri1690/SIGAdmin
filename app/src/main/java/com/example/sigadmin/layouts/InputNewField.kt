package com.example.sigadmin.layouts

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.sigadmin.R
import com.example.sigadmin.models.Field
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.input_new_field.*

class InputNewField : AppCompatActivity() {

    lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.input_new_field)

        ref = FirebaseDatabase.getInstance().getReference("Lapangan")
        btnSaveNewField.setOnClickListener {
            savedata()
        }
    }

    private fun savedata() {
        val namaLapangan = etNamaLapangan.text.toString()
        val fasilitas = etFasilitas.text.toString()
        val jamBuka = etJamBuka.text.toString()
        val jamTutup = etJamBuka.text.toString()
        val noTelp = etNotelp.text.toString()
        val alamat = etAlamat.text.toString()
        val latitude = etLatitude.text.toString()
        val longitude = etLongitude.text.toString()
        val lapangan = Field(namaLapangan,fasilitas,jamBuka,jamTutup,noTelp,alamat,latitude,longitude)
        val lapanganId = ref.push().key.toString()

        ref.child(lapanganId).setValue(lapangan).addOnCompleteListener {
            Toast.makeText(this, "Successs", Toast.LENGTH_SHORT).show()
            etNamaLapangan.setText("")
            etFasilitas.setText("")
            etJamBuka.setText("")
            etJamTutup.setText("")
            etNotelp.setText("")
            etAlamat.setText("")
            etLatitude.setText("")
            etLongitude.setText("")
            }
    }
}

