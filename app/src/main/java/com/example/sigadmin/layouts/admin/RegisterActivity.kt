package com.example.sigadmin.layouts.admin

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.sigadmin.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_register.*

@Suppress("NAME_SHADOWING")
class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private val db = FirebaseFirestore.getInstance()

    private lateinit var auth: FirebaseAuth


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btn_register.setOnClickListener(this)

        auth = FirebaseAuth.getInstance()

        btn_register.setOnClickListener {
            createAccount()
        }

        tv_login.setOnClickListener {
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun createAccount() {
        val name = et_name_register.text.toString()
        val email = et_email_register.text.toString()
        val password = et_password_register.text.toString()

        Log.d(TAG, "createAccount:$email")
        if (!validateForm()){
            return
        }

        if (name.isEmpty()) {
            Toast.makeText(this, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
            et_name_register.setBackgroundResource(R.drawable.err_outline_stroke)
            et_name_register.setHintTextColor(getColor(R.color.errColor))
            return
        } else if (name.length < 2) {
            Toast.makeText(this, "Nama minimal 2 karakter", Toast.LENGTH_SHORT).show()
            et_name_register.setBackgroundResource(R.drawable.err_outline_stroke)
            et_name_register.setHintTextColor(getColor(R.color.errColor))
            return
        } else if (email.isEmpty()) {
            Toast.makeText(this, "Email tidak boleh kosong", Toast.LENGTH_SHORT).show()
            et_email_register.setBackgroundResource(R.drawable.err_outline_stroke)
            et_email_register.setHintTextColor(getColor(R.color.errColor))
            return
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Format email tidak sesuai", Toast.LENGTH_SHORT).show()
            et_email_register.setBackgroundResource(R.drawable.err_outline_stroke)
            et_email_register.setHintTextColor(getColor(R.color.errColor))
            return
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Kata Sandi tidak boleh kosong", Toast.LENGTH_SHORT).show()
            et_password_register.setBackgroundResource(R.drawable.err_outline_stroke)
            et_password_register.setHintTextColor(getColor(R.color.errColor))
            return
        } else if(password.length < 6){
            Toast.makeText(this, "Kata Sandi minimal 6 karakter", Toast.LENGTH_SHORT).show()
            et_password_register.setBackgroundResource(R.drawable.err_outline_stroke)
            et_password_register.setHintTextColor(getColor(R.color.errColor))
            return
        } else {
            val result = HashMap<String, Any>()
            result["namaAdmin"] = name
            result["email"] = email
            result["kataSandi"] = password

            db.collection("admin")
                .add(result)
                .addOnSuccessListener {
                    auth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(this){task ->
                            if (task.isSuccessful){
                                Toast.makeText(this, "Daftar Sukses, silahkan Login untuk masuk sebagai Admin", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, LoginActivity::class.java))
                            } else {
                                Toast.makeText(baseContext, "Gagal bikin akun baru",
                                    Toast.LENGTH_SHORT).show()
                            }
                        }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Registrasi gagal, coba lagi beberapa saat", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun validateForm(): Boolean {
        var valid = true

        val email = et_email_register.text.toString()
        if (TextUtils.isEmpty(email)) {
            et_email_register.error = "Required."
            valid = false
        } else {
            et_email_register.error = null
        }

        val password = et_password_register.text.toString()
        if (TextUtils.isEmpty(password)) {
            et_password_register.error = "Required."
            valid = false
        } else {
            et_password_register.error = null
        }

        return valid
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_register -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                createAccount()
            }
        }
    }

    companion object {
        private const val TAG = "EmailPassword"
    }
}