package com.example.sigadmin.layouts

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
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

        val btnMasuk = findViewById<Button>(R.id.btnMasuk)

        ref = FirebaseDatabase.getInstance().getReference("Admin")
        registerButton.setOnClickListener {
            val intent = Intent(this, OtpActivity::class.java)
            startActivity(intent)
            savedata()
        }

        btnMasuk.setOnClickListener {
            val intent = Intent (this, LoginActivity::class.java)
            startActivity(intent)
        }
    }


    private fun savedata() {
        val namaPengguna = etNamaRegister.text.toString()
        val email = etEmailRegister.text.toString()
        val noTelp = etPhoneRegister.text.toString()
        val admin = Admin(namaPengguna, email, noTelp)
        val userId = ref.push().key.toString()

        ref.child(userId).setValue(admin).addOnCompleteListener {
            Toast.makeText(this, "Berhasil", Toast.LENGTH_SHORT).show()
            etNamaRegister.setText("")
            etEmailRegister.setText("")
            etPhoneRegister.setText("")
        }
    }
}

