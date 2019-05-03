package com.example.sigadmin.layouts

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.sigadmin.R
import com.example.sigadmin.models.Field
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.input_new_field.*
import java.io.IOException
import java.util.*

class InputNewField : AppCompatActivity() {


    lateinit var ref: DatabaseReference
    private val dbInstance = FirebaseDatabase.getInstance()

    private var filepath: Uri? = null
    private var PICK_IMAGE_REQUEST = 111

    private var imgRef: FirebaseStorage? = null
    private var storageReference: StorageReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.input_new_field)

        imgRef = FirebaseStorage.getInstance()
        storageReference = imgRef!!.reference

        ref = dbInstance.getReference("Lapangan")

        idImageField.setOnClickListener {
            selectImage()
        }

        btnSaveNewField.setOnClickListener {
            uploadImage()
            saveData()
            rollBack()
        }

    }

    private fun rollBack() {
        val newIntent = Intent(this, HomeAdmin::class.java)
        startActivity(newIntent)
    }

    private fun saveData() {
        val lapanganId = ref.push().key.toString()
        val namaLapangan = etNamaLapangan.text.toString()
        val fasilitas = etFasilitas.text.toString()
        val jamBuka = etJamBuka.text.toString()
        val jamTutup = etJamTutup.text.toString()
        val noTelp = etNotelp.text.toString()
        val alamat = etAlamat.text.toString()
        val latitude = etLatitude.text.toString()
        val longitude = etLongitude.text.toString()

        val lapangan = Field(namaLapangan,alamat,jamBuka,jamTutup,fasilitas,noTelp,latitude,longitude)

        ref.child(lapanganId).setValue(lapangan).addOnCompleteListener {
            Toast.makeText(this, "Successs", Toast.LENGTH_SHORT).show()
            etNamaLapangan.setText("")
            etFasilitas.setText("")
            etJamBuka.setText("")
            etJamTutup.setText("")
            etNotelp.setText("")
            etAlamat.setText("")
            etLatitude.setText("")
            etLongitude.setText("")
            }
    }

    private fun selectImage(){
        intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Pilih Foto"), PICK_IMAGE_REQUEST)
    }

    private fun uploadImage(){
        if (filepath != null){
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null){
            filepath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filepath)
                idImageField!!.setImageBitmap(bitmap)
            }catch (e:IOException){
                e.printStackTrace()
            }
        }
    }

}

