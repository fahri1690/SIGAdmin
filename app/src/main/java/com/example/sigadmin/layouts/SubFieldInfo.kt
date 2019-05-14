package com.example.sigadmin.layouts

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.sigadmin.R

class SubFieldInfo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sub_field_info)

        val btnUpdateSubField = findViewById<Button>(R.id.updateSubField)

        btnUpdateSubField.setOnClickListener {
            val intent = Intent (this, UpdateSubField::class.java)
            startActivity(intent)
        }
    }
}
