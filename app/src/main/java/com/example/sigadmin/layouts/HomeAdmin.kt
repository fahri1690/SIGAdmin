package com.example.sigadmin.layouts

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.sigadmin.R
import com.example.sigadmin.adapter.FieldAdapter

class HomeAdmin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_admin)

        val fieldList = findViewById<ListView>(R.id.fieldList)

        fieldList.adapter = FieldAdapter(this)

    }
}
