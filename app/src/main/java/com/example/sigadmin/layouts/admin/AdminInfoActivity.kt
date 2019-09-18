package com.example.sigadmin.layouts.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sigadmin.R
import com.example.sigadmin.layouts.home.HomeAdminActivity
import kotlinx.android.synthetic.main.activity_admin_info.*

class AdminInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_info)

        btn_perbarui_admin.setOnClickListener {
            val intent = Intent(this, UpdateAdminActivity::class.java)
            startActivity(intent)
        }
    }
}
