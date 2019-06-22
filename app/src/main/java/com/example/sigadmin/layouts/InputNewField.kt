package com.example.sigadmin.layouts

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.sigadmin.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.input_new_field.*
import java.util.*
import kotlin.collections.HashMap
import java.io.IOException as IOException1

class InputNewField : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    internal var id: String = ""

    private var filepath: Uri? = null
    private var PICK_IMAGE_REQUEST = 111

    private var imgRef: FirebaseStorage? = null
    private var storageReference: StorageReference? = null


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.input_new_field)

        imgRef = FirebaseStorage.getInstance()
        storageReference = imgRef!!.reference

        img_new_field.setOnClickListener {
            selectImage()
        }

        btn_save_new_field.setOnClickListener {
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
            val imageRef = storageReference!!.child("images/fields/*" + UUID.randomUUID().toString())
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            filepath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filepath)
                img_new_field!!.setImageBitmap(bitmap)
            } catch (e: IOException1) {
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
        val name = et_field_name.text.toString()
        val facility = et_facility.text.toString()
        val jamBuka = et_jam_buka.text.toString()
        val jamTutup = et_jam_tutup.text.toString()
        val noTelp = et_no_telp.text.toString()
        val alamat = et_alamat.text.toString()
        val lat = et_latitude.text.toString()
        val long = et_longitude.text.toString()

        if (name.isEmpty()) {
            Toast.makeText(this, "Nama wajib diisi", Toast.LENGTH_SHORT).show()
            et_field_name.setBackgroundResource(R.drawable.err_outline_stroke)
            et_field_name.setHintTextColor(getColor(R.color.errColor))
        } else if (facility.isEmpty()) {
            Toast.makeText(this, "Fasilitas wajib diisi", Toast.LENGTH_SHORT).show()
            et_facility.setBackgroundResource(R.drawable.err_outline_stroke)
            et_facility.setHintTextColor(getColor(R.color.errColor))
        } else if (jamBuka.isEmpty()) {
            Toast.makeText(this, "Jam Buka wajib diisi", Toast.LENGTH_SHORT).show()
            et_jam_buka.setBackgroundResource(R.drawable.err_outline_stroke)
            et_jam_buka.setHintTextColor(getColor(R.color.errColor))
        } else if (jamTutup.isEmpty()) {
            Toast.makeText(this, "Jam Tutup wajib diisi", Toast.LENGTH_SHORT).show()
            et_jam_tutup.setBackgroundResource(R.drawable.err_outline_stroke)
            et_jam_tutup.setHintTextColor(getColor(R.color.errColor))
        } else if (noTelp.isEmpty()) {
            Toast.makeText(this, "Nomor Telepon wajib diisi", Toast.LENGTH_SHORT).show()
            et_no_telp.setBackgroundResource(R.drawable.err_outline_stroke)
            et_no_telp.setHintTextColor(getColor(R.color.errColor))
        } else if (alamat.isEmpty()) {
            Toast.makeText(this, "Alamat wajib diisi", Toast.LENGTH_SHORT).show()
            et_alamat.setBackgroundResource(R.drawable.err_outline_stroke)
            et_alamat.setHintTextColor(getColor(R.color.errColor))
        } else if (lat.isEmpty()) {
            Toast.makeText(this, "Latitude wajib diisi", Toast.LENGTH_SHORT).show()
            et_latitude.setBackgroundResource(R.drawable.err_outline_stroke)
            et_latitude.setHintTextColor(getColor(R.color.errColor))
        } else if (long.isEmpty()) {
            Toast.makeText(this, "Longitude wajib diisi", Toast.LENGTH_SHORT).show()
            et_longitude.setBackgroundResource(R.drawable.err_outline_stroke)
            et_longitude.setHintTextColor(getColor(R.color.errColor))
        } else {

            val result = HashMap<String, Any>()
            result["name"] = name
            result["facility"] = facility
            result["jamBuka"] = jamBuka
            result["jamTutup"] = jamTutup
            result["noTelp"] = noTelp
            result["alamat"] = alamat
            result["lat"] = lat
            result["long"] = long

            db.collection("Lapangan")
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






