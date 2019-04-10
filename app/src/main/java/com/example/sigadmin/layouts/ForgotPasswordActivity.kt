package com.example.sigadmin.layouts

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.sigadmin.R

class ForgotPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forgot_password)

        val sendButton = findViewById<Button>(R.id.sendButton)

        sendButton.setOnClickListener{
            val regIntent = Intent(this, OtpActivity::class.java)
            startActivity(regIntent)
        }
    }
}
