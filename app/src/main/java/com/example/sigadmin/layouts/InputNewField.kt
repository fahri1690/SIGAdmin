package com.example.sigadmin.layouts

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.sigadmin.R

class InputNewField : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.input_new_field)

        val saveButtonNewField = findViewById<Button>(R.id.saveNewFieldButton)

        saveButtonNewField.setOnClickListener {
            val intent = Intent (this, HomeAdmin::class.java)
            startActivity(intent)
        }
    }
}
