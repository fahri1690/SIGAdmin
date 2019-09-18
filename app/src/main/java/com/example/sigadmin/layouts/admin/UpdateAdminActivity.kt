package com.example.sigadmin.layouts.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sigadmin.R
import com.example.sigadmin.layouts.home.HomeAdminActivity
import kotlinx.android.synthetic.main.activity_admin_info.*
import kotlinx.android.synthetic.main.activity_update_admin.*

class UpdateAdminActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_admin)

        btn_save_updt_admin.setOnClickListener {
            val intent = Intent(this, HomeAdminActivity::class.java)
            startActivity(intent)

        }
    }
}
