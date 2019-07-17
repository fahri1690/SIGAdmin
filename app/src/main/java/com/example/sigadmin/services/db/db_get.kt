package com.example.sigadmin.services.db

import com.example.sigadmin.models.PlaceModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
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