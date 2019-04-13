package com.example.sigadmin.layouts

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import com.example.sigadmin.R
import com.example.sigadmin.models.DataField

class FieldProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_field_profile)

        val img = findViewById<ImageView>(R.id.imgView)
        val fieldName = findViewById<TextView>(R.id.fieldName)
        val facilies = findViewById<TextView>(R.id.facilites)
        val openHour = findViewById<TextView>(R.id.openHour)
        val closeHour = findViewById<TextView>(R.id.closeHour)
        val phoneNumber = findViewById<TextView>(R.id.phoneNumber)
        val address = findViewById<TextView>(R.id.address)

        img.setImageResource(DataField.fields[0].image)
        fieldName.setText(DataField.fields[0].fieldName)
        facilies.setText(DataField.fields[0].facility)
        openHour.setText(DataField.fields[0].openHour)
        closeHour.setText(DataField.fields[0].closeHour)
        phoneNumber.setText(DataField.fields[0].phoneNumber)
        address.setText(DataField.fields[0].address)

    }
}
