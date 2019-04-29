package com.example.sigadmin.layouts

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.sigadmin.R
import com.example.sigadmin.models.Admin
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.core.Tag
import kotlinx.android.synthetic.main.activity_field_profile.*
import kotlinx.android.synthetic.main.register.*
import java.util.concurrent.TimeUnit

class RegisterActivity : AppCompatActivity() {

    lateinit var ref : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        val url = "https://sigfutsal.firebaseio.com"
        val registerButton = findViewById<Button>(R.id.registerButton)
        ref = FirebaseDatabase.getInstance(url).getReference("sigfutsal").child("admin")

        registerButton.setOnClickListener {
            saveData()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Log.d(TAG, "onVerificationComplete:$credential")
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Log.w(TAG, "onVerificationFailed", e)

                if (e is FirebaseAuthInvalidCredentialsException){
                    print("Kode Salah")
                }else if (e is FirebaseTooManyRequestsException){
                    print("Melewati batas pengiriman")
                }

                print("Silahkan ulangi")
            }

            override fun onCodeSent(verificationId: String?, token: PhoneAuthProvider.ForceResendingToken) {
                Log.d(TAG, "onCodeSent:" + verificationId)

                storedVerificationId = verificationId
                resendToken = token
            }

        }

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber,
            60,
            TimeUnit.SECONDS,
            this,
            callbacks
        )


    }

    private fun saveData() {
        val id = ref.push().key.toString()
        val name = etName.text.toString()
        val phone = etPhone.text.toString()
        val password = etPassword.text.toString()
        val admin = Admin(name,phone,password)

        ref.child(id).setValue(admin).addOnCompleteListener {
            Toast.makeText(this, "Registrasi Berhasil",Toast.LENGTH_SHORT).show()
        }

    }
}
