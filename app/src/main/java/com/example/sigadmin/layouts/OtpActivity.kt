package com.example.sigadmin.layouts

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.sigadmin.R

class OtpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.otp)

        val otpConfirmButton = findViewById<Button>(R.id.btn_verification_otp)

        otpConfirmButton.setOnClickListener {
            val intent= Intent(this, HomeAdmin::class.java)
            startActivity(intent)
        }
    }
}
