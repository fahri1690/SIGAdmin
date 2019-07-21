package com.example.sigadmin.models

import android.net.Uri

data class PlaceModel(
    val placeId: String = "",
    val name: String = "",
    val alamat: String ="",
    val jamBuka: String = "",
    val jamTutup: String = "",
    val facility: String = "",
    val noTelp: String = "",
    val lat: String = "",
    val long: String = ""
//    val images: ArrayList<Uri> = ArrayList()
)