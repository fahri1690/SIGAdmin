package com.example.sigadmin.layouts

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.sigadmin.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.input_new_field.*
import kotlinx.android.synthetic.main.input_new_sub_field.*
import java.io.IOException
import java.util.*

class InputNewSubField : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private val db = FirebaseFirestore.getInstance()

    internal var id: String = ""

    private var filepath: Uri? = null
    private var PICK_IMAGE_REQUEST = 111

    private var imgRef: FirebaseStorage? = null
    private var storageReference: StorageReference? = null

    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {

    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.input_new_sub_field)

        imgRef = FirebaseStorage.getInstance()
        storageReference = imgRef!!.reference

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.jenis_lapangan, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        spn_jenis_lapangan.adapter = adapter

        spn_jenis_lapangan.onItemSelectedListener = this

        img_new_sub_field.setOnClickListener {
            selectImage()
        }

        btn_save_new_sub_field.setOnClickListener {
            uploadImage()
            saveData()
            rollBack()
        }
    }

    private fun selectImage() {
        intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Pilih Foto"), PICK_IMAGE_REQUEST)
    }

    private fun uploadImage() {
        if (filepath != null) {
            val imageRef = storageReference!!.child("images/subFields/*" + UUID.randomUUID().toString())
            imageRef.putFile(filepath!!)
                .addOnSuccessListener {
                    Toast.makeText(this, "Upload Gambar Sukses", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Upload Gambar Gagal", Toast.LENGTH_SHORT).show()

                }
//                .addOnProgressListener { taskSnapShot ->
//                    val progress = 100.0 * taskSnapShot.bytesTransferred/taskSnapShot.totalByteCount
//                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            filepath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filepath)
                img_new_sub_field!!.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun rollBack() {
        val newIntent = Intent(this, HomeAdmin::class.java)
        startActivity(newIntent)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun saveData() {
        val namaSubLapangan = et_sub_field_name.text.toString()
        val jenis = spn_jenis_lapangan.selectedItem.toString()
        val hargaSiang = et_harga_siang.text.toString()
        val hargaMalam = et_harga_malam.text.toString()

        if (namaSubLapangan.isEmpty()) {
            Toast.makeText(this, "Nama wajib diisi", Toast.LENGTH_SHORT).show()
            et_field_name.setBackgroundResource(R.drawable.err_outline_stroke)
            et_field_name.setHintTextColor(getColor(R.color.errColor))
        } else if (hargaSiang.isEmpty()) {
            Toast.makeText(this, "Harga Siang wajib diisi", Toast.LENGTH_SHORT).show()
            et_harga_siang.setBackgroundResource(R.drawable.err_outline_stroke)
            et_harga_siang.setHintTextColor(getColor(R.color.errColor))
        } else if (hargaMalam.isEmpty()) {
            Toast.makeText(this, "Harga Malam wajib diisi", Toast.LENGTH_SHORT).show()
            et_harga_malam.setBackgroundResource(R.drawable.err_outline_stroke)
            et_harga_malam.setHintTextColor(getColor(R.color.errColor))
        } else {

            val result = HashMap<String, Any>()
            result["name"] = namaSubLapangan
            result["jenis"] = jenis
            result["hargaSiang"] = hargaSiang
            result["hargaMalam"] = hargaMalam

            db.collection("SubLapangan")
                .add(result)
                .addOnSuccessListener {
                    val intent = Intent(this, HomeAdmin::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener {

                }
        }
    }
}

