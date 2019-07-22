package com.example.sigadmin.models

data class PlaceModel(
    val images: ArrayList<String> = ArrayList(),
    val name: String = "",
    val alamat: String ="",
    val jamBuka: String = "",
    val jamTutup: String = "",
    val facility: String = "",
    val noTelp: String = "",
    val lat: String = "",
    val long: String = ""
)