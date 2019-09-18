package com.example.sigadmin.layouts.info.field

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sigadmin.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_update_field.*

class UpdateFieldActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented")
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_field)

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.jenis_lapangan, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        spn_updt_jenis_lapangan.adapter = adapter

        spn_updt_jenis_lapangan.onItemSelectedListener = this

        getData()

        btn_updt_sub_field.setOnClickListener {
            updateData()
        }
    }

    private fun updateData() {

        val fieldId: String = intent.getStringExtra("fieldId")
        val placeId = intent.getStringExtra("placeId")

        val db = FirebaseFirestore.getInstance()
        val query = db.collection("Lapangan").document(placeId).collection("listLapangan").document(fieldId)

        val name = et_updt_sub_field_name.text.toString()
        val jenis = spn_updt_jenis_lapangan.selectedItem.toString()
        val hargaSiang = et_updt_harga_siang.text.toString()
        val hargaMalam = et_updt_harga_malam.text.toString()

        if (name.isEmpty()) {
            Toast.makeText(this, "Kode Lapangn wajib diisi", Toast.LENGTH_SHORT).show()
            return
        } else if (hargaSiang.isEmpty()) {
            Toast.makeText(this, "Harga Siang wajib diisi", Toast.LENGTH_SHORT).show()
            return
        } else if (hargaMalam.isEmpty()) {
            Toast.makeText(this, "Harga Malam wajib diisi", Toast.LENGTH_SHORT).show()
            return
        }

        val result = HashMap<String, Any>()
        result["name"] = name
        result["jenis"] = jenis
        result["hargaSiang"] = hargaSiang
        result["hargaMalam"] = hargaMalam

        query.update(result)
            .addOnSuccessListener {
                val intent = Intent(this, ReadFieldDetailActivity::class.java)
                intent.putExtra("fieldId", fieldId)
                intent.putExtra("placeId", placeId)
                intent.putExtra("name", name)
                intent.putExtra("jenis", jenis)
                intent.putExtra("hargaSiang", hargaSiang)
                intent.putExtra("hargaMalam", hargaMalam)
                finish()
                startActivity(intent)
            }
            .addOnFailureListener { e -> Log.w("Messages", "Error updating document", e) }

        Log.d("PLACEID", placeId)
        Log.d("FIELDID", fieldId)


    }

    private fun getData() {

        val names = intent.getStringExtra("name")
        val hargaSiangs = intent.getStringExtra("hargaSiang")
        val hargaMalams = intent.getStringExtra("hargaMalam")

        val name = et_updt_sub_field_name
        val hargaSiang = et_updt_harga_siang
        val hargaMalam = et_updt_harga_malam

        name.setText(names)
        hargaSiang.setText(hargaSiangs)
        hargaMalam.setText(hargaMalams)

    }
}