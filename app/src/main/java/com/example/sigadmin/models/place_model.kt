package com.example.sigadmin.models

data class PlaceModel(
    val placeId: String = "",
    val name: String = "",
    val alamat: String ="",
    val jamBuka: String = "",
    val jamTutup: String = "",
    val facility: String = "",
    val noTelp: String = "",
    val lat: Double = 0.00,
    val long: Double = 0.00,
    val images: ArrayList<String> = arrayListOf(PlaceImages().images)
)

data class PlaceImages(
    val images: String = ""
)
