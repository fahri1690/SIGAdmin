package com.example.sigadmin.layouts

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.sigadmin.R
import com.example.sigadmin.adapter.FieldAdapter
import com.example.sigadmin.models.DataFieldByName
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.home_admin.*

class HomeAdmin : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_admin)

        val fieldLists = listOf(
                DataFieldByName("Lapangan 1"),
                DataFieldByName("Lapangan 1"),
                DataFieldByName("Lapangan 1")
        )

        val heroesAdapter = FieldAdapter(fieldLists)

        rvMain.apply {
            layoutManager = LinearLayoutManager(this@HomeAdmin)
            adapter = heroesAdapter
        }

    }

//    override fun onStart() {
//        super.onStart()
//        if (mAuth.currentUser == null){
//            startActivity(Intent(this, LoginActivity::class.java))
//        }else{
//
//        }
//    }
}
