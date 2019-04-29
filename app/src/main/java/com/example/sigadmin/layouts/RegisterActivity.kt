package com.example.sigadmin.layouts

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.sigadmin.R
import com.example.sigadmin.models.Admin
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.register.*

class RegisterActivity : AppCompatActivity() {

    lateinit var ref : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        val url = "https://sigfutsal.firebaseio.com"
        val registerButton = findViewById<Button>(R.id.registerButton)
        ref = FirebaseDatabase.getInstance(url).getReference("sigfutsal").child("admin")

        registerButton.setOnClickListener {
            saveData()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    private fun saveData() {
        val id = ref.push().key.toString()
        val name = etName.text.toString()
        val phone = etPhone.text.toString()
        val password = etPassword.text.toString()
        val admin = Admin(name,phone,password)

        ref.child(id).setValue(admin).addOnCompleteListener {
            Toast.makeText(this, "Registrasi Berhasil",Toast.LENGTH_SHORT).show()
        }

    }
}
