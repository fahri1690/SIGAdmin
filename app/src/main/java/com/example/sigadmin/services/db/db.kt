package com.example.sigadmin.services.db

import android.content.Intent
import com.example.sigadmin.models.PlaceModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage

class GetDb {
    val ref = FirebaseFirestore.getInstance()
    val collection = ref.collection("Lapangan")
    val recyclerOption = FirestoreRecyclerOptions.Builder<PlaceModel>()
}

class GetImage {
    val ref = FirebaseStorage.getInstance()
    val storage = ref.reference
}

class OrderBy {
    val ascendingName = GetDb().collection.orderBy("name", Query.Direction.ASCENDING)
}

fun getIntentExtras() {
    val intent = Intent()
    val placeId = intent.getStringExtra("placeId")
    val name = intent.getStringExtra("name")
    val facility = intent.getStringExtra("facility")
    val alamat = intent.getStringExtra("alamat")
    val jamBuka = intent.getStringExtra("jamBuka")
    val jamTutup = intent.getStringExtra("jamTutup")
    val lat = intent.getStringExtra("lat")
    val long = intent.getStringExtra("long")
    val noTelp = intent.getStringExtra("noTelp")

    intent.putExtra("placeId", placeId)
    intent.putExtra("name", name)
    intent.putExtra("facility", facility)
    intent.putExtra("jamBuka", jamBuka)
    intent.putExtra("jamTutup", jamTutup)
    intent.putExtra("noTelp", noTelp)
    intent.putExtra("alamat", alamat)
    intent.putExtra("lat", lat)
    intent.putExtra("long", long)
}