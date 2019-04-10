package com.example.sigadmin.layouts

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.sigadmin.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val tvRegister = findViewById<TextView>(R.id.registerText)
        val tvForgotPassword = findViewById<TextView>(R.id.forgotPasswordText)

        tvRegister.setOnClickListener{
            val regIntent = Intent(this, RegisterActivity::class.java)
            startActivity(regIntent)
        }

        tvForgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
    }
}