package com.example.sigadmin.layouts.place

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
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
import kotlin.collections.ArrayList


@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "REDUNDANT_LABEL_WARNING")
class CreatePlaceActivity : AppCompatActivity() {

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

        btn_save_new_place.setOnClickListener {
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

    //    cek image selected count
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

        if (imageCount >= 1) {
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
                    return@Continuation images.downloadUrl
                }).addOnSuccessListener {

                }.addOnFailureListener {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                }.addOnCompleteListener {
                    if (it.isComplete) {
                        imageListString.add(it.result.toString())
                        Log.d("imageList", imageCount.toString())
                        Log.d("imageListString", imageListString.size.toString())
                        if (imageListString.size == imageCount) {
                            pb_create_place.visibility = View.GONE
                            addUploadToDb(imageListString)
                        }
                    }
                }
            }
        } else {
            Toast.makeText(this, "Foto tidak boleh kosong", Toast.LENGTH_LONG).show()
            pb_create_place.visibility = View.GONE
        }
    }

    private fun addUploadToDb(listPict: ArrayList<String>) {

        val db = GetDb().collection

        val name = et_place_name.text.toString()
        val facility = et_facility.text.toString()

        val hBuka = spn_jam_buka.selectedItem as String
        val mBuka = spn_menit_buka.selectedItem as String
        val jamBuka = "$hBuka:$mBuka"

        val hTutup = spn_jam_tutup.selectedItem as String
        val mTutup = spn_menit_tutup.selectedItem as String
        val jamTutup = "$hTutup:$mTutup"

        val noTelp = et_no_telp.text.toString()
        val alamat = et_alamat.text.toString()
        val lat = et_latitude.text.toString()
        val long = et_longitude.text.toString()

        var jLap = ""
        if (cb_sintetis.isChecked && !cb_vinyl.isChecked) {
            jLap = "Sintetis"
        } else if (cb_vinyl.isChecked && !cb_sintetis.isChecked) {
            jLap = "Vinyl"
        } else if (cb_vinyl.isChecked && cb_sintetis.isChecked) {
            jLap = "Vinyl, Sintetis"
        }

        val jenisLapangan = jLap
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
            imageListString.clear()
            return
        } else if (noTelp.isEmpty()) {
            Toast.makeText(this, "Nomor Telepon tidak boleh kosong", Toast.LENGTH_SHORT).show()
            et_no_telp.setBackgroundResource(R.drawable.err_outline_stroke)
            imageListString.clear()
            return
        } else if (alamat.isEmpty()) {
            Toast.makeText(this, "Alamat tidak boleh kosong", Toast.LENGTH_SHORT).show()
            et_alamat.setBackgroundResource(R.drawable.err_outline_stroke)
            imageListString.clear()
            return
        } else if (latToDouble == null) {
            Toast.makeText(this, "Latitude tidak boleh kosong", Toast.LENGTH_SHORT).show()
            et_latitude.setBackgroundResource(R.drawable.err_outline_stroke)
            imageListString.clear()
            return
        } else if (latToDouble < -90 || latToDouble > 90) {
            Toast.makeText(this, "Latitude harus diantara -90 sampai 90", Toast.LENGTH_SHORT)
                .show()
            et_latitude.setBackgroundResource(R.drawable.err_outline_stroke)
            imageListString.clear()
            return
        } else if (longToDouble == null) {
            Toast.makeText(this, "Longitude tidak boleh kosong", Toast.LENGTH_SHORT).show()
            et_longitude.setBackgroundResource(R.drawable.err_outline_stroke)
            imageListString.clear()
            return
        } else if (longToDouble < -180 || longToDouble > 180) {
            Toast.makeText(this, "Longitude harus diantara -180 sampai 180", Toast.LENGTH_SHORT)
                .show()
            et_longitude.setBackgroundResource(R.drawable.err_outline_stroke)
            imageListString.clear()
            return
        } else if (jenisLapangan.isEmpty()) {
            Toast.makeText(this, "Wajib pilih minimal 1 jenis lapangan", Toast.LENGTH_SHORT).show()
//            cb_vinyl.setTextColor(R.color.errColor)
//            cb_sintetis.setTextColor(R.color.errColor)
            imageListString.clear()
            return
        } else if (lowestPrice == null) {
            Toast.makeText(this, "Harga Terendah tidak boleh kosong", Toast.LENGTH_SHORT).show()
            et_harga_terendah.setBackgroundResource(R.drawable.err_outline_stroke)
            imageListString.clear()
            return
        } else if (highestPrice == null) {
            Toast.makeText(this, "Harga Tertinggi tidak boleh kosong", Toast.LENGTH_SHORT).show()
            et_harga_tertinggi.setBackgroundResource(R.drawable.err_outline_stroke)
            imageListString.clear()
            return
        } else if (lowestPrice >= highestPrice) {
            Toast.makeText(
                this,
                "Harga Terendah harus lebih murah dari Harga Tertinggi",
                Toast.LENGTH_SHORT
            ).show()
            et_harga_terendah.setBackgroundResource(R.drawable.err_outline_stroke)
            et_harga_tertinggi.setBackgroundResource(R.drawable.err_outline_stroke)
            imageListString.clear()
            return
        } else if (facility.isEmpty()) {
            Toast.makeText(this, "Fasilitas tidak boleh kosong", Toast.LENGTH_SHORT).show()
            et_facility.setBackgroundResource(R.drawable.err_outline_stroke)
            imageListString.clear()
            return
        } else {
            val result = hashMapOf(
                "gambar" to listPict,
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
                Toast.makeText(this, "Berhasil menambahkan Tempat Futsal", Toast.LENGTH_SHORT).show()
                finish()
                startActivity(intent)
            }

        }

    }

}