package com.example.sigadmin.layouts.place

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.MediaStore
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

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_place)

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

    private var countDownTimer = object : CountDownTimer(2000, 800) {
        override fun onTick(millisUntilFinished: Long) {

        }

        override fun onFinish() {
            pb_create_place.visibility = View.GONE
        }
    }

    private fun selectImage() {
        intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Pilih Foto"), pickImageRequest)
    }

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
                data?.data != null -> {
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
    }

    private fun uploadImage() {

        pb_create_place.visibility = View.VISIBLE

        val imageRef = storageReference!!.child("gambar/places/*" + UUID.randomUUID().toString())

        for (uploadCount in 0 until imageList.size step 1) {

            val single = imageList[uploadCount]
            val images = storageReference!!.child("gambar/places/*" + single.lastPathSegment)
            val uploadTask = images.putFile(single)

            countDownTimer.start()

            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                when {
                    !task.isSuccessful -> task.exception?.let {
                        pb_create_place.visibility = View.GONE
                        Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show()
                        throw it
                    }
                }
                return@Continuation images.downloadUrl
            }).addOnSuccessListener {

            }
                .addOnFailureListener {
                    pb_create_place.visibility = View.GONE
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                }
                .addOnCompleteListener {
                    when {
                        it.isSuccessful -> {

                            val list = arrayListOf(it.result.toString())

                            for (size in 0 until list.size step 1) {
                                list.add(it.result.toString())
                            }
                            addUploadToDb(it.result.toString())
                        }
                    }
                    return@addOnCompleteListener
                }
        }

        if (filepath == null) {
            countDownTimer.start()
        } else {

            val uploadTask = imageRef.putFile(filepath!!)
            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                when {
                    !task.isSuccessful -> task.exception?.let {
                        throw it
                    }
                }
                return@Continuation imageRef.downloadUrl
            }).addOnFailureListener {
                Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show()

            }.addOnCompleteListener {
                when {
                    it.isSuccessful -> {

                        pb_create_place.visibility = View.GONE

                        val list = arrayListOf(it.result.toString())

                        for (size in 0 until list.size step 1) {
                            list.add(it.result.toString())
                        }
                        addUploadToDb(it.result.toString())

                    }
                }
            }
        }

    }

    private fun addUploadToDb(uri: String) {

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
            return
        } else if (facility.isEmpty()) {
            Toast.makeText(this, "Fasilitas tidak boleh kosong", Toast.LENGTH_SHORT).show()
            et_facility.setBackgroundResource(R.drawable.err_outline_stroke)
            et_facility.setHintTextColor(getColor(R.color.errColor))
            pb_create_place.visibility = View.GONE
            return
        } else if (jamBuka.isEmpty()) {
            Toast.makeText(this, "Jam Buka tidak boleh kosong", Toast.LENGTH_SHORT).show()
            et_jam_buka.setBackgroundResource(R.drawable.err_outline_stroke)
            et_jam_buka.setHintTextColor(getColor(R.color.errColor))
            pb_create_place.visibility = View.GONE
            return
        } else if (jamTutup.isEmpty()) {
            Toast.makeText(this, "Jam Tutup tidak boleh kosong", Toast.LENGTH_SHORT).show()
            et_jam_tutup.setBackgroundResource(R.drawable.err_outline_stroke)
            et_jam_tutup.setHintTextColor(getColor(R.color.errColor))
            pb_create_place.visibility = View.GONE
            return
        } else if (noTelp.isEmpty()) {
            Toast.makeText(this, "Nomor Telepon tidak boleh kosong", Toast.LENGTH_SHORT).show()
            et_no_telp.setBackgroundResource(R.drawable.err_outline_stroke)
            et_no_telp.setHintTextColor(getColor(R.color.errColor))
            pb_create_place.visibility = View.GONE
            return
        } else if (lat.isEmpty()) {
            Toast.makeText(this, "Latitude Telepon tidak boleh kosong", Toast.LENGTH_SHORT).show()
            et_latitude.setBackgroundResource(R.drawable.err_outline_stroke)
            et_latitude.setHintTextColor(getColor(R.color.errColor))
            pb_create_place.visibility = View.GONE
            return
        } else if (latToDouble == null) {
            Toast.makeText(this, "Latitude tidak boleh kosong", Toast.LENGTH_SHORT).show()
            et_latitude.setBackgroundResource(R.drawable.err_outline_stroke)
            et_latitude.setHintTextColor(getColor(R.color.errColor))
            pb_create_place.visibility = View.GONE
            return
        } else if (latToDouble < -90 || latToDouble > 90) {
            Toast.makeText(this, "Latitude harus diantara -90 sampai 90", Toast.LENGTH_SHORT)
                .show()
            et_latitude.setBackgroundResource(R.drawable.err_outline_stroke)
            et_latitude.setHintTextColor(getColor(R.color.errColor))
            pb_create_place.visibility = View.GONE
            return
        } else if (long.isEmpty()) {
            Toast.makeText(this, "Longitude tidak boleh kosong", Toast.LENGTH_SHORT).show()
            et_longitude.setBackgroundResource(R.drawable.err_outline_stroke)
            et_longitude.setHintTextColor(getColor(R.color.errColor))
            pb_create_place.visibility = View.GONE
            return
        } else if (longToDouble == null) {
            Toast.makeText(this, "Longitude tidak boleh kosong", Toast.LENGTH_SHORT).show()
            et_longitude.setBackgroundResource(R.drawable.err_outline_stroke)
            et_longitude.setHintTextColor(getColor(R.color.errColor))
            pb_create_place.visibility = View.GONE
            return
        } else if (longToDouble < -180 || longToDouble > 180) {
            Toast.makeText(this,"Longitude harus diantara -180 sampai 180",Toast.LENGTH_SHORT).show()
            et_longitude.setBackgroundResource(R.drawable.err_outline_stroke)
            et_longitude.setHintTextColor(getColor(R.color.errColor))
            pb_create_place.visibility = View.GONE
            return
        } else if (jenisLapangan.isEmpty()) {
            Toast.makeText(this, "Jenis Lapangan tidak boleh kosong", Toast.LENGTH_SHORT).show()
            et_jenisLapangan.setBackgroundResource(R.drawable.err_outline_stroke)
            et_jenisLapangan.setHintTextColor(getColor(R.color.errColor))
            pb_create_place.visibility = View.GONE
            return
        }else if (hargaTerendah.isEmpty()) {
            Toast.makeText(this, "Harga Terendah tidak boleh kosong", Toast.LENGTH_SHORT).show()
            et_harga_terendah.setBackgroundResource(R.drawable.err_outline_stroke)
            et_harga_terendah.setHintTextColor(getColor(R.color.errColor))
            pb_create_place.visibility = View.GONE
            return
        }else if (lowestPrice == null) {
            Toast.makeText(this, "Harga Terendah tidak boleh kosong", Toast.LENGTH_SHORT).show()
            et_harga_terendah.setBackgroundResource(R.drawable.err_outline_stroke)
            et_harga_terendah.setHintTextColor(getColor(R.color.errColor))
            pb_create_place.visibility = View.GONE
            return
        }else if (hargaTertinggi.isEmpty()) {
            Toast.makeText(this, "Harga Tertinggi tidak boleh kosong", Toast.LENGTH_SHORT).show()
            et_harga_tertinggi.setBackgroundResource(R.drawable.err_outline_stroke)
            et_harga_tertinggi.setHintTextColor(getColor(R.color.errColor))
            pb_create_place.visibility = View.GONE
            return
        }else if (highestPrice == null) {
            Toast.makeText(this, "Harga Tertinggi tidak boleh kosong", Toast.LENGTH_SHORT).show()
            et_harga_tertinggi.setBackgroundResource(R.drawable.err_outline_stroke)
            et_harga_tertinggi.setHintTextColor(getColor(R.color.errColor))
            pb_create_place.visibility = View.GONE
            return
        }else if (alamat.isEmpty()) {
            Toast.makeText(this, "Alamat tidak boleh kosong", Toast.LENGTH_SHORT).show()
            et_alamat.setBackgroundResource(R.drawable.err_outline_stroke)
            et_alamat.setHintTextColor(getColor(R.color.errColor))
            pb_create_place.visibility = View.GONE
            return
        } else {

            val result = hashMapOf(
                "gambar" to arrayListOf(uri),
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
                finish()
                startActivity(intent)
            }

        }

    }

}