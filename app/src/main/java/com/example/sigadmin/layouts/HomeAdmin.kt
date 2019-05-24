package com.example.sigadmin.layouts

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import com.example.sigadmin.R
import com.example.sigadmin.adapter.FieldAdapter
import com.google.firebase.auth.FirebaseAuth

class HomeAdmin : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_admin)

        val fieldList = findViewById<ListView>(R.id.fieldList)
        val btnNewField = findViewById<Button>(R.id.btnAddNewField)
        val btnNewSubField = findViewById<Button>(R.id.btnAddNewSubField)

        fieldList.adapter = FieldAdapter(this)

        btnNewField.setOnClickListener {
            val intent = Intent (this, InputNewField::class.java)
            startActivity(intent)
        }

        btnNewSubField.setOnClickListener {
            val intent = Intent (this, InputNewSubField::class.java)
            startActivity(intent)
        }

    }

    override fun onStart() {
        super.onStart()
        if (mAuth.currentUser == null){
            startActivity(Intent(this, LoginActivity::class.java))
        }else{

        }
    }
}
