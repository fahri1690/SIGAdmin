package com.example.sigadmin.layouts.info.place

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.sigadmin.R
import com.example.sigadmin.layouts.home.HomeAdminActivity
import com.example.sigadmin.models.PlaceModel
import com.example.sigadmin.services.db.GetDb
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_create_place.*
import java.io.IOException
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

    var stringList: ArrayList<String> = ArrayList()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_place)

        imgRef = FirebaseStorage.getInstance()
        storageReference = imgRef!!.reference

        select_button.setOnClickListener {
            selectImage()
        }

        btn_save_new_field.setOnClickListener {
            uploadImage()
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
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data?.clipData != null) {

                val totalItem = data.clipData.itemCount

                for (i in 0 until totalItem step 1) {
                    val uri = data.clipData.getItemAt(i).uri
                    imageList.add(uri)
                }

                Toast.makeText(this, "$totalItem foto dipilih", Toast.LENGTH_LONG).show()

            } else if (data?.data != null) {
                filepath = data.data
                try {
                    MediaStore.Images.Media.getBitmap(contentResolver, filepath)
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                Toast.makeText(this, "1 foto dipilih", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun uploadImage() {

        val imageRef = storageReference!!.child("images/places/*" + UUID.randomUUID().toString())

        for (uploadCount in 0 until imageList.size step 1) {

            val single = imageList.get(uploadCount)

            val images = storageReference!!.child("images/places/*" + single.lastPathSegment)

            val uploadTask = images.putFile(single)

            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                return@Continuation images.downloadUrl
            }).addOnSuccessListener {

            }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()

                }
                .addOnCompleteListener {
                    if (it.isSuccessful) {

                        val list = arrayListOf(it.result.toString())
                        val totalItem = list.size

                        for (size in 0 until totalItem step 1) {
                            println(totalItem)
                            addUploadToDb(it.result.toString())
                        }


                    }

                    return@addOnCompleteListener
                }

        }

        if (filepath != null) {

            val uploadTask = imageRef.putFile(filepath!!)
            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                return@Continuation imageRef.downloadUrl
            }).addOnFailureListener {
                Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show()

            }.addOnCompleteListener {
                if (it.isSuccessful) {

                    val list = it.result.toString()
                    addUploadToDb(list)


                }
            }
        }


    }

    private fun addUploadToDb(uri: String) {

        val db = GetDb().collection

        val model = PlaceModel()

        val name = et_field_name.text.toString()
        val facility = et_facility.text.toString()
        val jamBuka = et_jam_buka.text.toString()
        val jamTutup = et_jam_tutup.text.toString()
        val noTelp = et_no_telp.text.length
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
        } else if (noTelp == null) {
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

            val result = hashMapOf(
                "images" to arrayListOf(uri),
                "name" to name,
                "facility" to facility,
                "jamBuka" to jamBuka,
                "jamTutup" to jamTutup,
                "noTelp" to noTelp,
                "alamat" to alamat,
                "lat" to lat,
                "long" to long
            )

            db.add(result).addOnSuccessListener {
                val intent = Intent(this, HomeAdminActivity::class.java)
                finish()
                startActivity(intent)
            }
        }

    }

}
