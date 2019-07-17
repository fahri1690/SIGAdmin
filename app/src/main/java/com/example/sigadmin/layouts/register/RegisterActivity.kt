package com.example.sigadmin.layouts.register

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.sigadmin.R
import com.example.sigadmin.layouts.otp.OtpActivity
import com.example.sigadmin.layouts.login.LoginActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    internal var id: String = ""

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btn_register.setOnClickListener {
            saveData()
        }

        tv_login.setOnClickListener {
            val intent = Intent(this, OtpActivity::class.java)
            startActivity(intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun saveData() {
        // Create a new user with a first, middle, and last name
        val name = et_name_register.text.toString()
        val email = et_email_register.text.toString()
        val phone = et_phone_register.text.toString()

        if (name.isEmpty()) {
            Toast.makeText(this, "Nama wajib diisi", Toast.LENGTH_SHORT).show()
            et_email_register.setBackgroundResource(R.drawable.err_outline_stroke)
            et_email_register.setHintTextColor(getColor(R.color.errColor))
        }else if (name.length < 3) {
            Toast.makeText(this, "Nama minimal wajib 3 karakter", Toast.LENGTH_SHORT).show()
            et_name_register.setBackgroundResource(R.drawable.err_outline_stroke)
            et_name_register.setHintTextColor(getColor(R.color.errColor))
        }else if (email.isEmpty()) {
            Toast.makeText(this, "email wajib diisi", Toast.LENGTH_SHORT).show()
            et_email_register.setBackgroundResource(R.drawable.err_outline_stroke)
            et_email_register.setHintTextColor(getColor(R.color.errColor))
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Format email tidak sesuai", Toast.LENGTH_SHORT).show()
            et_email_register.setBackgroundResource(R.drawable.err_outline_stroke)
            et_email_register.setHintTextColor(getColor(R.color.errColor))
        }else if (phone.isEmpty()) {
            Toast.makeText(this, "No telp wajib diisi", Toast.LENGTH_SHORT).show()
            et_name_register.setBackgroundResource(R.drawable.err_outline_stroke)
            et_name_register.setHintTextColor(getColor(R.color.errColor))
        }else if(phone.length < 10){
            Toast.makeText(this, "No telepon minimal 10 karakter", Toast.LENGTH_SHORT).show()
        }else {

            val result = HashMap<String, Any>()
            result["name"] = name
            result["email"] = email
            result["phone"] = phone

            db.collection("Admin")
                    .add(result)
                    .addOnSuccessListener {
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    }
                    .addOnFailureListener {

                    }
        }
    }
}