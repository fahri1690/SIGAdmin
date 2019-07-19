package com.example.sigadmin.layouts.info.place

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.sigadmin.R
import com.example.sigadmin.layouts.home.HomeAdminActivity
import com.example.sigadmin.services.db.GetDb
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_create_place.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "REDUNDANT_LABEL_WARNING")
class CreatePlaceActivity : AppCompatActivity() {

    internal var id: String = ""

    private var filepath: Uri? = null
    private var PICK_IMAGE_REQUEST = 111

    private var imgRef: FirebaseStorage? = null
    private var storageReference: StorageReference? = null

    private var uploadCount = 0

    var imageList: ArrayList<Uri> = ArrayList()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_place)

        imgRef = FirebaseStorage.getInstance()
        storageReference = imgRef!!.reference

        progressBar.visibility = View.INVISIBLE
        select_button.setOnClickListener {
            selectImage()
        }

        btn_save_new_field.setOnClickListener {
            uploadImage()
            saveData()
        }

    }

    private fun selectImage() {
        intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Pilih Foto"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK ) {
            when {
                data?.clipData != null -> {
                    val totalItem = data.clipData.itemCount

                    for (i in 0 until totalItem step 1){
                        val uri = data.clipData.getItemAt(i).uri
                        imageList.add(uri)
                    }

                    Toast.makeText(this, "Multiple Image Selected", Toast.LENGTH_SHORT).show()
                }
                data?.data != null -> {
                    filepath = data.data
                    MediaStore.Images.Media.getBitmap(contentResolver, filepath)
//                    R.id.image_list!!.setImageBitmap(bitmap)
                }
                else -> {

                }
            }
//            filepath = data?.data
//            try {
//                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filepath)
////                img_new_sub_field!!.setImageBitmap(bitmap)
//            } catch (e: IOException1) {
//                e.printStackTrace()
//            }
        }
    }

    private fun uploadImage() {

        val imageRef = storageReference!!.child("images/places/*" + UUID.randomUUID().toString())

        for(uploadCount in 0 until imageList.size step 1) {
            val single = imageList.get(uploadCount)

            val images = storageReference!!.child("images/places/*" + single.lastPathSegment)

            images.putFile(single)
                    .addOnSuccessListener {
                        progressBar.visibility = View.GONE
                        Toast.makeText(this, "Sukses", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show()

                    }
                    .addOnProgressListener { taskSnapShot ->

                    }

        }

        if (filepath != null) {

            progressBar.visibility = View.VISIBLE

            imageRef.putFile(filepath!!)
                .addOnSuccessListener {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, "Sukses", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show()

                }
                .addOnProgressListener { taskSnapShot ->
                    val layout:RelativeLayout = findViewById(R.id.layout)
                    val params = RelativeLayout.LayoutParams(100, 100)
                    params.addRule(RelativeLayout.CENTER_IN_PARENT)
                    layout.addView(progressBar, params)
                }
        }


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

            GetDb().collection
                .add(result)
                .addOnSuccessListener {
                    val intent = Intent(this, HomeAdminActivity::class.java)
                    finish()
                    startActivity(intent)
                }
                .addOnFailureListener {

                }
        }
    }
}