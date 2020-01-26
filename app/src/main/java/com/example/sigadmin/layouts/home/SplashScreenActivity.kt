package com.example.sigadmin.layouts.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sigadmin.R
import com.example.sigadmin.layouts.admin.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        object : Thread() {
            override fun run() {
                try {
                    sleep(800)
                    verifyToken()

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }.also {
            it.start()
        }
    }

    private fun verifyToken() {

        val mUser = FirebaseAuth.getInstance().currentUser

        if (mUser?.email == null) {

            val intent = Intent(baseContext, LoginActivity::class.java)
            startActivity(intent)

        } else{
            mUser.getIdToken(true).addOnCompleteListener { p0 ->
                if (p0.isSuccessful) {
                    startActivity(Intent(baseContext, HomeAdminActivity::class.java))
                } else {
                    p0.exception
                }
            }
        }
    }

}
