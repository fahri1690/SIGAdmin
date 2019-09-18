package com.example.sigadmin.layouts.register

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.sigadmin.R
import com.example.sigadmin.layouts.home.HomeAdminActivity
import com.example.sigadmin.layouts.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btn_register.setOnClickListener {
            saveData()
        }

        tv_login.setOnClickListener {
            intent = Intent (this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun saveData() {
        val name = et_name_register.text.toString()
        val email = et_email_register.text.toString()
        val password = et_password_register.text.toString()

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener
                Log.d("Main", "Daftar Sukses")
            }
            .addOnFailureListener {
                Log.d("Main", "Daftar Gagal! ${it.message}")
            }

        if (name.isEmpty()) {
            Toast.makeText(this, "Nama wajib diisi", Toast.LENGTH_SHORT).show()
            et_name_register.setBackgroundResource(R.drawable.err_outline_stroke)
            et_name_register.setHintTextColor(getColor(R.color.errColor))
        } else if (name.length < 2) {
            Toast.makeText(this, "Nama minimal 2 karakter", Toast.LENGTH_SHORT).show()
            et_name_register.setBackgroundResource(R.drawable.err_outline_stroke)
            et_name_register.setHintTextColor(getColor(R.color.errColor))
        } else if (email.isEmpty()) {
            Toast.makeText(this, "email wajib diisi", Toast.LENGTH_SHORT).show()
            et_email_register.setBackgroundResource(R.drawable.err_outline_stroke)
            et_email_register.setHintTextColor(getColor(R.color.errColor))
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Format email tidak sesuai", Toast.LENGTH_SHORT).show()
            et_email_register.setBackgroundResource(R.drawable.err_outline_stroke)
            et_email_register.setHintTextColor(getColor(R.color.errColor))
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Kata Sandi wajib diisi", Toast.LENGTH_SHORT).show()
            et_password_register.setBackgroundResource(R.drawable.err_outline_stroke)
            et_password_register.setHintTextColor(getColor(R.color.errColor))
        } else {
            val result = HashMap<String, Any>()
            result["nama"] = name
            result["email"] = email
            result["kataSandi"] = password

            db.collection("Admin")
                .add(result)
                .addOnSuccessListener {
                    val intent = Intent(this, HomeAdminActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Daftar Sukses", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {

                }
        }
    }
}