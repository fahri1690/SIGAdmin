package com.example.sigadmin.layouts.admin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sigadmin.R
import kotlinx.android.synthetic.main.activity_admin_info.*

class AdminInfoActivity : AppCompatActivity() {

    companion object {
        var USER_EMAIL = "user_email"
        var USER_NAME = "user_name"
        var PHONE = "phone"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_info)

        val email = intent.getStringExtra(USER_EMAIL)
        val name = intent.getStringExtra(USER_NAME)
        val phone = intent.getStringExtra(PHONE)

        tv_email_admin.text = email
        tv_nama_admin.text = name
//        tv_phone.text = phone

    }
}
