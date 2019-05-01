package com.example.sigadmin.layouts

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.sigadmin.R
import com.example.sigadmin.models.Admin
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.register.*

class RegisterActivity : AppCompatActivity() {

    lateinit var ref : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        ref = FirebaseDatabase.getInstance().getReference("Admin")
        registerButton.setOnClickListener {
            savedata()
        }
    }



    private fun savedata() {
        val name = etName.text.toString()
        val phone = etPhone.text.toString()
        val password = etPassword.text.toString()
        val admin = Admin(name,phone, password)
        val userId = ref.push().key.toString()

        ref.child(userId).setValue(admin).addOnCompleteListener {
            Toast.makeText(this, "Successs",Toast.LENGTH_SHORT).show()
            etName.setText("")
            etPhone.setText("")
            etPassword.setText("")
        }
    }

}

