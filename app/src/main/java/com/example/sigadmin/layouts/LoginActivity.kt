package com.example.sigadmin.layouts

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sigadmin.R
import com.example.sigadmin.models.CountryData
import com.example.sigadmin.models.CountryData.countryNames
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.login.*
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Toast.makeText(parent?.getContext(),
            "OnItemSelectedListener : " + parent?.getItemAtPosition(position).toString(),
            Toast.LENGTH_SHORT).show()
    }

    lateinit var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        progress.visibility = View.INVISIBLE

        val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, countryNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        spinner!!.adapter = adapter

        spinner!!.onItemSelectedListener = this

        btn_otp_request.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val code = CountryData.countryAreaCodes[spinner.selectedItemPosition]

                val number = et_phone_login.text.toString().trim()

                if (number.isEmpty() || number.length < 10) {
                    Toast.makeText(this@LoginActivity, "Valid number is required", Toast.LENGTH_LONG).show()
                }

                val phone = "+$code+$number"

                val intent = Intent(this@LoginActivity, OtpActivity::class.java)
                intent.putExtra("noTelp", phone)
                startActivity(intent)

            }
        })



    }

//    override fun onNothingSelected(parent: AdapterView<*>?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//        countryNames[position].length
//    }


    private fun callBackVerification() {

        val newIntent = Intent(this, OtpActivity::class.java)

        mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(p0: PhoneAuthCredential?) {
                progress.visibility = View.INVISIBLE
            }

            override fun onVerificationFailed(p0: FirebaseException?) {
                Toast.makeText(this@LoginActivity, "Gagal", Toast.LENGTH_SHORT).show()
            }

            override fun onCodeSent(verificationId: String?, p1: PhoneAuthProvider.ForceResendingToken?) {
                super.onCodeSent(verificationId, p1)
                startActivity(newIntent)
            }

        }
    }

    private fun verify() {

        callBackVerification()

        val phone = et_phone_login.text.toString()

//        val phone = "+6282243361221"

        if (phone.isEmpty()) {
            progress.visibility = View.VISIBLE
            Toast.makeText(this, "KOSONG", Toast.LENGTH_SHORT).show()
        } else {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    phone,      // Phone number to verify
                    60,               // Timeout duration
                    TimeUnit.SECONDS, // Unit of timeout
                    this,             // Activity (for callback binding)
                    mCallbacks
            )
        }


    }
}
