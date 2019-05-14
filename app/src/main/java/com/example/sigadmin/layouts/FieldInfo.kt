package com.example.sigadmin.layouts

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.sigadmin.R

class FieldInfo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.field_info)

        val btnUpdateField = findViewById<Button>(R.id.updateField)

        btnUpdateField.setOnClickListener {
            val intent = Intent (this, UpdateField::class.java)
            startActivity(intent)
        }
    }
}
