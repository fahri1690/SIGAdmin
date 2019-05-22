package com.example.sigadmin.layouts

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.sigadmin.R
import com.example.sigadmin.models.Admin
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.register.*

class RegisterActivity : AppCompatActivity() {

    lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        val tvMasuk = findViewById<TextView>(R.id.tv_login)

        ref = FirebaseDatabase.getInstance().getReference("Admin")
        btn_register.setOnClickListener {
            val intent = Intent(this, OtpActivity::class.java)
            startActivity(intent)
            savedata()
        }

        tvMasuk.setOnClickListener {
            val intent = Intent (this, LoginActivity::class.java)
            startActivity(intent)
        }
    }


    private fun savedata() {
        val namaPengguna = et_name_register.text.toString()
        val email = et_email_register.text.toString()
        val noTelp = et_phone_register.text.toString()
        val admin = Admin(namaPengguna, email, noTelp)
        val userId = ref.push().key.toString()

        ref.child(userId).setValue(admin).addOnCompleteListener {
            Toast.makeText(this, "Berhasil", Toast.LENGTH_SHORT).show()
            et_name_register.setText("")
            et_email_register.setText("")
            et_phone_register.setText("")
        }
    }
}

