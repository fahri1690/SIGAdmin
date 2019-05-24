package com.example.sigadmin.layouts

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.sigadmin.R
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.otp.*

class OtpActivity : AppCompatActivity() {

    lateinit var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.otp)

        mAuth = FirebaseAuth.getInstance()

        btn_verification_otp.setOnClickListener {

        }

        mCallbacks = object: PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationFailed(p0: FirebaseException?) {

            }

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                signIn(credential)
            }
        }
    }



    private fun signIn(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener {
                    task: Task<AuthResult> ->
                    if (task.isSuccessful) {
                        startActivity(Intent(this, HomeAdmin::class.java))
                    }
                }
    }
}
