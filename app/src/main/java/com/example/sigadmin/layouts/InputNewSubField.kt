package com.example.sigadmin.layouts

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import com.example.sigadmin.R

class InputNewSubField : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.input_new_sub_field)

        val saveNewSubFieldBtn = findViewById<Button>(R.id.saveNewSubFieldButton)
        val dropJenis = findViewById<Spinner>(R.id.spnrJenisLapangan)

        saveNewSubFieldBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val jenisLap = arrayOf("Vinyl", "Sintetis")
        val arrayAdp = ArrayAdapter(this@InputNewSubField,R.layout.input_new_sub_field,jenisLap)
        dropJenis.adapter = arrayAdp
    }
}
