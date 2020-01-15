package com.example.sigadmin.layouts.place

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.sigadmin.R
import com.example.sigadmin.layouts.home.HomeAdminActivity
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


@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "REDUNDANT_LABEL_WARNING")
class CreatePlaceActivity : AppCompatActivity() {

    internal var id: String = ""

    private var filepath: Uri? = null
    private var pickImageRequest = 111

    private var imgRef: FirebaseStorage? = null
    private var storageReference: StorageReference? = null

    private var imageList: ArrayList<Uri> = ArrayList()
    private var imageListString: ArrayList<String> = ArrayList()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_place)

//        read database
        imgRef = FirebaseStorage.getInstance()
        storageReference = imgRef!!.reference

        pb_create_place.visibility = View.GONE

        pick_place_image.setOnClickListener {
            selectImage()
        }

        btn_save_new_field.setOnClickListener {
            uploadImage()
        }
    }

    //    select image in gallery
    private fun selectImage() {
        intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Pilih Foto"), pickImageRequest)
    }

    //    cek image selected
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImageRequest && resultCode == Activity.RESULT_OK) {
            when {
                data?.clipData != null -> {

                    val totalItem: Int = data.clipData.itemCount

                    for (i in 0 until totalItem step 1) {
                        val uri = data.clipData.getItemAt(i).uri
                        imageList.add(uri)
                    }

                    Toast.makeText(this, "$totalItem foto dipilih", Toast.LENGTH_LONG).show()

                }
            }

        }
    }

    //    add image to storage n DB
    private fun uploadImage() {

        pb_create_place.visibility = View.VISIBLE

        val imageCount = imageList.size

        if(imageCount >= 1) {
            for (i in 0 until imageCount step 1) {

                val single = imageList[i]
                val images = storageReference!!.child("gambar/places/*" + single.lastPathSegment)
                val uploadTask = images.putFile(single)

                uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                    when {
                        !task.isSuccessful -> task.exception?.let {
                            Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show()
                            throw it
                        }
                    }
                    Log.d("success", task.result.toString())
                    return@Continuation images.downloadUrl
                }).addOnSuccessListener {

                }.addOnFailureListener {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                }.addOnCompleteListener {
                    if (it.isComplete) {
                        imageListString.add(it.result.toString())
                        Log.d("imageList", imageCount.toString())
                        Log.d("imageListString", imageListString.size.toString())
                        if(imageListString.size == imageCount){
                            pb_create_place.visibility = View.GONE
                            addUploadToDb(imageListString)
                        }
                    }
                }
            }
        }else{
            Toast.makeText(this, "Foto tidak boleh kosong", Toast.LENGTH_LONG).show()
            pb_create_place.visibility = View.GONE
        }
    }

    private fun addUploadToDb(uri: ArrayList<String>) {

        val db = GetDb().collection

        val name = et_place_name.text.toString()
        val facility = et_facility.text.toString()
        val jamBuka = et_jam_buka.text.toString()
        val jamTutup = et_jam_tutup.text.toString()
        val noTelp = et_no_telp.text.toString()
        val alamat = et_alamat.text.toString()
        val lat = et_latitude.text.toString()
        val long = et_longitude.text.toString()
        val jenisLapangan = et_jenisLapangan.text.toString()
        val hargaTerendah = et_harga_terendah.text.toString()
        val hargaTertinggi = et_harga_tertinggi.text.toString()

        var latToDouble: Double? = null
        var longToDouble: Double? = null

        if (lat.isNotEmpty()) {
            latToDouble = java.lang.Double.parseDouble(lat)
        }
        if (long.isNotEmpty()) {
            longToDouble = java.lang.Double.parseDouble(long)
        }

        var lowestPrice: Int? = null
        var highestPrice: Int? = null

        if (hargaTerendah.isNotEmpty()) {
            lowestPrice = hargaTerendah.toInt()
        }

        if (hargaTertinggi.isNotEmpty()) {
            highestPrice = hargaTertinggi.toInt()
        }

        if (name.isEmpty()) {
            Toast.makeText(this, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
            et_place_name.setBackgroundResource(R.drawable.err_outline_stroke)
            et_place_name.setHintTextColor(getColor(R.color.errColor))
            pb_create_place.visibility = View.GONE
            imageListString.clear()
            return
        } else if (facility.isEmpty()) {
            Toast.makeText(this, "Fasilitas tidak boleh kosong", Toast.LENGTH_SHORT).show()
            et_facility.setBackgroundResource(R.drawable.err_outline_stroke)
            et_facility.setHintTextColor(getColor(R.color.errColor))
            pb_create_place.visibility = View.GONE
            imageListString.clear()
            return
        } else if (jamBuka.isEmpty()) {
            Toast.makeText(this, "Jam Buka tidak boleh kosong", Toast.LENGTH_SHORT).show()
            et_jam_buka.setBackgroundResource(R.drawable.err_outline_stroke)
            et_jam_buka.setHintTextColor(getColor(R.color.errColor))
            pb_create_place.visibility = View.GONE
            imageListString.clear()
            return
        } else if (jamTutup.isEmpty()) {
            Toast.makeText(this, "Jam Tutup tidak boleh kosong", Toast.LENGTH_SHORT).show()
            et_jam_tutup.setBackgroundResource(R.drawable.err_outline_stroke)
            et_jam_tutup.setHintTextColor(getColor(R.color.errColor))
            pb_create_place.visibility = View.GONE
            imageListString.clear()
            return
        } else if (noTelp.isEmpty()) {
            Toast.makeText(this, "Nomor Telepon tidak boleh kosong", Toast.LENGTH_SHORT).show()
            et_no_telp.setBackgroundResource(R.drawable.err_outline_stroke)
            et_no_telp.setHintTextColor(getColor(R.color.errColor))
            pb_create_place.visibility = View.GONE
            imageListString.clear()
            return
        } else if (lat.isEmpty()) {
            Toast.makeText(this, "Latitude Telepon tidak boleh kosong", Toast.LENGTH_SHORT).show()
            et_latitude.setBackgroundResource(R.drawable.err_outline_stroke)
            et_latitude.setHintTextColor(getColor(R.color.errColor))
            pb_create_place.visibility = View.GONE
            imageListString.clear()
            return
        } else if (latToDouble == null) {
            Toast.makeText(this, "Latitude tidak boleh kosong", Toast.LENGTH_SHORT).show()
            et_latitude.setBackgroundResource(R.drawable.err_outline_stroke)
            et_latitude.setHintTextColor(getColor(R.color.errColor))
            pb_create_place.visibility = View.GONE
            imageListString.clear()
            return
        } else if (latToDouble < -90 || latToDouble > 90) {
            Toast.makeText(this, "Latitude harus diantara -90 sampai 90", Toast.LENGTH_SHORT)
                .show()
            et_latitude.setBackgroundResource(R.drawable.err_outline_stroke)
            et_latitude.setHintTextColor(getColor(R.color.errColor))
            pb_create_place.visibility = View.GONE
            imageListString.clear()
            return
        } else if (long.isEmpty()) {
            Toast.makeText(this, "Longitude tidak boleh kosong", Toast.LENGTH_SHORT).show()
            et_longitude.setBackgroundResource(R.drawable.err_outline_stroke)
            et_longitude.setHintTextColor(getColor(R.color.errColor))
            pb_create_place.visibility = View.GONE
            imageListString.clear()
            return
        } else if (longToDouble == null) {
            Toast.makeText(this, "Longitude tidak boleh kosong", Toast.LENGTH_SHORT).show()
            et_longitude.setBackgroundResource(R.drawable.err_outline_stroke)
            et_longitude.setHintTextColor(getColor(R.color.errColor))
            pb_create_place.visibility = View.GONE
            imageListString.clear()
            return
        } else if (longToDouble < -180 || longToDouble > 180) {
            Toast.makeText(this, "Longitude harus diantara -180 sampai 180", Toast.LENGTH_SHORT)
                .show()
            et_longitude.setBackgroundResource(R.drawable.err_outline_stroke)
            et_longitude.setHintTextColor(getColor(R.color.errColor))
            pb_create_place.visibility = View.GONE
            return
        } else if (jenisLapangan.isEmpty()) {
            Toast.makeText(this, "Jenis Lapangan tidak boleh kosong", Toast.LENGTH_SHORT).show()
            et_jenisLapangan.setBackgroundResource(R.drawable.err_outline_stroke)
            et_jenisLapangan.setHintTextColor(getColor(R.color.errColor))
            pb_create_place.visibility = View.GONE
            imageListString.clear()
            return
        } else if (hargaTerendah.isEmpty()) {
            Toast.makeText(this, "Harga Terendah tidak boleh kosong", Toast.LENGTH_SHORT).show()
            et_harga_terendah.setBackgroundResource(R.drawable.err_outline_stroke)
            et_harga_terendah.setHintTextColor(getColor(R.color.errColor))
            pb_create_place.visibility = View.GONE
            imageListString.clear()
            return
        } else if (lowestPrice == null) {
            Toast.makeText(this, "Harga Terendah tidak boleh kosong", Toast.LENGTH_SHORT).show()
            et_harga_terendah.setBackgroundResource(R.drawable.err_outline_stroke)
            et_harga_terendah.setHintTextColor(getColor(R.color.errColor))
            pb_create_place.visibility = View.GONE
            return
        } else if (hargaTertinggi.isEmpty()) {
            Toast.makeText(this, "Harga Tertinggi tidak boleh kosong", Toast.LENGTH_SHORT).show()
            et_harga_tertinggi.setBackgroundResource(R.drawable.err_outline_stroke)
            et_harga_tertinggi.setHintTextColor(getColor(R.color.errColor))
            pb_create_place.visibility = View.GONE
            imageListString.clear()
            return
        } else if (highestPrice == null) {
            Toast.makeText(this, "Harga Tertinggi tidak boleh kosong", Toast.LENGTH_SHORT).show()
            et_harga_tertinggi.setBackgroundResource(R.drawable.err_outline_stroke)
            et_harga_tertinggi.setHintTextColor(getColor(R.color.errColor))
            pb_create_place.visibility = View.GONE
            imageListString.clear()
            return
        } else if (alamat.isEmpty()) {
            Toast.makeText(this, "Alamat tidak boleh kosong", Toast.LENGTH_SHORT).show()
            et_alamat.setBackgroundResource(R.drawable.err_outline_stroke)
            et_alamat.setHintTextColor(getColor(R.color.errColor))
            pb_create_place.visibility = View.GONE
            return
        }else if (lowestPrice >= highestPrice) {
            Toast.makeText(this, "Harga Terendah harus lebih murah dari Harga Tertinggi", Toast.LENGTH_SHORT).show()
            et_harga_terendah.setBackgroundResource(R.drawable.err_outline_stroke)
            et_harga_terendah.setHintTextColor(getColor(R.color.errColor))
            pb_create_place.visibility = View.GONE
            imageListString.clear()
            return
        } else {
            val result = hashMapOf(
                "gambar" to uri,
                "namaTempat" to name,
                "fasilitas" to facility,
                "jamBuka" to jamBuka,
                "jamTutup" to jamTutup,
                "noTelp" to noTelp,
                "alamat" to alamat,
                "latitude" to latToDouble,
                "longitude" to longToDouble,
                "jenisLapangan" to jenisLapangan,
                "hargaTerendah" to lowestPrice,
                "hargaTertinggi" to highestPrice
            )

            db.add(result).addOnSuccessListener {
                val intent = Intent(this, HomeAdminActivity::class.java)
                Toast.makeText(this, "Upload berhasil", Toast.LENGTH_SHORT).show()
                finish()
                startActivity(intent)
            }

        }

    }

}