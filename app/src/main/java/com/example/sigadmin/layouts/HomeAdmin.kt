package com.example.sigadmin.layouts

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import com.example.sigadmin.R
import com.example.sigadmin.adapter.FieldAdapter

class HomeAdmin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_admin)

        val fieldList = findViewById<ListView>(R.id.fieldList)
        val btnNewField = findViewById<Button>(R.id.btnAddNewField)

        fieldList.adapter = FieldAdapter(this)

        btnNewField.setOnClickListener {
            val intent = Intent (this, InputNewField::class.java)
            startActivity(intent)
        }

    }
}
