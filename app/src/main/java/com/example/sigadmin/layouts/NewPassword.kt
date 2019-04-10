package com.example.sigadmin.layouts

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.sigadmin.R
import kotlinx.android.synthetic.main.forgot_password.*

class NewPassword : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_password)

        val newPasswordButton = findViewById<Button>(R.id.newPassButton)

        newPasswordButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }
}
