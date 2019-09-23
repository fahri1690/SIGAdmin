package com.example.sigadmin.layouts.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sigadmin.R
import com.example.sigadmin.layouts.admin.LoginActivity

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        object : Thread() {
            override fun run() {
                try {
                    sleep(1500)

                    val intent = Intent(baseContext, LoginActivity::class.java)
                    startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }.also {
            it.start()
        }
    }
}
