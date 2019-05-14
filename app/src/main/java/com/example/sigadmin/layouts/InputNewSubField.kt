package com.example.sigadmin.layouts

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.sigadmin.R
import com.example.sigadmin.models.SubField
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.input_new_sub_field.*
import java.io.IOException
import java.util.*

class InputNewSubField : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    lateinit var ref: DatabaseReference
    private val dbInstance = FirebaseDatabase.getInstance()

    private var filepath: Uri? = null
    private var PICK_IMAGE_REQUEST = 111

    private var imgRef: FirebaseStorage? = null
    private var storageReference: StorageReference? = null

    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {

    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.input_new_sub_field)

        imgRef = FirebaseStorage.getInstance()
        storageReference = imgRef!!.reference

        ref = dbInstance.getReference("SubLapangan")

        val adapter = ArrayAdapter.createFromResource(this,
            R.array.jenis_lapangan, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        spinner2.adapter = adapter

        spinner2.onItemSelectedListener = this

        idImageSubField.setOnClickListener {
            selectImage()
        }


        btnSaveNewSubField.setOnClickListener {
            uploadImage()
            saveData()
            rollBack()
        }
    }
    private fun rollBack() {
        val newIntent = Intent(this, HomeAdmin::class.java)
        startActivity(newIntent)
    }

    private fun uploadImage() {
        if (filepath != null){
            val imageRef = storageReference!!.child("images/subFields/*" + UUID.randomUUID().toString())
            imageRef.putFile(filepath!!)
                .addOnSuccessListener {
                    Toast.makeText(this, "Sukses", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show()

                }
//                .addOnProgressListener { taskSnapShot ->
//                    val progress = 100.0 * taskSnapShot.bytesTransferred/taskSnapShot.totalByteCount
//                }
        }
    }

    private fun selectImage() {
        intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Pilih Foto"), PICK_IMAGE_REQUEST)
    }

    private fun saveData() {
        val namaSubLapangan = etNamaSubLapangan.text.toString()
        val jenis = spinner2.selectedItem.toString()
        val hargaSiang : Int = etHargaSiang.text.length
        val hargaMalam = etHargaMalam.text.toString()
        val sublapangan = SubField(namaSubLapangan = namaSubLapangan, jenis = jenis, hargaSiang = hargaSiang.toString(), hargaMalam = hargaMalam)
        val sublapanganId = ref.push().key.toString()

        ref.child(sublapanganId).setValue(sublapangan).addOnCompleteListener {
            Toast.makeText(this, "Successs", Toast.LENGTH_SHORT).show()
            etNamaSubLapangan.setText("")
            etHargaSiang.setText("")
            etHargaMalam.setText("")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null){
            filepath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filepath)
                idImageSubField!!.setImageBitmap(bitmap)
            }catch (e: IOException){
                e.printStackTrace()
            }
        }
    }
}
