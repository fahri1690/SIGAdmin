package com.example.sigadmin.layouts.info.field

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
import com.example.sigadmin.layouts.home.HomeAdminActivity
import com.example.sigadmin.layouts.info.main.MainFragmentActivity
import com.example.sigadmin.services.db.GetDb
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_input_field.*
import kotlinx.android.synthetic.main.activity_create_place.*
import java.util.*

class CreateFieldActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    internal var id: String = ""

    private var filepath: Uri? = null
    private var PICK_IMAGE_REQUEST = 1

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
        setContentView(R.layout.activity_input_field)

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
            saveData()
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

    private fun uploadImage() {
        if (filepath != null) {
            val imageRef = storageReference!!.child("images/fields/${UUID.randomUUID()}/*" + UUID.randomUUID().toString())
            imageRef.putFile(filepath!!)
                .addOnSuccessListener {
                    Toast.makeText(this, "Sukses", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show()

                }
                .addOnProgressListener { taskSnapShot ->
                    val progress = 100.0 * taskSnapShot.bytesTransferred / taskSnapShot.totalByteCount
                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            when {
                data?.clipData != null -> {
                    Toast.makeText(this, "Multiple Image Selected", Toast.LENGTH_SHORT).show()
                }
                data?.data != null -> {
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filepath)
                    img_new_sub_field!!.setImageBitmap(bitmap)
                }
                else -> {

                }
            }
//            filepath = data.data
//            try {
//                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filepath)
////                img_new_sub_field!!.setImageBitmap(bitmap)
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun saveData() {
        val placeId = intent.getStringExtra("placeId")
        val docRef = GetDb().collection.document(placeId)
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

            docRef.collection("listLapangan")
                .add(result)
                .addOnSuccessListener {
                    val intent = Intent(this, HomeAdminActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener {

                }
        }
    }

    override fun onBackPressed() {
        val placeId = intent.getStringExtra("placeId")
        val docRef = GetDb().collection.document(placeId)

        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {

                    val intent = Intent(this, MainFragmentActivity::class.java)

                    val name = document.data?.get("name").toString()
                    val facility = document.data?.get("facility").toString()
                    val jamBuka = document.data?.get("jamBuka").toString()
                    val jamTutup = document.data?.get("jamTutup").toString()
                    val noTelp = document.data?.get("noTelp").toString()
                    val alamat = document.data?.get("alamat").toString()
                    val lat = document.data?.get("lat").toString()
                    val long = document.data?.get("long").toString()
                    intent.putExtra("placeId", placeId)
                    intent.putExtra("name", name)
                    intent.putExtra("facility", facility)
                    intent.putExtra("jamBuka", jamBuka)
                    intent.putExtra("jamTutup", jamTutup)
                    intent.putExtra("noTelp", noTelp)
                    intent.putExtra("alamat", alamat)
                    intent.putExtra("lat", lat)
                    intent.putExtra("long", long)
                    startActivity(intent)
                } else {

                }
            }
            .addOnFailureListener { exception ->

            }
    }
}