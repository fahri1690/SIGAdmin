package com.example.sigadmin.layouts

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.sigadmin.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {

    lateinit var ref : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val tvRegister = findViewById<TextView>(R.id.registerText)
        val tvForgotPassword = findViewById<TextView>(R.id.forgotPasswordText)
        val btnLogin = findViewById<Button>(R.id.btnMasuk)
        ref = FirebaseDatabase.getInstance().getReference("Admin")

        tvRegister.setOnClickListener{
            val regIntent = Intent(this, RegisterActivity::class.java)
            startActivity(regIntent)
        }

        tvForgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            val intent = Intent (this, HomeAdmin::class.java)
            startActivity(intent)
        }
    }
}