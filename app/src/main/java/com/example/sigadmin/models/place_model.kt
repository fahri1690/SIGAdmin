package com.example.sigadmin.models

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.GeoPoint

data class PlaceModel(
    val placeId: String = "",
    val name: String = "",
    val alamat: String ="",
    val jamBuka: String = "",
    val jamTutup: String = "",
    val facility: String = "",
    val noTelp: Int = 0,
    val latLng: List<Double> = listOf(0.0, 0.0),
    val images: ArrayList<String> = arrayListOf(PlaceImages().images)
)

data class PlaceImages(
    val images: String = ""
)
