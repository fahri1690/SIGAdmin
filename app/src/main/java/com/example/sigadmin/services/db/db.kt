package com.example.sigadmin.services.db

import com.example.sigadmin.models.PlaceModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage

class GetDb {
    val ref = FirebaseFirestore.getInstance()
    val collection = ref.collection("tempatFutsal")
    val recyclerOption = FirestoreRecyclerOptions.Builder<PlaceModel>()
}

class GetImage {
    val ref = FirebaseStorage.getInstance()
    val storage = ref.reference
}

class OrderBy {
    val ascendingName = GetDb().collection.orderBy("namaTempat", Query.Direction.ASCENDING)
}

class GetAuth {
    val userAuth = FirebaseAuth.getInstance().currentUser?.uid
}