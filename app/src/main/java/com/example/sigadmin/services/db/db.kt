package com.example.sigadmin.services.db

import com.example.sigadmin.models.PlaceModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class GetDb {
    val ref = FirebaseFirestore.getInstance()
    val collection = ref.collection("tempatFutsal")
    val recyclerOption = FirestoreRecyclerOptions.Builder<PlaceModel>()
}

class OrderBy {
    val ascendingName = GetDb().collection.orderBy("namaTempat", Query.Direction.ASCENDING)
}